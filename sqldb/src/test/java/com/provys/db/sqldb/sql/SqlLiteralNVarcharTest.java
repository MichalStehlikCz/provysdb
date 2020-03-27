package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtUid;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import java.io.IOException;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SqlLiteralNVarcharTest {

  @Test
  void getTypeTest() {
    var context = mock(SqlContext.class);
    var literal = new SqlLiteralNVarchar(context, "test1");
    assertThat(literal.getType()).isSameAs(String.class);
  }

  @Test
  void transferSameTest() {
    @SuppressWarnings("unchecked")
    var context = (SqlContext<?, ?, ?, ?, ?, ?, SqlExpression>) mock(SqlContext.class);
    var literal = new SqlLiteralNVarchar(context, "test2");
    assertThat(literal.transfer(context, null, null)).isSameAs(literal);
  }

  @Test
  void transferDifferentTest() {
    var context = mock(SqlContext.class);
    var text = "test3";
    var literal = new SqlLiteralNVarchar(context, text);
    @SuppressWarnings("unchecked")
    var targetContext = (Context<?, ?, ?, ?, ?, ?, Expression>) mock(Context.class);
    var clone = mock(Expression.class);
    when(targetContext.literalNVarchar(text)).thenReturn(clone);
    assertThat(literal.transfer(targetContext, null, null)).isEqualTo(clone);
  }

  @Test
  void getContextTest() {
    var context = mock(SqlContext.class);
    var literal = new SqlLiteralNVarchar(context, "test4");
    assertThat(literal.getContext()).isSameAs(context);
  }

  @Test
  void getBindsTest() {
    var context = mock(SqlContext.class);
    var literal = new SqlLiteralNVarchar(context, "test5");
    assertThat(literal.getBinds()).isEmpty();
  }

  static Stream<@Nullable Object[]> appendTest() {
    return Stream.of(
        new Object[]{"test6", "N'test6'"}
        , new Object[]{"first'second", "N'first''second'"}
        , new Object[]{"first\nsecond", "N'first'||CHR(10)||N'second'"}
        , new Object[]{"\nfirst\nsecond\n", "CHR(10)||N'first'||CHR(10)||N'second'||CHR(10)"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(String text, String result) {
    var context = SqlContextImpl.getNoDbInstance();
    var literal = new SqlLiteralNVarchar(context, text);
    var builder = CodeBuilderFactory.getCodeBuilder();
    literal.append(builder);
    assertThat(builder.build()).isEqualTo(result);
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new SqlLiteralNVarchar(SqlContextImpl.getNoDbInstance(), "testString"),
            "{\"VALUE\":\"testString\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERALNVARCHAR><VALUE>testString</VALUE></LITERALNVARCHAR>"}
        , new Object[]{new SqlLiteralNVarchar("\nfirst\nsecond\n"),
            "{\"VALUE\":\"\\nfirst\\nsecond\\n\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERALNVARCHAR><VALUE>\nfirst\nsecond\n</VALUE></LITERALNVARCHAR>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SqlLiteralNVarchar value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SqlLiteralNVarchar value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SqlLiteralNVarchar.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SqlLiteralNVarchar value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SqlLiteralNVarchar value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SqlLiteralNVarchar.class))
        .isEqualTo(value);
  }
}