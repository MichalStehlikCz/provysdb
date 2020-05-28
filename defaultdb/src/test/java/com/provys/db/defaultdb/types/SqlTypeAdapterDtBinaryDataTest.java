package com.provys.db.defaultdb.types;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.provys.common.datatype.DtBinaryData;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.junit.jupiter.api.Test;

class SqlTypeAdapterDtBinaryDataTest {

  private final SqlTypeAdapterDtBinaryData adapter = SqlTypeAdapterDtBinaryData.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(DtBinaryData.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.BLOB);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = new DtBinaryData(new byte[]{1, 15, 2, 6});
    when(resultSet.getNonNullDtBinaryData(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = new DtBinaryData(new byte[]{-15, 14, 25, -27, 115});
    when(resultSet.getNonNullDtBinaryData(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = new DtBinaryData(new byte[]{-114, 57, -23, -86, 103});
    when(resultSet.getNullableDtBinaryData(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableDtBinaryData(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = new DtBinaryData(new byte[]{-114, 57, -23, -86, 103});
    when(resultSet.getNullableDtBinaryData(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableDtBinaryData(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = new DtBinaryData(new byte[]{68, 102, -15, 71, -101});
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableDtBinaryData(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }
}