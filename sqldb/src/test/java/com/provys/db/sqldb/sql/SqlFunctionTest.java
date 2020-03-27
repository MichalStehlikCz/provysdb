package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.sql.BindName;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.BindWithPos;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Function;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlFunctionTest {

  public static final BindWithPos[] BIND_WITH_POS = new BindWithPos[]{};

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new SqlFunction(SqlContextImpl.getNoDbInstance(), Function.STRING_CHR,
            new SqlExpression[]{new SqlLiteral(5)}, null, null),
            "CHR(5)", BIND_WITH_POS}
        , new Object[]{new SqlFunction(SqlContextImpl.getNoDbInstance(), Function.STRING_CONCAT,
            new SqlExpression[]{new SqlLiteral("first"), new SqlLiteral("second"),
                new SqlExpressionBind(new BindVariable("bind1", String.class, null))},
            null, null), "'first'||'second'||?",
            new BindWithPos[]{new BindWithPos(BindName.valueOf("bind1"), String.class,
                List.of(1))}}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(SqlElement value, String result, BindWithPos[] bindsWithPos) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    value.append(builder);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).containsExactlyInAnyOrder(bindsWithPos);
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new SqlFunction(SqlContextImpl.getNoDbInstance(), Function.STRING_CHR,
            new SqlExpression[]{new SqlLiteral(5)}, null, null),
            "{\"FUNCTION\":\"STRING_CHR\",\"ARGUMENTS\":[{\"LITERAL\":{\"INTEGER\":5}}]}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FUNCTION><FUNCTION>STRING_CHR</FUNCTION>"
                + "<ARGUMENTS><ARGUMENT><LITERAL><INTEGER>5</INTEGER></LITERAL></ARGUMENT>"
                + "</ARGUMENTS></FUNCTION>"}
        , new Object[]{new SqlFunction(SqlContextImpl.getNoDbInstance(), Function.STRING_CONCAT,
            new SqlExpression[]{new SqlLiteral("first"), new SqlLiteral("second"),
                new SqlExpressionBind(new BindVariable("bind1", String.class, null))},
            null, null),
            "{\"FUNCTION\":\"STRING_CONCAT\",\"ARGUMENTS\":[{\"LITERAL\":{\"STRING\":\"first\"}},"
                + "{\"LITERAL\":{\"STRING\":\"second\"}},"
                + "{\"BINDEXPRESSION\":{\"NAME\":\"BIND1\",\"TYPE\":\"STRING\"}}]}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FUNCTION><FUNCTION>STRING_CONCAT</FUNCTION><ARGUMENTS><ARGUMENT>"
                + "<LITERAL><STRING>first</STRING></LITERAL></ARGUMENT><ARGUMENT><LITERAL>"
                + "<STRING>second</STRING></LITERAL></ARGUMENT><ARGUMENT><BINDEXPRESSION>"
                + "<NAME>BIND1</NAME><TYPE>STRING</TYPE><VALUE/></BINDEXPRESSION></ARGUMENT>"
                + "</ARGUMENTS></FUNCTION>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SqlFunction value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SqlFunction value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SqlFunction.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SqlFunction value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SqlFunction value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SqlFunction.class))
        .isEqualTo(value);
  }
}