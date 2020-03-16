package com.provys.db.sqldb.types;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.common.datatype.DtUid;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.junit.jupiter.api.Test;

class SqlTypeAdapterDtUidTest {

  private final SqlTypeAdapterDtUid adapter = SqlTypeAdapterDtUid.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(DtUid.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.BIGINT);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtUid.valueOf("1234567890");
    when(resultSet.getNonNullDtUid(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = DtUid.valueOf("54484311689");
    when(resultSet.getNonNullDtUid(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtUid.valueOf("5481215454");
    when(resultSet.getNullableDtUid(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableDtUid(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = DtUid.valueOf("841236546");
    when(resultSet.getNullableDtUid(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableDtUid(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = DtUid.valueOf("8759638748865");
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableDtUid(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }

  @Test
  void getLiteralTest() {
    var uid = "123456789123456789123";
    var value = DtUid.valueOf(uid);
    assertThat(adapter.getLiteral(value)).isEqualTo(uid);
  }

  @Test
  void getLiteralNullTest() {
    assertThat(adapter.getLiteral(null)).isEqualTo("NULL");
  }
}