package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;

/**
 * Jackson deserializer for {@link SimpleName} class.
 */
public class SimpleNameDeserializer extends StdScalarDeserializer<SimpleName> {

  private static final long serialVersionUID = -5788020750047992892L;

  protected SimpleNameDeserializer() {
    super(SimpleName.class);
  }

  @Override
  public SimpleName deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    return SimpleName.valueOf(jsonParser.getText());
  }
}
