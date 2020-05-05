package com.provys.db.query.names;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import java.io.IOException;

/**
 * Jackson serializer for {@link SegmentedName} class.
 */
class SegmentedNameSerializer extends StdScalarSerializer<SegmentedName> {

  private static final long serialVersionUID = 8329354958617894765L;

  protected SegmentedNameSerializer() {
    super(SegmentedName.class);
  }

  @Override
  public void serialize(SegmentedName segmentedName, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(segmentedName.getText());
  }
}
