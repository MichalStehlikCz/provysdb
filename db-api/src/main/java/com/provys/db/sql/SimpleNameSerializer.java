package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * Jackson serializer for {@link SimpleName} class.
 */
public class SimpleNameSerializer extends JsonSerializer<SimpleName> {

  @Override
  public void serialize(SimpleName simpleName, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(simpleName.getText());
  }
}
