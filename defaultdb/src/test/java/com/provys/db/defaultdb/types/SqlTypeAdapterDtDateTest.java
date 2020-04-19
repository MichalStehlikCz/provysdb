package com.provys.db.defaultdb.types;

import static org.assertj.core.api.Assertions.*;
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

class SqlTypeAdapterDtDateTest {

  private final SqlTypeAdapterDtDate adapter = SqlTypeAdapterDtDate.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(DtDate.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.DATE);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDate.of(1985, 10, 24);
    when(resultSet.getNonNullDtDate(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = DtDate.of(2014, 1, 15);
    when(resultSet.getNonNullDtDate(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDate.of(2010, 5, 24);
    when(resultSet.getNullableDtDate(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableDtDate(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = DtDate.of(1975, 1, 25);
    when(resultSet.getNullableDtDate(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableDtDate(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = DtDate.of(1948, 8, 12);
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableDtDate(parameterIndex, value);
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
        , new Object[]{DtDate.class, true}
        , new Object[]{DtDateTime.class, false}
        , new Object[]{LocalDate.class, true}
        , new Object[]{LocalDateTime.class, false}
        , new Object[]{DtUid.class, false}
        , new Object[]{Boolean.class, false}
        , new Object[]{DbBoolean.class, false}
        , new Object[]{String.class, false}
    );
  }

  @ParameterizedTest
  @MethodSource
  void isAssignableFromTest(Class<?> sourceType, boolean result) {
    assertThat(SqlTypeAdapterDtDate.getInstance().isAssignableFrom(sourceType))
        .isEqualTo(result);
  }

  @Test
  void convertNullTest() {
    assertThat(SqlTypeAdapterDtDate.getInstance().convert(null)).isNull();
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
        , new @Nullable Object[]{DtDate.of(2015, 4, 12), DtDate.of(2015, 4, 12)}
        , new @Nullable Object[]{DtDateTime.of(2022, 12, 5, 10, 24), null}
        , new @Nullable Object[]{LocalDate.of(1995, 5, 16), DtDate.of(1995, 5, 16)}
        , new @Nullable Object[]{LocalDateTime.of(2001, 8, 11, 12, 25, 5), null}
        , new @Nullable Object[]{DtUid.valueOf("10002321445985"), null}
        , new @Nullable Object[]{true, null}
        , new @Nullable Object[]{DbBoolean.FALSE, null}
        , new @Nullable Object[]{"Test string", null}
    );
  }

  @ParameterizedTest
  @MethodSource
  void convertTest(Object value, @Nullable DtDate result) {
    if (result == null) {
      assertThatThrownBy(() -> SqlTypeAdapterDtDate.getInstance().convert(value))
          .hasMessageContaining("Conversion not supported");
    } else {
      assertThat(SqlTypeAdapterDtDate.getInstance().convert(value))
          .isEqualTo(result);
    }
  }
}