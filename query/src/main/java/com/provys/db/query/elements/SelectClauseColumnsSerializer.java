package com.provys.db.query.elements;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;

class SelectClauseColumnsSerializer extends StdSerializer<SelectClauseColumns> {

  private static final long serialVersionUID = 8230708676543234921L;

  protected SelectClauseColumnsSerializer() {
    super(SelectClauseColumns.class);
  }

  @Override
  public void serialize(SelectClauseColumns selectClause, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    boolean xml = generator.getCodec() instanceof XmlMapper;
    if (xml) {
      generator.writeStartObject();
      generator.writeFieldName("COLUMN");
    }
    generator.writeStartArray();
    var codec = (ObjectMapper) generator.getCodec();
    for (var column : selectClause.getColumns()) {
      codec.writerFor(SelectColumn.class).writeValue(generator, column);
    }
    generator.writeEndArray();
    if (xml) {
      generator.writeEndObject();
    }
  }
}
