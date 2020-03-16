package com.provys.db.sqldb.types;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.junit.jupiter.api.Test;

class SqlTypeAdapterByteTest {
  
  private final SqlTypeAdapterByte adapter = SqlTypeAdapterByte.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(Byte.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.TINYINT);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = (byte) 123;
    when(resultSet.getNonNullByte(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = (byte) 53;
    when(resultSet.getNonNullByte(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = (byte) 45;
    when(resultSet.getNullableByte(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableByte(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = (byte) 12;
    when(resultSet.getNullableByte(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableByte(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = (byte) 185;
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableByte(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }

  @Test
  void getLiteralTest() {
    var value = (byte) 45;
    assertThat(adapter.getLiteral(value)).isEqualTo("45");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(adapter.getLiteral(null)).isEqualTo("NULL");
  }
}