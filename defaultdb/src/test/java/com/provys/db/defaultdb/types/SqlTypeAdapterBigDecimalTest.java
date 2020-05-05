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

class SqlTypeAdapterBigDecimalTest {

  private final SqlTypeAdapterBigDecimal adapter = SqlTypeAdapterBigDecimal.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(BigDecimal.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.NUMERIC);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = BigDecimal.valueOf(1564847354536487878L, -2);
    when(resultSet.getNonNullBigDecimal(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = BigDecimal.valueOf(54484311689L);
    when(resultSet.getNonNullBigDecimal(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = BigDecimal.valueOf(5481215454L);
    when(resultSet.getNullableBigDecimal(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableBigDecimal(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = BigDecimal.valueOf(841236546L);
    when(resultSet.getNullableBigDecimal(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableBigDecimal(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = BigDecimal.valueOf(8759638748865L);
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableBigDecimal(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }
}