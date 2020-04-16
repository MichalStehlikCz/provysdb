package com.provys.db.sqlquery.query;

import static com.provys.db.query.functions.BuiltInFunction.STRING_CHR;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CONCAT;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.EXPR_ADD;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.EXPR_BRACKET;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.IN_BRACKET;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.sqlquery.literals.SqlLiteralTypeHandlerMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlRecursiveAppenderTest {

  private static final BindName bindName1 = BindName.valueOf("name1");
  private static final BindName bindName2 = BindName.valueOf("name2");

  private static void appendArg0(QueryConsumer builder) {
    builder.bind(Integer.class, new BindVariable(bindName1, Integer.class, 4));
  }

  private static void appendArg1(QueryConsumer builder) {
    builder.bind(Integer.class, new BindVariable(bindName1, Integer.class, 4));
    builder.bind(String.class, new BindVariable(bindName2, String.class, null));
  }

  private static void appendArg2(QueryConsumer builder) {
    builder.bind(String.class, new BindVariable(bindName2, String.class, null));
  }

  @Test
  void appendTest() {
    // note that arguments in concat appender are switched against normal
    var map = mock(SqlBuiltInMap.class);
    var builder = new DefaultSqlBuilder(SqlLiteralTypeHandlerMap.getDefaultMap(), map);
    var concatAppender = SqlRecursiveAppender.forTemplate("{0}||{1}", EXPR_ADD, EXPR_ADD);
    concatAppender.append(
        List.of(SqlRecursiveAppenderTest::appendArg0, SqlRecursiveAppenderTest::appendArg1,
            SqlRecursiveAppenderTest::appendArg2), builder);
    assertThat(builder.getSql()).isEqualTo("?||??||?");
    assertThat(builder.getBindsWithPos())
        .containsExactlyInAnyOrder(new BindWithPos(bindName1, Integer.class, List.of(1, 2)),
            new BindWithPos(bindName2, String.class, List.of(3, 4)));
  }

  @Test
  void appendSwitchedTest() {
    // note that arguments in concat appender are switched against normal
    var map = mock(SqlBuiltInMap.class);
    var builder = new DefaultSqlBuilder(SqlLiteralTypeHandlerMap.getDefaultMap(), map);
    var concatAppender = SqlRecursiveAppender.forTemplate("{1}||{0}", EXPR_ADD, EXPR_ADD);
    concatAppender.append(
        List.of(SqlRecursiveAppenderTest::appendArg0, SqlRecursiveAppenderTest::appendArg1,
            SqlRecursiveAppenderTest::appendArg2), builder);
    assertThat(builder.getSql()).isEqualTo("?||??||?");
    assertThat(builder.getBindsWithPos())
        .containsExactlyInAnyOrder(new BindWithPos(bindName1, Integer.class, List.of(2, 4)),
            new BindWithPos(bindName2, String.class, List.of(1, 3)));
  }
}