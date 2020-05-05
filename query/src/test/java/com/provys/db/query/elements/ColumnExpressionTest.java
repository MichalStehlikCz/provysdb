package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ColumnExpressionTest {

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new ColumnExpression<>(new Literal<>(5), null),
            "{\"EXPRESSION\":{\"LITERAL\":{\"INTEGER\":5}}}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMN><EXPRESSION><LITERAL>"
                + "<INTEGER>5</INTEGER></LITERAL></EXPRESSION><ALIAS/></COLUMN>"}
        , new Object[]{new ColumnExpression<>(
            ExpressionBind.ofBindVariable(new BindVariable("name", String.class, null)),
            SimpleName.valueOf("alias")),
            "{\"EXPRESSION\":{\"BIND\":{\"NAME\":\"NAME\",\"TYPE\":\"STRING\"}},"
                + "\"ALIAS\":\"alias\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMN><EXPRESSION><BIND>"
                + "<NAME>NAME</NAME><TYPE>STRING</TYPE><VALUE/></BIND></EXPRESSION>"
                + "<ALIAS>alias</ALIAS></COLUMN>"
        }
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(ColumnExpression<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(ColumnExpression<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, ColumnExpression.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(ColumnExpression<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(ColumnExpression<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, ColumnExpression.class))
        .isEqualTo(value);
  }
}