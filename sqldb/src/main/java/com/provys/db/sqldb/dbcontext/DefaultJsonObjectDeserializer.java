package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Deserializer for deserialization of Json objects of unknown type, using class name translated
 * to type via type map.
 */
public final class DefaultJsonObjectDeserializer extends StdDeserializer<Object> {

  private static final long serialVersionUID = 5994780487590278107L;
  private final SqlTypeMap sqlTypeMap;

  /**
   * Create deserializer using specified type map.
   *
   * @param sqlTypeMap is type map that will be used to look up class based on name
   */
  public DefaultJsonObjectDeserializer(SqlTypeMap sqlTypeMap) {
    super(Object.class);
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

    private static final long serialVersionUID = -1717662918850904631L;
    private @Nullable SqlTypeMap sqlTypeMap;

    SerializationProxy() {
    }

    SerializationProxy(DefaultJsonObjectDeserializer value) {
      this.sqlTypeMap =
          value.sqlTypeMap.equals(DefaultTypeMapImpl.getDefaultMap()) ? null : value.sqlTypeMap;
    }

    private Object readResolve() {
      return (sqlTypeMap == null) ? new DefaultJsonObjectDeserializer()
          : new DefaultJsonObjectDeserializer(sqlTypeMap);
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
    DefaultJsonObjectDeserializer that = (DefaultJsonObjectDeserializer) o;
    return sqlTypeMap.equals(that.sqlTypeMap);
  }

  @Override
  public int hashCode() {
    return sqlTypeMap.hashCode();
  }

  @Override
  public String toString() {
    return "DefaultJsonObjectDeserializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
