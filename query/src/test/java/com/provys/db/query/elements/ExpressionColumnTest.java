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
        new Object[]{new ExpressionColumn<>(Integer.class, null, SimpleName.valueOf("column")),
            "{\"TYPE\":\"INTEGER\",\"COLUMN\":\"column\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<COLUMN><TYPE>INTEGER</TYPE><TABLE/><COLUMN>column</COLUMN></COLUMN>"}
        , new Object[]{new ExpressionColumn<>(String.class, SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column")),
            "{\"TYPE\":\"STRING\",\"TABLE\":\"scheme.table\",\"COLUMN\":\"column\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMN><TYPE>STRING</TYPE>"
                + "<TABLE>scheme.table</TABLE><COLUMN>column</COLUMN></COLUMN>"}
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