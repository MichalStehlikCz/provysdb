package com.provys.db.query.elements;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.provys.common.types.ProvysObjectSerializer;
import com.provys.common.types.TypeMap;
import com.provys.common.types.TypeMapImpl;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Serializer used to flatten {@link Literal} during serialization.
 */
public final class LiteralSerializer extends StdSerializer<Literal<?>> {

  private final TypeMap typeMap;

  /**
   * Creates new literal serializer with supplied object serializer, that will be used for value.
   *
   * @param typeMap is type map that will be used to translate type name
   */
  public LiteralSerializer(TypeMap typeMap) {
    super(Literal.class, true);
    this.typeMap = typeMap;
  }

  /**
   * Create new literal serializer, using {@link ProvysObjectSerializer} as value serializer.
   */
  public LiteralSerializer() {
    this(TypeMapImpl.getDefault());
  }

  @Override
  public void serialize(Literal<?> literal, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeStartObject();
    var value = literal.getValue();
    if (value == null) {
      generator.writeNullField(typeMap.getName(literal.getType()));
    } else {
      generator.writeObjectField(typeMap.getName(literal.getType()), value);
    }
    generator.writeEndObject();
  }

  @Override
  public void serializeWithType(Literal<?> literal, JsonGenerator generator,
      SerializerProvider provider, TypeSerializer typeSerializer)
      throws IOException {
    var typeId = typeSerializer.typeId(literal, START_OBJECT);
    typeSerializer.writeTypePrefix(generator, typeId);
    var value = literal.getValue();
    if (value == null) {
      generator.writeNullField(typeMap.getName(literal.getType()));
    } else {
      generator.writeObjectField(typeMap.getName(literal.getType()), value);
    }
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

    private static final long serialVersionUID = -2200144145253697396L;
    private @Nullable TypeMap typeMap;

    SerializationProxy() {
    }

    SerializationProxy(LiteralSerializer value) {
      this.typeMap = value.typeMap;
    }

    private Object readResolve() throws InvalidObjectException {
      if (typeMap == null) {
        throw new InvalidObjectException("TypeMap not found in LiteralSerializer deserialization");
      }
      return new LiteralSerializer(typeMap);
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
    LiteralSerializer that = (LiteralSerializer) o;
    return typeMap.equals(that.typeMap);
  }

  @Override
  public int hashCode() {
    return typeMap.hashCode();
  }

  @Override
  public String toString() {
    return "SqlLiteralSerializer{"
        + "typeMap=" + typeMap + '}';
  }
}
