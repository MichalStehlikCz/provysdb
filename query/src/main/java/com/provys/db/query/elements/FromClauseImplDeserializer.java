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

public class FromClauseImplDeserializer extends StdDeserializer<FromClauseImpl> {

  private static final long serialVersionUID = 8810246829625837188L;

  protected FromClauseImplDeserializer() {
    super(FromClauseImpl.class);
  }

  private static FromClauseImpl deserializeJson(JsonParser parser)
      throws IOException {
    if (parser.getCurrentToken() != JsonToken.START_ARRAY) {
      throw new InternalException("Start array expected deserializing from clause");
    }
    List<FromElement> elements = new ArrayList<>(5);
    while (parser.nextToken() != JsonToken.END_ARRAY) {
      elements.add(parser.readValueAs(FromElement.class));
    }
    return new FromClauseImpl(elements);
  }

  private static FromClauseImpl deserializeXml(JsonParser parser)
      throws IOException {
    if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
      throw new InternalException("Start object expected deserializing from clause from xml");
    }
    List<FromElement> elements = new ArrayList<>(5);
    while ("ELEM".equals(parser.nextFieldName())) {
      parser.nextToken();
      elements.add(parser.readValueAs(FromElement.class));
    }
    if (parser.currentToken() != JsonToken.END_OBJECT) {
      throw new InternalException("End object expected after last ELEM deserializing from clause"
          + " from xml");
    }
    return new FromClauseImpl(elements);
  }

  @Override
  public FromClauseImpl deserialize(JsonParser parser,
      DeserializationContext context) throws IOException {
    if (parser.getCodec() instanceof XmlMapper) {
      return deserializeXml(parser);
    }
    return deserializeJson(parser);
  }
}
