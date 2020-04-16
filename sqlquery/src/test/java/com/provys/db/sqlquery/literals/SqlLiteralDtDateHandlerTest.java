package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import com.provys.common.datatype.DtDate;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlLiteralDtDateHandlerTest {

  static Stream<@Nullable Object[]> getLiteralTest() {
    return Stream.of(
        new Object[]{DtDate.of(2012, 10, 25), "DATE'2012-10-25'"}
        , new Object[]{DtDate.of(2025, 11, 30), "DATE'2025-11-30'"}
        , new Object[]{DtDate.PRIV, "DATE'1000-01-02'"}
        , new Object[]{DtDate.ME, "DATE'1000-01-01'"}
        , new Object[]{DtDate.MIN, "DATE'1000-01-03'"}
        , new Object[]{DtDate.MAX, "DATE'5000-01-01'"}
        , new @Nullable Object[]{null, "TO_DATE(NULL)"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void getLiteralTest(@Nullable DtDate value, String result) {
    assertThat(SqlLiteralDtDateHandler.getInstance().getLiteral(value)).isEqualTo(result);
  }

  @ParameterizedTest
  @MethodSource("getLiteralTest")
  void appendLiteralTest(@Nullable DtDate value, String result) {
    var builder = new StringBuilder();
    SqlLiteralDtDateHandler.getInstance().appendLiteral(builder, value);
    assertThat(builder.toString()).isEqualTo(result);
  }
}