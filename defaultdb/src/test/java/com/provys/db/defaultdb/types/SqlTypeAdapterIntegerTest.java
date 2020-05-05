package com.provys.db.defaultdb.types;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

class SqlTypeAdapterIntegerTest {

  private final SqlTypeAdapterInteger adapter = SqlTypeAdapterInteger.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(Integer.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.INTEGER);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = (Integer) 123;
    when(resultSet.getNonNullInteger(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = 45453;
    when(resultSet.getNonNullInteger(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = 65486945;
    when(resultSet.getNullableInteger(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableInteger(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = 487846512;
    when(resultSet.getNullableInteger(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableInteger(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = 86584185;
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableInteger(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }
}