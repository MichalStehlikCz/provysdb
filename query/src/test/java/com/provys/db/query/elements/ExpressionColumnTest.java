package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.SegmentedName;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ExpressionColumnTest {

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new ExpressionColumn<>(null, SimpleName.valueOf("column"),
            Integer.class),
            "{\"COLUMN\":\"column\",\"TYPE\":\"INTEGER\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<COLUMN><TABLE/><COLUMN>column</COLUMN><TYPE>INTEGER</TYPE></COLUMN>"}
        , new Object[]{new ExpressionColumn<>(SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column"), String.class),
            "{\"TABLE\":\"scheme.table\",\"COLUMN\":\"column\",\"TYPE\":\"STRING\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMN><TABLE>scheme.table</TABLE>"
                + "<COLUMN>column</COLUMN><TYPE>STRING</TYPE></COLUMN>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(ExpressionColumn<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(ExpressionColumn<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, ExpressionColumn.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(ExpressionColumn<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(ExpressionColumn<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, ExpressionColumn.class))
        .isEqualTo(value);
  }
}