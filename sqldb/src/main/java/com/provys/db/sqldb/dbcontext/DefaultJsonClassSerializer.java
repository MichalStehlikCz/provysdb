package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;

/**
 * Json serializer, using type name as available in Sql type map.
 */
public class DefaultJsonClassSerializer extends JsonSerializer<Class<?>> {

  private final SqlTypeMap sqlTypeMap;

  /**
   * Create serializer using supplied map.
   *
   * @param sqlTypeMap is type map used for translation of object type to name
   */
  public DefaultJsonClassSerializer(SqlTypeMap sqlTypeMap) {
    this.sqlTypeMap = sqlTypeMap;
  }

  /**
   * Create serializer using default map.
   */
  public DefaultJsonClassSerializer() {
    this(DefaultTypeMapImpl.getDefaultMap());
  }

  @Override
  public void serialize(Class<?> type, JsonGenerator generator,
      SerializerProvider serializerProvider) throws IOException {
    generator.writeString(sqlTypeMap.getName(type));
  }

  @Override
  public String toString() {
    return "DefaultJsonClassSerializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
