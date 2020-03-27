package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.provys.db.sqldb.dbcontext.DefaultJsonObjectDeserializer;
import java.io.IOException;

/**
 * Deserializer used to construct {@link SqlLiteral} from flattened form during deserialization.
 */
public class SqlLiteralDeserializer extends JsonDeserializer<SqlLiteral> {

  private final JsonDeserializer<Object> objectDeserializer;

  /**
   * Creates new literal deserializer with supplied object deserializer, that will be used for
   * value.
   *
   * @param objectDeserializer is deserializer to be used for literal value
   */
  public SqlLiteralDeserializer(JsonDeserializer<Object> objectDeserializer) {
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

  @Override
  public String toString() {
    return "SqlLiteralDeserializer{"
        + "objectDeserializer=" + objectDeserializer + '}';
  }
}
