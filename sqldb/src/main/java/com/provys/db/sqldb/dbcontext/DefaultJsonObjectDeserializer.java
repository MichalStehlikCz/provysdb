package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;
import java.util.Objects;

public class DefaultJsonObjectDeserializer extends JsonDeserializer<Object> {

  private final SqlTypeMap sqlTypeMap;

  public DefaultJsonObjectDeserializer(SqlTypeMap sqlTypeMap) {
    this.sqlTypeMap = sqlTypeMap;
  }

  public DefaultJsonObjectDeserializer() {
    this(DefaultTypeMapImpl.getDefaultMap());
  }

  @Override
  public Object deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    var objectCodec = parser.getCodec();
    var node = objectCodec.readTree(parser);
    var typeNode = node.get("type");
    if (typeNode == null) {
      throw new InternalException("Failed to deserialize sql value - type node not found");
    }
    if (!(typeNode instanceof TextNode)) {
      throw new InternalException("Failed to deserialize sql value - type node not text");
    }
    var type = sqlTypeMap.getTypeByName(((TextNode) typeNode).textValue());
    var valueNode = node.get("value");
    if (valueNode == null) {
      throw new InternalException("Failed to deserialize sql value - value node not found");
    }
    return Objects.requireNonNull(objectCodec.treeToValue(valueNode, type),
        "Failed to deserialize sql value - null return value");
  }

  @Override
  public String toString() {
    return "DefaultJsonObjectDeserializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
