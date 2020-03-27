package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;

public class DefaultJsonClassDeserializer extends StdScalarDeserializer<Class<?>> {

  private final SqlTypeMap sqlTypeMap;

  /**
   * Create serializer using supplied map.
   *
   * @param sqlTypeMap is type map used for translation of object type to name
   */
  public DefaultJsonClassDeserializer(SqlTypeMap sqlTypeMap) {
    super(Class.class);
    this.sqlTypeMap = sqlTypeMap;
  }

  /**
   * Create serializer using default map.
   */
  public DefaultJsonClassDeserializer() {
    this(DefaultTypeMapImpl.getDefaultMap());
  }

  @Override
  public Class<?> deserialize(JsonParser parser, DeserializationContext deserializationContext)
      throws IOException {
    return sqlTypeMap.getTypeByName(parser.getValueAsString());
  }

  @Override
  public String toString() {
    return "DefaultJsonClassDeserializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
