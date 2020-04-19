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

  static Stream<Object[]> isAssignableFromTest() {
    return Stream.of(
        new Object[]{Byte.class, false}
        , new Object[]{Short.class, false}
        , new Object[]{Integer.class, false}
        , new Object[]{Long.class, false}
        , new Object[]{Float.class, false}
        , new Object[]{Double.class, false}
        , new Object[]{BigInteger.class, false}
        , new Object[]{BigDecimal.class, false}
        , new Object[]{DtDate.class, false}
        , new Object[]{DtDateTime.class, false}
        , new Object[]{LocalDate.class, false}
        , new Object[]{LocalDateTime.class, false}
        , new Object[]{DtUid.class, false}
        , new Object[]{Boolean.class, false}
        , new Object[]{DbBoolean.class, false}
        , new Object[]{String.class, true}
    );
  }

  @ParameterizedTest
  @MethodSource
  void isAssignableFromTest(Class<?> sourceType, boolean result) {
    assertThat(SqlTypeAdapterString.getInstance().isAssignableFrom(sourceType))
        .isEqualTo(result);
  }

  @Test
  void convertNullTest() {
    assertThat(SqlTypeAdapterString.getInstance().convert(null)).isNull();
  }

  static Stream<@Nullable Object[]> convertTest() {
    return Stream.of(
        new @Nullable Object[]{(byte) 15, null}
        , new @Nullable Object[]{(short) 1561, null}
        , new @Nullable Object[]{1569852, null}
        , new @Nullable Object[]{15474846464646L, null}
        , new @Nullable Object[]{15645.786f, null}
        , new @Nullable Object[]{15456864.12565468, null}
        , new @Nullable Object[]{new BigInteger("2516548487843546897665644"), null}
        , new @Nullable Object[]{new BigDecimal("2516548487843546897665644.541864789"), null}
        , new @Nullable Object[]{DtDate.of(2015, 4, 12), null}
        , new @Nullable Object[]{DtDateTime.of(2022, 12, 5, 10, 24), null}
        , new @Nullable Object[]{LocalDate.of(1995, 5, 16), null}
        , new @Nullable Object[]{LocalDateTime.of(2001, 8, 11, 12, 25, 5), null}
        , new @Nullable Object[]{DtUid.valueOf("10002321445985"), null}
        , new @Nullable Object[]{true, null}
        , new @Nullable Object[]{DbBoolean.FALSE, null}
        , new @Nullable Object[]{"Test string", "Test string"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void convertTest(Object value, @Nullable String result) {
    if (result == null) {
      assertThatThrownBy(() -> SqlTypeAdapterString.getInstance().convert(value))
          .hasMessageContaining("Conversion not supported");
    } else {
      assertThat(SqlTypeAdapterString.getInstance().convert(value))
          .isEqualTo(result);
    }
  }
}