package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DefaultJsonClassDeserializer extends StdScalarDeserializer<Class<?>> {

  private static final long serialVersionUID = 5126541588601674024L;
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

    private static final long serialVersionUID = 4943844360741067489L;
    private @Nullable SqlTypeMap sqlTypeMap;

    SerializationProxy() {
    }

    SerializationProxy(DefaultJsonClassDeserializer value) {
      this.sqlTypeMap =
          value.sqlTypeMap.equals(DefaultTypeMapImpl.getDefaultMap()) ? null : value.sqlTypeMap;
    }

    private Object readResolve() {
      return (sqlTypeMap == null) ? new DefaultJsonClassDeserializer()
          : new DefaultJsonClassDeserializer(sqlTypeMap);
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
    DefaultJsonClassDeserializer that = (DefaultJsonClassDeserializer) o;
    return sqlTypeMap.equals(that.sqlTypeMap);
  }

  @Override
  public int hashCode() {
    return sqlTypeMap.hashCode();
  }

  @Override
  public String toString() {
    return "DefaultJsonClassDeserializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
