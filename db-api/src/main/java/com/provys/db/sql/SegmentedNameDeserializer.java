package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * Jackson deserializer for {@link SegmentedName} class.
 */
public class SegmentedNameDeserializer extends JsonDeserializer<SegmentedName> {

  @Override
  public SegmentedName deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    return SegmentedName.valueOf(jsonParser.getText());
  }
}
