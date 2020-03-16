package com.provys.db.sqldb.types;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.math.BigInteger;
import java.sql.Types;
import org.junit.jupiter.api.Test;

class SqlTypeAdapterBigIntegerTest {

  private final SqlTypeAdapterBigInteger adapter = SqlTypeAdapterBigInteger.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(BigInteger.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.BIGINT);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = BigInteger.valueOf(1564847354536487878L);
    when(resultSet.getNonNullBigInteger(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = BigInteger.valueOf(54484311689L);
    when(resultSet.getNonNullBigInteger(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = BigInteger.valueOf(5481215454L);
    when(resultSet.getNullableBigInteger(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableBigInteger(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = BigInteger.valueOf(841236546L);
    when(resultSet.getNullableBigInteger(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableBigInteger(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = BigInteger.valueOf(8759638748865L);
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableBigInteger(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }

  @Test
  void getLiteralTest() {
    var value = BigInteger.valueOf(1234567891234567858L);
    assertThat(adapter.getLiteral(value)).isEqualTo("1234567891234567858");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(adapter.getLiteral(null)).isEqualTo("NULL");
  }
}