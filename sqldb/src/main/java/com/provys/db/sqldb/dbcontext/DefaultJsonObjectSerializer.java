package com.provys.db.sqldb.dbcontext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Json serializer, using type name as available in Sql type map.
 */
public final class DefaultJsonObjectSerializer extends StdSerializer<Object> {

  private final SqlTypeMap sqlTypeMap;

  /**
   * Create serializer using supplied map.
   *
   * @param sqlTypeMap is type map used for translation of object type to name
   */
  public DefaultJsonObjectSerializer(SqlTypeMap sqlTypeMap) {
    super(Object.class);
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
    var inObject = true;
    if (!generator.getOutputContext().inObject()) {
      inObject = false;
      generator.writeStartObject();
    }
    generator.writeObjectField(sqlTypeMap.getName(o.getClass()), o);
    if (!inObject) {
      generator.writeEndObject();
    }
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

    private static final long serialVersionUID = -5725243459308442129L;
    private @Nullable SqlTypeMap sqlTypeMap;

    SerializationProxy() {
    }

    SerializationProxy(DefaultJsonObjectSerializer value) {
      this.sqlTypeMap =
          value.sqlTypeMap.equals(DefaultTypeMapImpl.getDefaultMap()) ? null : value.sqlTypeMap;
    }

    private Object readResolve() {
      return (sqlTypeMap == null) ? new DefaultJsonObjectSerializer()
          : new DefaultJsonObjectSerializer(sqlTypeMap);
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
    DefaultJsonObjectSerializer that = (DefaultJsonObjectSerializer) o;
    return sqlTypeMap.equals(that.sqlTypeMap);
  }

  @Override
  public int hashCode() {
    return sqlTypeMap.hashCode();
  }

  @Override
  public String toString() {
    return "DefaultJsonObjectSerializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
