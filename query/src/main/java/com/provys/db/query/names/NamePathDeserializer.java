package com.provys.db.query.names;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;

/**
 * Jackson deserializer for {@link NamePath} interface.
 */
public class NamePathDeserializer extends StdScalarDeserializer<NamePath> {

  private static final long serialVersionUID = 7946130703031689135L;

  protected NamePathDeserializer() {
    super(NamePath.class);
  }

  @Override
  public NamePath deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    String text = jsonParser.getText();
    if (text.indexOf('.') < 0) {
      return SimpleName.valueOf(text);
    }
    return SegmentedName.valueOf(text);
  }
}
