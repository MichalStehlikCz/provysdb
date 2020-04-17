package com.provys.db.query.elements;

import static com.provys.db.query.functions.ConditionalOperator.COND_AND;
import static com.provys.db.query.functions.ConditionalOperator.COND_EQ_NONNULL;
import static com.provys.db.query.functions.ConditionalOperator.COND_GT_NONNULL;
import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtDate;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ConditionOperationTest {

  private static final ElementFactory FACTORY = ElementFactory.getInstance();

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new ConditionOperation(COND_EQ_NONNULL,
            List.of(FACTORY.bind(String.class, "bind"), FACTORY.literal("text"))),
            "{\"OPERATOR\":\"COND_EQ_NONNULL\",\"ARGUMENTS\":[{\"BIND\":{\"NAME\":\"BIND\","
                + "\"TYPE\":\"STRING\"}},{\"LITERAL\":{\"STRING\":\"text\"}}]}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CONDOP><OPERATOR>COND_EQ_NONNULL</OPERATOR>"
                + "<ARGUMENTS><ARGUMENT><BIND><NAME>BIND</NAME><TYPE>STRING</TYPE><VALUE/></BIND>"
                + "</ARGUMENT><ARGUMENT><LITERAL><STRING>text</STRING></LITERAL></ARGUMENT>"
                + "</ARGUMENTS></CONDOP>"}
        , new Object[]{new ConditionOperation(COND_AND, List.of(
            new ConditionOperation(COND_EQ_NONNULL,
                List.of(FACTORY.bind(String.class, "bind"), FACTORY.literal("text"))),
            new ConditionOperation(COND_GT_NONNULL, List.of(FACTORY.bind(DtDate.class, "dateBind"),
                FACTORY.literal(DtDate.of(2011, 5, 6)))))),
            "{\"OPERATOR\":\"COND_AND\",\"ARGUMENTS\":[{\"CONDOP\":{\"OPERATOR\":"
                + "\"COND_EQ_NONNULL\",\"ARGUMENTS\":[{\"BIND\":{\"NAME\":\"BIND\",\"TYPE\":"
                + "\"STRING\"}},{\"LITERAL\":{\"STRING\":\"text\"}}]}},{\"CONDOP\":{\"OPERATOR\":"
                + "\"COND_GT_NONNULL\",\"ARGUMENTS\":[{\"BIND\":{\"NAME\":\"DATEBIND\",\"TYPE\":"
                + "\"DATE\"}},{\"LITERAL\":{\"DATE\":\"2011-05-06\"}}]}}]}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CONDOP><OPERATOR>COND_AND</OPERATOR>"
                + "<ARGUMENTS><ARGUMENT><CONDOP><OPERATOR>COND_EQ_NONNULL</OPERATOR><ARGUMENTS>"
                + "<ARGUMENT><BIND><NAME>BIND</NAME><TYPE>STRING</TYPE><VALUE/></BIND></ARGUMENT>"
                + "<ARGUMENT><LITERAL><STRING>text</STRING></LITERAL></ARGUMENT></ARGUMENTS>"
                + "</CONDOP></ARGUMENT><ARGUMENT><CONDOP><OPERATOR>COND_GT_NONNULL</OPERATOR>"
                + "<ARGUMENTS><ARGUMENT><BIND><NAME>DATEBIND</NAME><TYPE>DATE</TYPE><VALUE/></BIND>"
                + "</ARGUMENT><ARGUMENT><LITERAL><DATE>2011-05-06</DATE></LITERAL></ARGUMENT>"
                + "</ARGUMENTS></CONDOP></ARGUMENT></ARGUMENTS></CONDOP>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(ConditionOperation value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(ConditionOperation value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, ConditionOperation.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(ConditionOperation value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(ConditionOperation value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, ConditionOperation.class))
        .isEqualTo(value);
  }
}