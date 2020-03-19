package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * Jackson serializer for {@link NamePath}.
 */
public class NamePathSerializer extends JsonSerializer<NamePath> {

  @Override
  public void serialize(NamePath namePath, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(namePath.getText());
  }
}
