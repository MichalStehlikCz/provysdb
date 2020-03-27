package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.provys.db.sqldb.dbcontext.DefaultJsonObjectDeserializer;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Deserializer used to construct {@link SqlLiteral} from flattened form during deserialization.
 */
public final class SqlLiteralDeserializer extends StdDeserializer<SqlLiteral> {

  private final DefaultJsonObjectDeserializer objectDeserializer;

  /**
   * Creates new literal deserializer with supplied object deserializer, that will be used for
   * value.
   *
   * @param objectDeserializer is deserializer to be used for literal value
   */
  public SqlLiteralDeserializer(DefaultJsonObjectDeserializer objectDeserializer) {
    super(SqlLiteral.class);
    this.objectDeserializer = objectDeserializer;
  }

  /**
   * Create new literal deserializer, using {@link DefaultJsonObjectDeserializer} as value
   * deserializer.
   */
  public SqlLiteralDeserializer() {
    this(new DefaultJsonObjectDeserializer());
  }

  @Override
  public SqlLiteral deserialize(JsonParser parser,
      DeserializationContext deserializationContext) throws IOException {
    return new SqlLiteral(objectDeserializer.deserialize(parser, deserializationContext));
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

    private static final long serialVersionUID = -1600120462226783447L;
    private @Nullable DefaultJsonObjectDeserializer objectDeserializer;

    SerializationProxy() {
    }

    SerializationProxy(SqlLiteralDeserializer value) {
      this.objectDeserializer = value.objectDeserializer;
    }

    private Object readResolve() {
      return new SqlLiteralDeserializer(Objects.requireNonNull(objectDeserializer));
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
    SqlLiteralDeserializer that = (SqlLiteralDeserializer) o;
    return objectDeserializer.equals(that.objectDeserializer);
  }

  @Override
  public int hashCode() {
    return objectDeserializer.hashCode();
  }

  @Override
  public String toString() {
    return "SqlLiteralDeserializer{"
        + "objectDeserializer=" + objectDeserializer + '}';
  }
}
