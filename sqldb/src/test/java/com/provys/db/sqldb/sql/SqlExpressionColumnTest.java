package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.SegmentedName;
import com.provys.db.sql.SimpleName;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlExpressionColumnTest {

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new SqlExpressionColumn(SqlContextImpl.getNoDbInstance(), null,
            SimpleName.valueOf("column"), Integer.class, null),
            "column"}
        , new Object[]{new SqlExpressionColumn(SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column"), String.class),
            "scheme.table.column"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(SqlElement value, String result) {
    CodeBuilder builder = CodeBuilderFactory.getCodeBuilder();
    value.append(builder);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new SqlExpressionColumn(SqlContextImpl.getNoDbInstance(), null,
            SimpleName.valueOf("column"), Integer.class, null),
            "{\"COLUMN\":\"column\",\"TYPE\":\"INTEGER\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<COLUMN><TABLE/><COLUMN>column</COLUMN><TYPE>INTEGER</TYPE></COLUMN>"}
        , new Object[]{new SqlExpressionColumn(SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column"), String.class),
            "{\"TABLE\":\"scheme.table\",\"COLUMN\":\"column\",\"TYPE\":\"STRING\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMN><TABLE>scheme.table</TABLE>"
                + "<COLUMN>column</COLUMN><TYPE>STRING</TYPE></COLUMN>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SqlExpressionColumn value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SqlExpressionColumn value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SqlExpressionColumn.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SqlExpressionColumn value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SqlExpressionColumn value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SqlExpressionColumn.class))
        .isEqualTo(value);
  }
}