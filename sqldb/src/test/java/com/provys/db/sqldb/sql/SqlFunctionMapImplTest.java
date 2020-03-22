package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;

import com.provys.db.sql.BindName;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.BindWithPos;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Function;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class SqlFunctionMapImplTest {

  @Test
  void getTemplateTest() {
    var map = new SqlFunctionMapImpl(Map.of(Function.STRING_CHR, "CHR({0})",
        Function.STRING_CONCAT, "{0}||{1}"));
    assertThat(map.getTemplate(Function.STRING_CONCAT)).isEqualTo("{0}||{1}");
  }

  @Test
  void getTemplateFailTest() {
    var map = new SqlFunctionMapImpl(Map.of(Function.STRING_CHR, "CHR({0})",
        Function.STRING_CONCAT, "{0}||{1}"));
    assertThatThrownBy(() -> map.getTemplate(Function.ANY_NVL))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("Template not found");
  }

  private static final BindName bindName1 = BindName.valueOf("name1");
  private static final BindName bindName2 = BindName.valueOf("name2");

  private static void appendArg0(CodeBuilder builder) {
    builder.append("arg0");
    builder.addBind(new BindVariable(bindName1, Integer.class, 4));
  }

  private static void appendArg1(CodeBuilder builder) {
    builder.append("arg1");
    builder.addBind(new BindVariable(bindName1, Integer.class, 4));
    builder.addBind(new BindVariable(bindName2, String.class, null));
  }

  private static void appendArg2(CodeBuilder builder) {
    builder.append("arg2");
    builder.addBind(new BindVariable(bindName2, String.class, null));
  }

  @Test
  void appendOperatorTest() {
    var map = new SqlFunctionMapImpl(Map.of(Function.STRING_CHR, "CHR({0})",
        Function.STRING_CONCAT, "{1}||{0}"));
    var builder = CodeBuilderFactory.getCodeBuilder();
    map.append(Function.STRING_CONCAT,
        List.of(SqlFunctionMapImplTest::appendArg0, SqlFunctionMapImplTest::appendArg1,
            SqlFunctionMapImplTest::appendArg2), builder);
    assertThat(builder.build()).isEqualTo("arg2||arg1||arg0");
    assertThat(builder.getBindsWithPos())
        .containsExactlyInAnyOrder(new BindWithPos(bindName1, Integer.class, List.of(2, 4)),
            new BindWithPos(bindName2, String.class, List.of(1, 3)));
  }

  @Test
  void appendFunctionTest() {
    var map = new SqlFunctionMapImpl(Map.of(Function.STRING_CHR, "CHR({0})",
        Function.STRING_CONCAT, "{1}||{0}"));
    var builder = CodeBuilderFactory.getCodeBuilder();
    map.append(Function.STRING_CHR, List.of(SqlFunctionMapImplTest::appendArg0), builder);
    assertThat(builder.build()).isEqualTo("CHR(arg0)");
    assertThat(builder.getBindsWithPos())
        .containsExactlyInAnyOrder(new BindWithPos(bindName1, Integer.class, List.of(1)));
  }
}