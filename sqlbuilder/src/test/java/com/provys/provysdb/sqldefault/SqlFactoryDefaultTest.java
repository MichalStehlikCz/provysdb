package com.provys.provysdb.sqldefault;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sql.CodeBuilderFactory;
import com.provys.provysdb.sql.SqlFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class SqlFactoryDefaultTest {

  private final SqlFactory sql = SqlFactoryDefault.getInstance();

  @Test
  void literalString() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal("Test \"string\" 'constant'");
    assertThat(literal.getValue()).isEqualTo("Test \"string\" 'constant'");
    assertThat(literal.getType()).isEqualTo(String.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("'Test \"string\" ''constant'''");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalNVarchar() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literalNVarchar("Test \"string\" 'constant'");
    assertThat(literal.getValue()).isEqualTo("Test \"string\" 'constant'");
    assertThat(literal.getType()).isEqualTo(String.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("N'Test \"string\" ''constant'''");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalByte() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal((byte) 5);
    assertThat(literal.getValue()).isEqualTo((byte) 5);
    assertThat(literal.getType()).isEqualTo(Byte.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("5");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalShort() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal((short) 5);
    assertThat(literal.getValue()).isEqualTo((short) 5);
    assertThat(literal.getType()).isEqualTo(Short.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("5");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalInt() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(5);
    assertThat(literal.getValue()).isEqualTo(5);
    assertThat(literal.getType()).isEqualTo(Integer.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("5");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalLong() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(5L);
    assertThat(literal.getValue()).isEqualTo(5L);
    assertThat(literal.getType()).isEqualTo(Long.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("5");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalBigInteger() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(BigInteger.valueOf(123456789123L));
    assertThat(literal.getValue()).isEqualTo(BigInteger.valueOf(123456789123L));
    assertThat(literal.getType()).isEqualTo(BigInteger.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("123456789123");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalFloat() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal((float) 1245.2684);
    assertThat(literal.getValue()).isEqualTo((float) 1245.2684);
    assertThat(literal.getType()).isEqualTo(Float.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("1245.2684");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalDouble() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(1245.268471465942);
    assertThat(literal.getValue()).isEqualTo(1245.268471465942);
    assertThat(literal.getType()).isEqualTo(Double.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("1245.268471465942");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalBigDecimal() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(BigDecimal.valueOf(1245.268471465942));
    assertThat(literal.getValue()).isEqualTo(BigDecimal.valueOf(1245.268471465942));
    assertThat(literal.getType()).isEqualTo(BigDecimal.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("1245.268471465942");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalBooleanTrue() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(true);
    assertThat(literal.getValue()).isEqualTo(true);
    assertThat(literal.getType()).isEqualTo(Boolean.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("'Y'");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalBooleanFalse() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(false);
    assertThat(literal.getValue()).isEqualTo(false);
    assertThat(literal.getType()).isEqualTo(Boolean.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("'N'");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalDtUid() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(DtUid.valueOf("123456789123456789"));
    assertThat(literal.getValue()).isEqualTo(DtUid.valueOf("123456789123456789"));
    assertThat(literal.getType()).isEqualTo(DtUid.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("123456789123456789");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalDtDate() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(DtDate.of(1957, 11, 24));
    assertThat(literal.getValue()).isEqualTo(DtDate.of(1957, 11, 24));
    assertThat(literal.getType()).isEqualTo(DtDate.class);
    literal.addSql(builder);
    assertThat(builder.build()).isEqualTo("DATE'1957-11-24'");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void literalDtDateTime() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var literal = sql.literal(DtDateTime.of(2020, 5, 18, 15, 24, 35));
    assertThat(literal.getValue()).isEqualTo(DtDateTime.of(2020, 5, 18, 15, 24, 35));
    assertThat(literal.getType()).isEqualTo(DtDateTime.class);
    literal.addSql(builder);
    assertThat(builder.build())
        .isEqualTo("TO_DATE('2020-05-18T15:24:35', 'YYYY-MM-DD\"T\"HH24:MI:SS')");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }
/*
  @Test
  void eq() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var first = com.provys.db.sql.literal(100);
    var second = com.provys.db.sql.literal(200);
    var condition = com.provys.db.sql.eq(first, second);
    assertThat(condition.isEmpty()).isFalse();
    condition.addSql(builder);
    assertThat(builder.build()).isEqualTo("(100=200)");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void eqSame() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var first = com.provys.db.sql.literal(100);
    var second = com.provys.db.sql.literal(100);
    var condition = com.provys.db.sql.eq(first, second);
    assertThat(condition.isEmpty()).isTrue();
  }

  @Test
  void isNull() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var expression = com.provys.db.sql.literal(100);
    var condition = com.provys.db.sql.isNull(expression);
    assertThat(condition.isEmpty()).isFalse();
    condition.addSql(builder);
    assertThat(builder.build()).isEqualTo("(100 IS NULL)");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void nvl() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var first = com.provys.db.sql.literal(100);
    var second = com.provys.db.sql.column("second", Integer.class);
    var nvl = com.provys.db.sql.nvl(first, second);
    assertThat(nvl.getType()).isEqualTo(Integer.class);
    nvl.addSql(builder);
    assertThat(builder.build()).isEqualTo("NVL(100, second)");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void coalesce() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    var first = com.provys.db.sql.literal(100);
    var second = com.provys.db.sql.column("second", Integer.class);
    var third = com.provys.db.sql.literal(555);
    var coalesce = com.provys.db.sql.coalesce(first, second, third);
    assertThat(coalesce.getType()).isEqualTo(Integer.class);
    coalesce.addSql(builder);
    assertThat(builder.build()).isEqualTo("COALESCE(100, second, 555)");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }
 */
}
