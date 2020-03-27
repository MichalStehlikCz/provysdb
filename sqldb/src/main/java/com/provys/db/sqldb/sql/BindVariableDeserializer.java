package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.SimpleName;
import com.provys.db.sqldb.dbcontext.DefaultTypeMapImpl;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Deserializer for deserialization of {@link BindVariable} using sql type map to translate
 * information about type if value.
 */
public final class BindVariableDeserializer extends StdDeserializer<BindVariable> {

  private static final long serialVersionUID = 8580067727072116590L;
  private final SqlTypeMap sqlTypeMap;

  /**
   * Create deserializer using specified type map.
   *
   * @param sqlTypeMap is type map that will be used to look up class based on name
   */
  public BindVariableDeserializer(SqlTypeMap sqlTypeMap) {
    super(BindVariable.class);
    this.sqlTypeMap = sqlTypeMap;
  }

  /**
   * Create deserializer using default type map.
   */
  public BindVariableDeserializer() {
    this(DefaultTypeMapImpl.getDefaultMap());
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
    return sqlTypeMap.getTypeByName(((TextNode) typeNode).textValue());
  }

  private static @Nullable Object getValue(ObjectCodec objectCodec, TreeNode objectNode,
      Class<?> type) throws JsonProcessingException {
    var valueNode = objectNode.get("VALUE");
    if (valueNode == null) {
      return null;
    }
    return objectCodec.treeToValue(valueNode, type);
  }

  @Override
  public BindVariable deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    var objectCodec = parser.getCodec();
    var objectNode = objectCodec.readTree(parser);
    var name = getName(objectNode);
    var type = getType(objectNode);
    var value = getValue(objectCodec, objectNode, type);
    return new BindVariable(name, type, value);
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
    private @Nullable SqlTypeMap sqlTypeMap;

    SerializationProxy() {
    }

    SerializationProxy(BindVariableDeserializer value) {
      if (value.sqlTypeMap.equals(DefaultTypeMapImpl.getDefaultMap())) {
        this.sqlTypeMap = null;
      } else {
        this.sqlTypeMap = value.sqlTypeMap;
      }
    }

    private Object readResolve() {
      if (sqlTypeMap == null) {
        return new BindVariableDeserializer();
      }
      return new BindVariableDeserializer(sqlTypeMap);
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
    return sqlTypeMap.equals(that.sqlTypeMap);
  }

  @Override
  public int hashCode() {
    return sqlTypeMap.hashCode();
  }

  @Override
  public String toString() {
    return "BindVariableDeserializer{"
        + "sqlTypeMap=" + sqlTypeMap + '}';
  }
}
