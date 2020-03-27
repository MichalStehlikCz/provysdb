package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;

/**
 * Jackson serializer for {@link SimpleName} class.
 */
public class SimpleNameSerializer extends StdScalarSerializer<SimpleName> {

  protected SimpleNameSerializer() {
    super(SimpleName.class);
  }

  @Override
  public void serialize(SimpleName simpleName, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(simpleName.getText());
  }
}
