package com.provys.db.defaultdb.types;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.common.datatype.DbBoolean;
import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SqlTypeAdapterDtDateTest {

  private final SqlTypeAdapterDtDate adapter = SqlTypeAdapterDtDate.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(DtDate.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.DATE);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDate.of(1985, 10, 24);
    when(resultSet.getNonNullDtDate(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = DtDate.of(2014, 1, 15);
    when(resultSet.getNonNullDtDate(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDate.of(2010, 5, 24);
    when(resultSet.getNullableDtDate(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableDtDate(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = DtDate.of(1975, 1, 25);
    when(resultSet.getNullableDtDate(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableDtDate(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = DtDate.of(1948, 8, 12);
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableDtDate(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }
}