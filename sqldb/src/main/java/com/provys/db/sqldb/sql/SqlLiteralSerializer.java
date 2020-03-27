package com.provys.db.sqldb.sql;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.provys.db.sqldb.dbcontext.DefaultJsonObjectSerializer;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Serializer used to flatten {@link SqlLiteral} during serialization.
 */
public final class SqlLiteralSerializer extends StdSerializer<SqlLiteral> {

  private final DefaultJsonObjectSerializer objectSerializer;

  /**
   * Creates new literal serializer with supplied object serializer, that will be used for value.
   *
   * @param objectSerializer is serializer to be used for literal value
   */
  public SqlLiteralSerializer(DefaultJsonObjectSerializer objectSerializer) {
    super(SqlLiteral.class);
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

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this DefaultJsonClassDeserializer
   */
  private Object writeReplace() {
    return new SerializationProxy(this);
  }

  /**
   * Should be serialized via proxy, thus no direct deserialization should occur.
   *
   * @param stream is stream from which object is to be read
   * @throws InvalidObjectException always
   */
  private void readObject(ObjectInputStream stream) throws InvalidObjectException {
    throw new InvalidObjectException("Use Serialization Proxy instead.");
  }

  private static final class SerializationProxy implements Serializable {

    private static final long serialVersionUID = -5959109065173165261L;
    private @Nullable DefaultJsonObjectSerializer objectSerializer;

    SerializationProxy() {
    }

    SerializationProxy(SqlLiteralSerializer value) {
      this.objectSerializer = value.objectSerializer;
    }

    private Object readResolve() {
      return new SqlLiteralSerializer(Objects.requireNonNull(objectSerializer));
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlLiteralSerializer that = (SqlLiteralSerializer) o;
    return objectSerializer.equals(that.objectSerializer);
  }

  @Override
  public int hashCode() {
    return objectSerializer.hashCode();
  }

  @Override
  public String toString() {
    return "SqlLiteralSerializer{"
        + "objectSerializer=" + objectSerializer + '}';
  }
}
