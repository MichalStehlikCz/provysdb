package com.provys.db.sqldb.sql;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.provys.db.sqldb.dbcontext.DefaultJsonObjectSerializer;
import java.io.IOException;

/**
 * Serializer used to flatten {@link SqlLiteral} during serialization.
 */
public class SqlLiteralSerializer extends JsonSerializer<SqlLiteral> {

  private final JsonSerializer<Object> objectSerializer;

  /**
   * Creates new literal serializer with supplied object serializer, that will be used for value.
   *
   * @param objectSerializer is serializer to be used for literal value
   */
  public SqlLiteralSerializer(JsonSerializer<Object> objectSerializer) {
    this.objectSerializer = objectSerializer;
  }

  /**
   * Create new literal serializer, using {@link DefaultJsonObjectSerializer} as value serializer.
   */
  public SqlLiteralSerializer() {
    this(new DefaultJsonObjectSerializer());
  }

  @Override
  public void serialize(SqlLiteral sqlLiteral, JsonGenerator generator,
      SerializerProvider serializerProvider) throws IOException {
    objectSerializer.serialize(sqlLiteral.getValue(), generator, serializerProvider);
  }

  @Override
  public void serializeWithType(SqlLiteral sqlLiteral, JsonGenerator generator,
      SerializerProvider provider, TypeSerializer typeSerializer)
      throws IOException {
    var typeId = typeSerializer.typeId(sqlLiteral, START_OBJECT);
    typeSerializer.writeTypePrefix(generator, typeId);
    serialize(sqlLiteral, generator, provider);
    typeSerializer.writeTypeSuffix(generator, typeId);
  }

  @Override
  public String toString() {
    return "SqlLiteralSerializer{"
        + "objectSerializer=" + objectSerializer + '}';
  }
}
