package com.provys.db.sqlquery.queryold;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.query.Context;
import com.provys.db.query.Expression;
import com.provys.db.sqlquery.codebuilder.CodeBuilderFactory;
import com.provys.db.sqlquery.query.SqlLiteralNVarchar;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LiteralSqlBuilderNVarcharTest {

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

}