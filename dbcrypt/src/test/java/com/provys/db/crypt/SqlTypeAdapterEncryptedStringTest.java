package com.provys.db.crypt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.provys.common.crypt.DtEncryptedString;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.defaultdb.types.SqlTypeAdapterString;
import java.sql.Types;
import org.junit.jupiter.api.Test;

public class SqlTypeAdapterEncryptedStringTest {

  private final SqlTypeAdapterEncryptedString adapter = SqlTypeAdapterEncryptedString.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(DtEncryptedString.class);
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
    assertThat(adapter.readNonNullValue(resultSet, columnIndex))
        .isEqualTo(DtEncryptedString.valueOf(result));
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = "IIS$BMom+CsNbRklWuoYWaVPTalWpj+Tug==";
    when(resultSet.getNonNullString(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel))
        .isEqualTo(DtEncryptedString.valueOf(result));
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = "lefkjwoeqijtdfdsaf";
    when(resultSet.getNullableString(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex))
        .isEqualTo(DtEncryptedString.valueOf(result));
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
    assertThat(adapter.readNullableValue(resultSet, columnLabel))
        .isEqualTo(DtEncryptedString.valueOf(result));
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
    var value = DtEncryptedString.valueOf("jwaefhiuqwehfrejlkqj");
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1))
        .setNullableString(parameterIndex, value.getValue());
    verifyNoMoreInteractions(preparedStatement);
  }
}