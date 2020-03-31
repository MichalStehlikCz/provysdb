package com.provys.db.query.elements;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;

public class DefaultFromClauseSerializer extends StdSerializer<DefaultFromClause> {

  private static final long serialVersionUID = -489636074594048553L;

  protected DefaultFromClauseSerializer() {
    super(DefaultFromClause.class);
  }

  @Override
  public void serialize(DefaultFromClause fromClause, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    boolean xml = generator.getCodec() instanceof XmlMapper;
    if (xml) {
      generator.writeStartObject();
      generator.writeFieldName("ELEM");
    }
    generator.writeStartArray();
    var codec = (ObjectMapper) generator.getCodec();
    for (var element : fromClause.getElements()) {
      codec.writerFor(FromElement.class).writeValue(generator, element);
    }
    generator.writeEndArray();
    if (xml) {
      generator.writeEndObject();
    }
  }
}
