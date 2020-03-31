package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtUid;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class LiteralTest {
  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new Literal<>("testString"),
            "{\"STRING\":\"testString\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERAL><STRING>testString</STRING></LITERAL>"}
        , new Object[]{new Literal<>(5),
            "{\"INTEGER\":5}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERAL><INTEGER>5</INTEGER></LITERAL>"}
        , new Object[]{new Literal<>(DtUid.valueOf("123456")),
            "{\"UID\":123456}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERAL><UID>123456</UID></LITERAL>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(Literal<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(Literal<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, Literal.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(Literal<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(Literal<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, Literal.class))
        .isEqualTo(value);
  }
}