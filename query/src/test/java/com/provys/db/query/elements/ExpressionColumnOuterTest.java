package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.SegmentedName;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ExpressionColumnOuterTest {

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new ExpressionColumnOuter<>(
            new ExpressionColumn<>(Integer.class, null, SimpleName.valueOf("column"))),
            "{\"TYPE\":\"INTEGER\",\"COLUMN\":\"column\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<COLUMNOUTER><TYPE>INTEGER</TYPE><TABLE/><COLUMN>column</COLUMN></COLUMNOUTER>"}
        , new Object[]{new ExpressionColumnOuter<>(
            new ExpressionColumn<>(String.class, SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column"))),
            "{\"TYPE\":\"STRING\",\"TABLE\":\"scheme.table\",\"COLUMN\":\"column\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMNOUTER><TYPE>STRING</TYPE>"
                + "<TABLE>scheme.table</TABLE><COLUMN>column</COLUMN></COLUMNOUTER>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(ExpressionColumnOuter<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(ExpressionColumnOuter<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, ExpressionColumnOuter.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(ExpressionColumnOuter<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(ExpressionColumnOuter<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, ExpressionColumnOuter.class))
        .isEqualTo(value);
  }
}
