package com.provys.db.sqldb.types;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.junit.jupiter.api.Test;

class SqlTypeAdapterStringTest {

  private final SqlTypeAdapterString adapter = SqlTypeAdapterString.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(String.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.VARCHAR);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = "lfdkmaqoeptfjkgpoqekgft";
    when(resultSet.getNonNullString(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = "jfiuwtehftruehtgfuw";
    when(resultSet.getNonNullString(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = "lefkjwoeqijtdfdsaf";
    when(resultSet.getNullableString(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableString(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = "jurhequtfgheigjsdfredjgs";
    when(resultSet.getNullableString(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableString(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = "jwaefhiuqwehfrejlkqj";
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableString(parameterIndex, value);
    verifyNoMoreInteractions(preparedStatement);
  }

  @Test
  void getLiteralTest() {
    var value = "kelwafrqietrjwe'jsehf\"rufghewa\ndaokjfo";
    assertThat(adapter.getLiteral(value))
        .isEqualTo("'kelwafrqietrjwe''jsehf\"rufghewa'||CHR(10)||'daokjfo'");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(adapter.getLiteral(null)).isEqualTo("NULL");
  }
}