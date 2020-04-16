package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import com.provys.common.datatype.DtDateTime;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlLiteralDtDateTimeHandlerTest {

  static Stream<@Nullable Object[]> getLiteralTest() {
    return Stream.of(
        new Object[]{DtDateTime.of(2012, 10, 25, 12, 25),
            "TO_DATE('2012-10-25T12:25:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
        , new Object[]{DtDateTime.of(2025, 11, 30, 18, 10, 45),
            "TO_DATE('2025-11-30T18:10:45', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
        , new Object[]{DtDateTime.PRIV,
            "TO_DATE('1000-01-02T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
        , new Object[]{DtDateTime.ME,
            "TO_DATE('1000-01-01T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
        , new Object[]{DtDateTime.MIN,
            "TO_DATE('1000-01-03T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
        , new Object[]{DtDateTime.MAX,
            "TO_DATE('5000-01-01T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
        , new @Nullable Object[]{null, "TO_DATE(NULL)"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void getLiteralTest(@Nullable DtDateTime value, String result) {
    assertThat(SqlLiteralDtDateTimeHandler.getInstance().getLiteral(value)).isEqualTo(result);
  }

  @ParameterizedTest
  @MethodSource("getLiteralTest")
  void appendLiteralTest(@Nullable DtDateTime value, String result) {
    var builder = new StringBuilder();
    SqlLiteralDtDateTimeHandler.getInstance().appendLiteral(builder, value);
    assertThat(builder.toString()).isEqualTo(result);
  }
}