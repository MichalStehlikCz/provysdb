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

class SqlTypeAdapterDtDateTimeTest {

  private final SqlTypeAdapterDtDateTime adapter = SqlTypeAdapterDtDateTime.getInstance();

  @Test
  void getTypeTest() {
    assertThat(adapter.getType()).isEqualTo(DtDateTime.class);
  }

  @Test
  void getSqlTypeTest() {
    assertThat(adapter.getSqlType()).isEqualTo(Types.TIMESTAMP);
  }

  @Test
  void readNonnullValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDateTime.of(1985, 10, 24, 12, 25, 15);
    when(resultSet.getNonNullDtDateTime(columnIndex)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNonnullValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "label";
    var result = DtDateTime.of(2014, 1, 15, 7, 54, 1);
    when(resultSet.getNonNullDtDateTime(columnLabel)).thenReturn(result);
    assertThat(adapter.readNonNullValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 5;
    var result = DtDateTime.of(2010, 5, 24, 18, 45);
    when(resultSet.getNullableDtDateTime(columnIndex)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isEqualTo(result);
  }

  @Test
  void readNullableValueNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnIndex = 4;
    when(resultSet.getNullableDtDateTime(columnIndex)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnIndex)).isNull();
  }

  @Test
  void readNullableValueLabelTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "adfeta";
    var result = DtDateTime.of(1975, 1, 25, 22, 18, 24);
    when(resultSet.getNullableDtDateTime(columnLabel)).thenReturn(result);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isEqualTo(result);
  }

  @Test
  void readNullableValueLabelNullTest() {
    var resultSet = mock(DbResultSet.class);
    var columnLabel = "sferg54er8gga";
    when(resultSet.getNullableDtDateTime(columnLabel)).thenReturn(null);
    assertThat(adapter.readNullableValue(resultSet, columnLabel)).isNull();
  }

  @Test
  void bindValueTest() {
    var preparedStatement = mock(DbPreparedStatement.class);
    var parameterIndex = 3;
    var value = DtDateTime.of(1948, 8, 12, 3, 52, 12);
    adapter.bindValue(preparedStatement, parameterIndex, value);
    verify(preparedStatement, times(1)).setNullableDtDateTime(parameterIndex, value);
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
        , new Object[]{DtDateTime.class, true}
        , new Object[]{LocalDate.class, true}
        , new Object[]{LocalDateTime.class, true}
        , new Object[]{DtUid.class, false}
        , new Object[]{Boolean.class, false}
        , new Object[]{DbBoolean.class, false}
        , new Object[]{String.class, false}
    );
  }

  @ParameterizedTest
  @MethodSource
  void isAssignableFromTest(Class<?> sourceType, boolean result) {
    assertThat(SqlTypeAdapterDtDateTime.getInstance().isAssignableFrom(sourceType))
        .isEqualTo(result);
  }

  @Test
  void convertNullTest() {
    assertThat(SqlTypeAdapterDtDateTime.getInstance().convert(null)).isNull();
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
        , new @Nullable Object[]{DtDate.of(2015, 4, 12), DtDateTime.of(2015, 4, 12)}
        , new @Nullable Object[]{DtDateTime.of(2022, 12, 5, 10, 24),
            DtDateTime.of(2022, 12, 5, 10, 24)}
        , new @Nullable Object[]{LocalDate.of(1995, 5, 16), DtDateTime.of(1995, 5, 16)}
        , new @Nullable Object[]{LocalDateTime.of(2001, 8, 11, 12, 25, 5),
            DtDateTime.of(2001, 8, 11, 12, 25, 5)}
        , new @Nullable Object[]{DtUid.valueOf("10002321445985"), null}
        , new @Nullable Object[]{true, null}
        , new @Nullable Object[]{DbBoolean.FALSE, null}
        , new @Nullable Object[]{"Test string", null}
    );
  }

  @ParameterizedTest
  @MethodSource
  void convertTest(Object value, @Nullable DtDateTime result) {
    if (result == null) {
      assertThatThrownBy(() -> SqlTypeAdapterDtDateTime.getInstance().convert(value))
          .hasMessageContaining("Conversion not supported");
    } else {
      assertThat(SqlTypeAdapterDtDateTime.getInstance().convert(value))
          .isEqualTo(result);
    }
  }
}