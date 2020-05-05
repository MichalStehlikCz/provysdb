package com.provys.db.query.elements;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.provys.common.types.TypeMap;
import com.provys.common.types.TypeMapImpl;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Deserializer used to construct {@link Literal} from flattened form during deserialization.
 */
public final class LiteralDeserializer extends StdDeserializer<Literal<?>> {

  private final TypeMap typeMap;

  /**
   * Creates new literal deserializer with supplied object deserializer, that will be used for
   * value.
   *
   * @param typeMap is type map that will be used for type name translation
   */
  public LiteralDeserializer(TypeMap typeMap) {
    super(Literal.class);
    this.typeMap = typeMap;
  }

  /**
   * Create new literal deserializer, using {@link TypeMap} for type name translation.
   */
  public LiteralDeserializer() {
    this(TypeMapImpl.getDefault());
  }

  // we are not able to verify in runtime that type is immutable, but in general, types retrieved
  // via TypeMap should be immutable
  @SuppressWarnings("Immutable")
  @Override
  public Literal<?> deserialize(JsonParser parser,
      DeserializationContext context) throws IOException {

    if (!parser.isExpectedStartObjectToken()) {
      return (Literal<?>) context
          .handleUnexpectedToken(Literal.class, parser.currentToken(), parser,
              "Failed to deserialize com.provys.db.sql value - start object expected");
    }
    var typeName = parser.nextFieldName();
    if (typeName == null) {
      return (Literal<?>) context.handleUnexpectedToken(Object.class, parser.currentToken(), parser,
          "Failed to deserialize com.provys.db.sql value - field not found inside value object");
    }
    var type = typeMap.getType(typeName);
    parser.nextToken();
    var value = context.readValue(parser, type);
    if (!parser.nextToken().isStructEnd()) {
      return (Literal<?>) context.handleUnexpectedToken(Object.class, parser.currentToken(), parser,
          "Failed to deserialize com.provys.db.sql value - end object expected");
    }
    if (value == null) {
      return new Literal<>(type, null);
    }
    return new Literal<>(value);
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

    private static final long serialVersionUID = 4298634732340784367L;
    private @Nullable TypeMap typeMap;

    SerializationProxy() {
    }

    SerializationProxy(LiteralDeserializer value) {
      this.typeMap = value.typeMap;
    }

    private Object readResolve() throws InvalidObjectException {
      if (typeMap == null) {
        throw new InvalidObjectException(
            "TypeMap not found during LiteralDeserializer deserialization");
      }
      return new LiteralDeserializer(typeMap);
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
    LiteralDeserializer that = (LiteralDeserializer) o;
    return typeMap.equals(that.typeMap);
  }

  @Override
  public int hashCode() {
    return typeMap.hashCode();
  }

  @Override
  public String toString() {
    return "SqlLiteralDeserializer{"
        + "typeMap=" + typeMap + '}';
  }
}
