package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * Jackson deserializer for {@link NamePath} interface.
 */
public class NamePathDeserializer extends JsonDeserializer<NamePath> {

  @Override
  public NamePath deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    String text = jsonParser.getText();
    if (text.indexOf('.') < 0) {
      return SimpleName.valueOf(text);
    }
    return SegmentedName.valueOf(text);
  }
}
