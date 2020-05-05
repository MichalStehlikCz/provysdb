package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.exception.InternalException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.BindVariable;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ExpressionBindTest {

  @Test
  void typeMismatchTest() {
    var bind = new BindVariable("name", Double.class, 5.0);
    assertThatThrownBy(() -> new ExpressionBind<>(Integer.class, bind))
        .isInstanceOf(InternalException.class);
  }

  @Test
  void getTypeTest() {
    var bind = new BindVariable("name", Integer.class, 5);
    var expression = ExpressionBind.ofBindVariable(bind);
    assertThat(expression.getType()).isSameAs(Integer.class);
  }

  @Test
  void getBindsTest() {
    var bind = new BindVariable("name", String.class, null);
    var expression = new ExpressionBind<>(String.class, bind);
    assertThat(expression.getBinds()).containsExactlyInAnyOrder(bind);
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{ExpressionBind.ofBindVariable(
            new BindVariable("name1", Integer.class, 5)),
            "{\"NAME\":\"NAME1\",\"TYPE\":\"INTEGER\",\"VALUE\":5}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BIND><NAME>NAME1</NAME>"
                + "<TYPE>INTEGER</TYPE><VALUE>5</VALUE></BIND>"}
        , new Object[]{ExpressionBind.ofBindVariable(
            new BindVariable("name2", String.class, null)),
            "{\"NAME\":\"NAME2\",\"TYPE\":\"STRING\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BIND><NAME>NAME2</NAME>"
                + "<TYPE>STRING</TYPE><VALUE/></BIND>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(ExpressionBind<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(ExpressionBind<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, ExpressionBind.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(ExpressionBind<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(ExpressionBind<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, ExpressionBind.class))
        .isEqualTo(value);
  }
}