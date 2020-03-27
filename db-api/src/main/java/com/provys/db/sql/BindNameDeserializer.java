package com.provys.db.sql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;

/**
 * Jackson deserializer for {@link BindName} class.
 */
public class BindNameDeserializer extends StdScalarDeserializer<BindName> {

  private static final long serialVersionUID = 4166600761822068313L;

  protected BindNameDeserializer() {
    super(BindName.class);
  }

  @Override
  public BindName deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    return BindName.valueOf(jsonParser.getText());
  }
}
