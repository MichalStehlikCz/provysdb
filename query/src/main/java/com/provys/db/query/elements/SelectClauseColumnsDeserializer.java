package com.provys.db.query.elements;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.provys.common.exception.InternalException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SelectClauseColumnsDeserializer extends StdDeserializer<SelectClauseColumns> {

  private static final long serialVersionUID = 4026946436771652195L;

  protected SelectClauseColumnsDeserializer() {
    super(SelectClauseColumns.class);
  }

  private static SelectClauseColumns deserializeJson(JsonParser parser)
      throws IOException {
    if (parser.getCurrentToken() != JsonToken.START_ARRAY) {
      throw new InternalException("Start array expected deserializing select clause");
    }
    List<SelectColumn<?>> columns = new ArrayList<>(5);
    while (parser.nextToken() != JsonToken.END_ARRAY) {
      columns.add(parser.readValueAs(SelectColumn.class));
    }
    return new SelectClauseColumns(columns);
  }

  private static SelectClauseColumns deserializeXml(JsonParser parser)
      throws IOException {
    if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
      throw new InternalException("Start object expected deserializing select clause from xml");
    }
    List<SelectColumn<?>> columns = new ArrayList<>(5);
    while ("COLUMN".equals(parser.nextFieldName())) {
      parser.nextToken();
      columns.add(parser.readValueAs(SelectColumn.class));
    }
    if (parser.currentToken() != JsonToken.END_OBJECT) {
      throw new InternalException("End object expected after last COLUMN deserializing select "
          + "clause from xml");
    }
    return new SelectClauseColumns(columns);
  }

  @Override
  public SelectClauseColumns deserialize(JsonParser parser,
      DeserializationContext context) throws IOException {
    if (parser.getCodec() instanceof XmlMapper) {
      return deserializeXml(parser);
    }
    return deserializeJson(parser);
  }
}
