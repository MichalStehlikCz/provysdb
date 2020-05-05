package com.provys.db.sqlquery.query;

import static com.provys.db.query.functions.BuiltInFunction.ANY_NVL;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CHR;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CONCAT;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.EXPR_ADD;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.EXPR_BRACKET;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.IN_BRACKET;
import static org.assertj.core.api.Assertions.*;

import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.sqlquery.literals.SqlLiteralTypeHandlerMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class SqlFunctionMapImplTest {

  @Test
  void getTemplateTest() {
    var chrAppender = new SqlTemplateAppender("CHR({0})", EXPR_BRACKET, IN_BRACKET);
    var concatAppender = SqlRecursiveAppender.forTemplate("{0}||{1}", EXPR_ADD, EXPR_ADD);
    var map = new SqlBuiltInMapImpl(Map.of(STRING_CHR, chrAppender, STRING_CONCAT, concatAppender));
    assertThat(map.getAppender(STRING_CONCAT))
        .isEqualTo(concatAppender);
  }

  @Test
  void getTemplateFailTest() {
    var chrAppender = new SqlTemplateAppender("CHR({0})", EXPR_BRACKET, IN_BRACKET);
    var concatAppender = SqlRecursiveAppender.forTemplate("{0}||{1}", EXPR_ADD, EXPR_ADD);
    var map = new SqlBuiltInMapImpl(Map.of(STRING_CHR, chrAppender, STRING_CONCAT, concatAppender));
    assertThatThrownBy(() -> map.getAppender(ANY_NVL))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("Appender not found");
  }

  private static final BindName bindName1 = BindName.valueOf("name1");

  private static void appendArg0(QueryConsumer builder) {
    builder.bind(Integer.class, new BindVariable(bindName1, Integer.class, 4));
  }

  @Test
  void appendFunctionTest() {
    var chrAppender = new SqlTemplateAppender("CHR({0})", EXPR_BRACKET, IN_BRACKET);
    var map = new SqlBuiltInMapImpl(Map.of(STRING_CHR, chrAppender));
    var builder = new DefaultSqlBuilder(SqlLiteralTypeHandlerMap.getDefaultMap(), map);
    map.append(STRING_CHR, List.of(SqlFunctionMapImplTest::appendArg0), builder);
    assertThat(builder.getSql()).isEqualTo("CHR(?)");
    assertThat(builder.getBindsWithPos())
        .containsExactlyInAnyOrder(new BindWithPos(bindName1, Integer.class, List.of(1)));
  }
}