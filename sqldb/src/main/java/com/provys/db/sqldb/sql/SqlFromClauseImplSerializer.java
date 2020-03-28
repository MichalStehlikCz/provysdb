package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;

public class SqlFromClauseImplSerializer extends StdSerializer<SqlFromClauseImpl> {

  private static final long serialVersionUID = 1791526393540881717L;

  protected SqlFromClauseImplSerializer() {
    super(SqlFromClauseImpl.class);
  }

  @Override
  public void serialize(SqlFromClauseImpl sqlFromClause, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    boolean xml = generator.getCodec() instanceof XmlMapper;
    if (xml) {
      generator.writeStartObject();
      generator.writeFieldName("ELEM");
    }
    generator.writeStartArray();
    var codec = (ObjectMapper) generator.getCodec();
    for (var element : sqlFromClause.getSqlElements()) {
      codec.writerFor(SqlFromElement.class).writeValue(generator, element);
    }
    generator.writeEndArray();
    if (xml) {
      generator.writeEndObject();
    }
  }
}
