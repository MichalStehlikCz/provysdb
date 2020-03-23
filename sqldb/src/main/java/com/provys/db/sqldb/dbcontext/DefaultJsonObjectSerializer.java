package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;

/**
 * Json serializer, using type name as available in Sql type map.
 */
public class DefaultJsonObjectSerializer extends JsonSerializer<Object> {

  private final SqlTypeMap sqlTypeMap;

  /**
   * Create serializer using supplied map.
   *
   * @param sqlTypeMap is type map used for translation of object type to name
   */
  public DefaultJsonObjectSerializer(SqlTypeMap sqlTypeMap) {
    this.sqlTypeMap = sqlTypeMap;
  }

  /**
   * Create serializer using default map.
   */
  public DefaultJsonObjectSerializer() {
    this(DefaultTypeMapImpl.getDefaultMap());
  }

  @Override
  public void serialize(Object o, JsonGenerator generator,
      SerializerProvider serializerProvider) throws IOException {
    generator.writeStartObject();
    generator.writeObjectField(sqlTypeMap.getName(o.getClass()), o);
    generator.writeEndObject();
  }

  @Override
  public String toString() {
    return "DefaultJsonObjectSerializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
