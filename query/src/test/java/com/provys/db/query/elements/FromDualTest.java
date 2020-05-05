package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class FromDualTest {

  @Test
  void getAliasTest() {
    var alias = SimpleName.valueOf("al");
    var fromElem = new FromDual(alias);
    assertThat(fromElem.getAlias()).isEqualTo(alias);
  }

  @Test
  void getAliasNoAliasTest() {
    var fromElem = new FromDual(null);
    assertThat(fromElem.getAlias()).isEqualTo(SimpleName.valueOf("dual"));
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{
            new FromDual(SimpleName.valueOf("rec")),
            "{\"ALIAS\":\"rec\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FROMDUAL><ALIAS>rec</ALIAS></FROMDUAL>"}
        , new Object[]{new FromDual(null),
            "{}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FROMDUAL><ALIAS/></FROMDUAL>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(FromDual value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(FromDual value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, FromDual.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(FromDual value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(FromDual value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, FromDual.class))
        .isEqualTo(value);
  }
}