package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;

/**
 * Jackson serializer for {@link NamePath}.
 */
public class NamePathSerializer extends StdScalarSerializer<NamePath> {

  private static final long serialVersionUID = -623708646400338457L;

  protected NamePathSerializer() {
    super(NamePath.class);
  }

  @Override
  public void serialize(NamePath namePath, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(namePath.getText());
  }
}
