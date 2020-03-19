package com.provys.db.sqldb.types;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.common.datatype.DtDateTime;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlTypeAdapterDtDateTimeTest {

  private final SqlTypeAdapterDtDateTime adapter = SqlTypeAdapterDtDateTime.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(DtDateTime.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.TIMESTAMP);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDateTime.of(1985, 10, 24, 12, 25, 15);
    when(resultSet.getNonNullDtDateTime(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = DtDateTime.of(2014, 1, 15, 7, 54, 1);
    when(resultSet.getNonNullDtDateTime(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDateTime.of(2010, 5, 24, 18, 45);
    when(resultSet.getNullableDtDateTime(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableDtDateTime(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = DtDateTime.of(1975, 1, 25, 22, 18, 24);
    when(resultSet.getNullableDtDateTime(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableDtDateTime(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = DtDateTime.of(1948, 8, 12, 3, 52, 12);
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableDtDateTime(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }

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
        , new @Nullable Object[]{null, "NULL"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void getLiteralTest(@Nullable DtDateTime value, String result) {
    assertThat(adapter.getLiteral(value)).isEqualTo(result);
  }

  @ParameterizedTest
  @MethodSource("getLiteralTest")
  void appendLiteralTest(@Nullable DtDateTime value, String result) {
    var builder = new StringBuilder();
    adapter.appendLiteral(builder, value);
    assertThat(builder.toString()).isEqualTo(result);
  }
}