package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sqldb.dbcontext.DefaultTypeMapImpl;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Deserializer for deserialization of {@link BindVariable} using sql type map to translate
 * information about type if value.
 */
public class BindVariableDeserializer extends JsonDeserializer<BindVariable> {

  private final SqlTypeMap sqlTypeMap;

  /**
   * Create deserializer using specified type map.
   *
   * @param sqlTypeMap is type map that will be used to look up class based on name
   */
  public BindVariableDeserializer(SqlTypeMap sqlTypeMap) {
    this.sqlTypeMap = sqlTypeMap;
  }

  /**
   * Create deserializer using default type map.
   */
  public BindVariableDeserializer() {
    this(DefaultTypeMapImpl.getDefaultMap());
  }

  private static String getName(TreeNode objectNode) {
    var nameNode = objectNode.get("NAME");
    if (nameNode == null) {
      throw new InternalException("Field NAME missing in BindVariable deserialization");
    }
    if (!(nameNode instanceof TextNode)) {
      throw new InternalException("NAME node must be text in BindVariable deserialization");
    }
    return ((TextNode) nameNode).textValue();
  }

  private Class<?> getType(TreeNode objectNode) {
    var typeNode = objectNode.get("TYPE");
    if (typeNode == null) {
      throw new InternalException("Field TYPE missing in BindVariable deserialization");
    }
    if (!(typeNode instanceof TextNode)) {
      throw new InternalException("TYPE node must be text in BindVariable deserialization");
    }
    return sqlTypeMap.getTypeByName(((TextNode) typeNode).textValue());
  }

  private static @Nullable Object getValue(ObjectCodec objectCodec, TreeNode objectNode,
      Class<?> type) throws JsonProcessingException {
    var valueNode = objectNode.get("VALUE");
    if (valueNode == null) {
      return null;
    }
    return objectCodec.treeToValue(valueNode, type);
  }

  @Override
  public BindVariable deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    var objectCodec = parser.getCodec();
    var objectNode = objectCodec.readTree(parser);
    var name = getName(objectNode);
    var type = getType(objectNode);
    var value = getValue(objectCodec, objectNode, type);
    return new BindVariable(name, type, value);
  }

  @Override
  public String toString() {
    return "BindVariableDeserializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
