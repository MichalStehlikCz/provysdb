package com.provys.db.query.names;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;

/**
 * Jackson serializer for {@link BindName} class.
 */
class BindNameSerializer extends StdScalarSerializer<BindName> {

  private static final long serialVersionUID = 2881017755242788034L;

  protected BindNameSerializer() {
    super(BindName.class);
  }

  @Override
  public void serialize(BindName bindName, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(bindName.getName());
  }
}
