package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class LiteralNVarcharTest {

  @Test
  void getBindsTest() {
    var literal = new LiteralNVarchar("test5");
    assertThat(literal.getBinds()).isEmpty();
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new LiteralNVarchar("testString"),
            "{\"VALUE\":\"testString\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERALNVARCHAR><VALUE>testString</VALUE></LITERALNVARCHAR>"}
        , new Object[]{new LiteralNVarchar("\nfirst\nsecond\n"),
            "{\"VALUE\":\"\\nfirst\\nsecond\\n\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERALNVARCHAR><VALUE>\nfirst\nsecond\n</VALUE></LITERALNVARCHAR>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(LiteralNVarchar value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(LiteralNVarchar value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, LiteralNVarchar.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(LiteralNVarchar value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(LiteralNVarchar value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, LiteralNVarchar.class))
        .isEqualTo(value);
  }
}