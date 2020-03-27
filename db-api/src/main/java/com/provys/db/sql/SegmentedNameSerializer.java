package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * Jackson serializer for {@link SegmentedName} class.
 */
public class SegmentedNameSerializer extends JsonSerializer<SegmentedName> {

  @Override
  public void serialize(SegmentedName segmentedName, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(segmentedName.getText());
  }
}
