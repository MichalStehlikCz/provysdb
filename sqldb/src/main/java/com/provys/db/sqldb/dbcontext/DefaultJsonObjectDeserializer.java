package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;

/**
 * Deserializer for deserialization of Json objects of unknown type, using class name translated
 * to type via type map.
 */
public class DefaultJsonObjectDeserializer extends JsonDeserializer<Object> {

  private final SqlTypeMap sqlTypeMap;

  /**
   * Create deserializer using specified type map.
   *
   * @param sqlTypeMap is type map that will be used to look up class based on name
   */
  public DefaultJsonObjectDeserializer(SqlTypeMap sqlTypeMap) {
    this.sqlTypeMap = sqlTypeMap;
  }

  /**
   * Create deserializer using default type map.
   */
  public DefaultJsonObjectDeserializer() {
    this(DefaultTypeMapImpl.getDefaultMap());
  }

  @Override
  public Object deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    if (!parser.isExpectedStartObjectToken()) {
      return context.handleUnexpectedToken(Object.class, parser.currentToken(), parser,
          "Failed to deserialize sql value - start object expected");
    }
    var typeName = parser.nextFieldName();
    if (typeName == null) {
      return context.handleUnexpectedToken(Object.class, parser.currentToken(), parser,
          "Failed to deserialize sql value - field not found inside value object");
    }
    var type = sqlTypeMap.getTypeByName(typeName);
    parser.nextToken();
    var result = context.readValue(parser, type);
    if (result == null) {
      throw new InternalException("Failed to deserialize sql value - null return value");
    }
    if (!parser.nextToken().isStructEnd()) {
      return context.handleUnexpectedToken(Object.class, parser.currentToken(), parser,
          "Failed to deserialize sql value - end object expected");
    }
    return result;
  }

  @Override
  public String toString() {
    return "DefaultJsonObjectDeserializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
