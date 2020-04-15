package com.provys.db.query.names;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.provys.common.exception.InternalException;
import com.provys.common.types.TypeMap;
import com.provys.common.types.TypeMapImpl;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Deserializer for deserialization of {@link BindVariable} using type map to translate information
 * about type if value.
 */
public final class BindVariableDeserializer extends StdDeserializer<BindVariable> {

  private static final long serialVersionUID = 8580067727072116590L;
  private final TypeMap typeMap;

  /**
   * Create deserializer using specified type map.
   *
   * @param typeMap is type map that will be used to look up class based on name
   */
  public BindVariableDeserializer(TypeMap typeMap) {
    super(BindVariable.class);
    this.typeMap = typeMap;
  }

  /**
   * Create deserializer using default type map.
   */
  public BindVariableDeserializer() {
    this(TypeMapImpl.getDefault());
  }

  private static String getName(TreeNode objectNode) {
    var nameNode = objectNode.get("NAME");
    if (nameNode == null) {
      throw new InternalException("Field NAME missing in BindVariable deserialization");
    }
    if (!(nameNode instanceof TextNode)) {
      throw new InternalException("NAME node must be text in BindVariable deserialization");
    }
    return ((TextNode) nameNode).textValue();
  }

  private Class<?> getType(TreeNode objectNode) {
    var typeNode = objectNode.get("TYPE");
    if (typeNode == null) {
      throw new InternalException("Field TYPE missing in BindVariable deserialization");
    }
    if (!(typeNode instanceof TextNode)) {
      throw new InternalException("TYPE node must be text in BindVariable deserialization");
    }
    return typeMap.getType(((TextNode) typeNode).textValue());
  }

  private static <T extends Serializable> @Nullable T getValue(ObjectCodec objectCodec,
      TreeNode objectNode, Class<T> type) throws JsonProcessingException {
    var valueNode = objectNode.get("VALUE");
    if (valueNode == null) {
      return null;
    }
    return objectCodec.treeToValue(valueNode, type);
  }

  // First, Object.class is handled in deserialize and all other classes returned by TypeMap should
  // be immutable. Second, we hope that whoever serialized this knew what he does...
  @SuppressWarnings("Immutable")
  private static <T extends Serializable> BindVariable deserializeWithValue(String name,
      Class<T> type, ObjectCodec objectCodec, TreeNode objectNode) throws JsonProcessingException {
    return new BindVariable(name, type, getValue(objectCodec, objectNode, type));
  }

  @Override
  public BindVariable deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    var objectCodec = parser.getCodec();
    var objectNode = objectCodec.readTree(parser);
    var name = getName(objectNode);
    var type = getType(objectNode);
    if (type == Object.class) {
      return new BindVariable(name);
    }
    return deserializeWithValue(name, type.asSubclass(Serializable.class), objectCodec, objectNode);
  }

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this SimpleName
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

    private static final long serialVersionUID = 9209714022358652365L;
    private @Nullable TypeMap typeMap;

    SerializationProxy() {
    }

    SerializationProxy(BindVariableDeserializer value) {
      if (value.typeMap.equals(TypeMapImpl.getDefault())) {
        this.typeMap = null;
      } else {
        this.typeMap = value.typeMap;
      }
    }

    private Object readResolve() {
      if (typeMap == null) {
        return new BindVariableDeserializer();
      }
      return new BindVariableDeserializer(typeMap);
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
    BindVariableDeserializer that = (BindVariableDeserializer) o;
    return typeMap.equals(that.typeMap);
  }

  @Override
  public int hashCode() {
    return typeMap.hashCode();
  }

  @Override
  public String toString() {
    return "BindVariableDeserializer{"
        + "typeMap=" + typeMap + '}';
  }
}
