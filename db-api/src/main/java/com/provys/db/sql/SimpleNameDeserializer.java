package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * Jackson deserializer for {@link SimpleName} class.
 */
public class SimpleNameDeserializer extends JsonDeserializer<SimpleName> {

  @Override
  public SimpleName deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    return SimpleName.valueOf(jsonParser.getText());
  }
}
