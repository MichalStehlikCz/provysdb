package com.provys.db.query.elements;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;

class SelectClauseColumnsSerializer extends StdSerializer<SelectClauseColumns> {

  private static final long serialVersionUID = 8230708676543234921L;

  protected SelectClauseColumnsSerializer() {
    super(SelectClauseColumns.class);
  }

  private static void serializeBody(SelectClauseColumns selectClause, JsonGenerator generator)
      throws IOException {
    generator.writeStartArray();
    var codec = (ObjectMapper) generator.getCodec();
    for (var column : selectClause.getColumns()) {
      codec.writerFor(SelectColumn.class).writeValue(generator, column);
    }
    generator.writeEndArray();
  }

  @Override
  public void serialize(SelectClauseColumns selectClause, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    boolean xml = generator.getCodec() instanceof XmlMapper;
    if (xml) {
      generator.writeStartObject();
      generator.writeFieldName("COLUMN");
    }
    serializeBody(selectClause, generator);
    if (xml) {
      generator.writeEndObject();
    }
  }

  @Override
  public void serializeWithType(SelectClauseColumns selectClause, JsonGenerator generator,
      SerializerProvider provider, TypeSerializer typeSerializer)
      throws IOException {
    var typeId = typeSerializer.typeId(selectClause, START_OBJECT);
    typeSerializer.writeTypePrefix(generator, typeId);
    generator.writeFieldName("COLUMN");
    serializeBody(selectClause, generator);
    typeSerializer.writeTypeSuffix(generator, typeId);
  }
}
