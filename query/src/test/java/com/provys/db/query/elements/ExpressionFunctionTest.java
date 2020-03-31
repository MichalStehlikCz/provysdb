package com.provys.db.query.elements;

import static com.provys.db.query.elements.Function.STRING_CHR;
import static com.provys.db.query.elements.Function.STRING_CONCAT;
import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.BindVariable;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ExpressionFunctionTest {

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new ExpressionFunction<>(String.class, STRING_CHR,
            List.of(new Literal<>(5))),
            "{\"FUNCTION\":\"STRING_CHR\",\"ARGUMENTS\":[{\"LITERAL\":{\"INTEGER\":5}}]}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FUNCTION><FUNCTION>STRING_CHR</FUNCTION>"
                + "<ARGUMENTS><ARGUMENT><LITERAL><INTEGER>5</INTEGER></LITERAL></ARGUMENT>"
                + "</ARGUMENTS></FUNCTION>"}
        , new Object[]{new ExpressionFunction<>(String.class, STRING_CONCAT,
            List.of(new Literal<>("first"), new Literal<>("second"),
                ExpressionBind.ofBindVariable(new BindVariable("bind1", String.class, null)))),
            "{\"FUNCTION\":\"STRING_CONCAT\",\"ARGUMENTS\":[{\"LITERAL\":{\"STRING\":\"first\"}},"
                + "{\"LITERAL\":{\"STRING\":\"second\"}},"
                + "{\"BIND\":{\"NAME\":\"BIND1\",\"TYPE\":\"STRING\"}}]}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FUNCTION><FUNCTION>STRING_CONCAT</FUNCTION><ARGUMENTS><ARGUMENT>"
                + "<LITERAL><STRING>first</STRING></LITERAL></ARGUMENT><ARGUMENT><LITERAL>"
                + "<STRING>second</STRING></LITERAL></ARGUMENT><ARGUMENT><BIND>"
                + "<NAME>BIND1</NAME><TYPE>STRING</TYPE><VALUE/></BIND></ARGUMENT>"
                + "</ARGUMENTS></FUNCTION>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(ExpressionFunction<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(ExpressionFunction<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, ExpressionFunction.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(ExpressionFunction<?> value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(ExpressionFunction<?> value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, ExpressionFunction.class))
        .isEqualTo(value);
  }
}