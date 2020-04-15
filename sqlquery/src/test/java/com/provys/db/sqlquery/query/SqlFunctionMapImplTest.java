package com.provys.db.sqlquery.query;

import static com.provys.db.query.functions.BuiltInFunction.ANY_NVL;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CHR;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CONCAT;
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
    var map = new SqlFunctionMapImpl(Map.of(STRING_CHR, "CHR({0})",
        STRING_CONCAT, "{0}||{1}"));
    assertThat(map.getTemplate(STRING_CONCAT)).isEqualTo("{0}||{1}");
  }

  @Test
  void getTemplateFailTest() {
    var map = new SqlFunctionMapImpl(Map.of(STRING_CHR, "CHR({0})",
        STRING_CONCAT, "{0}||{1}"));
    assertThatThrownBy(() -> map.getTemplate(ANY_NVL))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("Template not found");
  }

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
  void appendOperatorTest() {
    var map = new SqlFunctionMapImpl(Map.of(STRING_CHR, "CHR({0})",
        STRING_CONCAT, "{1}||{0}"));
    var builder = new DefaultSqlBuilder(SqlLiteralTypeHandlerMap.getDefaultMap(), map);
    map.append(STRING_CONCAT,
        List.of(SqlFunctionMapImplTest::appendArg0, SqlFunctionMapImplTest::appendArg1,
            SqlFunctionMapImplTest::appendArg2), builder);
    assertThat(builder.getSql()).isEqualTo("?||??||?");
    assertThat(builder.getBindsWithPos())
        .containsExactlyInAnyOrder(new BindWithPos(bindName1, Integer.class, List.of(2, 4)),
            new BindWithPos(bindName2, String.class, List.of(1, 3)));
  }

  @Test
  void appendFunctionTest() {
    var map = new SqlFunctionMapImpl(Map.of(STRING_CHR, "CHR({0})",
        STRING_CONCAT, "{1}||{0}"));
    var builder = new DefaultSqlBuilder(SqlLiteralTypeHandlerMap.getDefaultMap(), map);
    map.append(STRING_CHR, List.of(SqlFunctionMapImplTest::appendArg0), builder);
    assertThat(builder.getSql()).isEqualTo("CHR(?)");
    assertThat(builder.getBindsWithPos())
        .containsExactlyInAnyOrder(new BindWithPos(bindName1, Integer.class, List.of(1)));
  }
}