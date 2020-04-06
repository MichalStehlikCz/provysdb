package com.provys.db.query.elements;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;

class FromClauseSerializer extends StdSerializer<FromClause> {

  private static final long serialVersionUID = -489636074594048553L;

  protected FromClauseSerializer() {
    super(FromClause.class);
  }

  private static void serializeBody(FromClause fromClause, JsonGenerator generator)
      throws IOException {
    generator.writeStartArray();
    var codec = (ObjectMapper) generator.getCodec();
    for (var element : fromClause.getElements()) {
      codec.writerFor(FromElement.class).writeValue(generator, element);
    }
    generator.writeEndArray();
  }

  @Override
  public void serialize(FromClause fromClause, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    boolean xml = generator.getCodec() instanceof XmlMapper;
    if (xml) {
      generator.writeStartObject();
      generator.writeFieldName("ELEM");
    }
    serializeBody(fromClause, generator);
    if (xml) {
      generator.writeEndObject();
    }
  }

  @Override
  public void serializeWithType(FromClause fromClause, JsonGenerator generator,
      SerializerProvider provider, TypeSerializer typeSerializer)
      throws IOException {
    var typeId = typeSerializer.typeId(fromClause, START_OBJECT);
    typeSerializer.writeTypePrefix(generator, typeId);
    generator.writeFieldName("ELEM");
    serializeBody(fromClause, generator);
    typeSerializer.writeTypeSuffix(generator, typeId);
  }
}
