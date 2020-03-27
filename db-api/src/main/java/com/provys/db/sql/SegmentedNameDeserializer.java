package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;

/**
 * Jackson deserializer for {@link SegmentedName} class.
 */
public class SegmentedNameDeserializer extends StdScalarDeserializer<SegmentedName> {

  private static final long serialVersionUID = -1577881006994682759L;

  protected SegmentedNameDeserializer() {
    super(SegmentedName.class);
  }

  @Override
  public SegmentedName deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    return SegmentedName.valueOf(jsonParser.getText());
  }
}
