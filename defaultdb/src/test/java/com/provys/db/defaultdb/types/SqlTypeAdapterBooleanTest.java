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

class SqlTypeAdapterBooleanTest {

  private final SqlTypeAdapterBoolean adapter = SqlTypeAdapterBoolean.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(Boolean.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.CHAR);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    when(resultSet.getNonNullBoolean(columnIndex)).thenReturn(true);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isTrue();
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    when(resultSet.getNonNullBoolean(columnLabel)).thenReturn(false);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isFalse();
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    when(resultSet.getNullableBoolean(columnIndex)).thenReturn(false);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isFalse();
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableBoolean(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    when(resultSet.getNullableBoolean(columnLabel)).thenReturn(true);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isTrue();
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableBoolean(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    adapter.bindValue(preparedStatement, parameterIndex, true);
    verify(preparedStatement, times(1)).setNullableBoolean(parameterIndex, true);
    verifyNoMoreInteractions(preparedStatement);
  }
}