package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.common.datatype.DtDate;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
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
    assertThat(literal.transfer(context)).isSameAs(literal);
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
    assertThat(literal.transfer(targetContext)).isEqualTo(clone);
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
        , new Object[]{DtDate.PRIV, "DATE'1000-01-02'"}
        , new Object[]{DtDate.ME, "DATE'1000-01-01'"}
        , new Object[]{DtDate.MIN, "DATE'1000-01-03'"}
        , new Object[]{DtDate.MAX, "DATE'5000-01-01'"}
        , new @Nullable Object[]{null, "NULL"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(String text, String result) {
    var context = mock(SqlContext.class);
    var literal = new SqlLiteralNVarchar(context, text);
    var builder = CodeBuilderFactory.getCodeBuilder();
    literal.append(builder);
    assertThat(builder.build()).isEqualTo(result);
  }
}