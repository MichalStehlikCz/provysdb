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

  static Stream<Object[]> isAssignableFromTest() {
    return Stream.of(
        new Object[]{Byte.class, true}
        , new Object[]{Short.class, true}
        , new Object[]{Integer.class, true}
        , new Object[]{Long.class, true}
        , new Object[]{Float.class, true}
        , new Object[]{Double.class, true}
        , new Object[]{BigInteger.class, true}
        , new Object[]{BigDecimal.class, true}
        , new Object[]{DtDate.class, false}
        , new Object[]{DtDateTime.class, false}
        , new Object[]{LocalDate.class, false}
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
    assertThat(SqlTypeAdapterBigDecimal.getInstance().isAssignableFrom(sourceType))
        .isEqualTo(result);
  }

  @Test
  void convertNullTest() {
    assertThat(SqlTypeAdapterBigDecimal.getInstance().convert(null)).isNull();
  }

  static Stream<@Nullable Object[]> convertTest() {
    return Stream.of(
        new @Nullable Object[]{(byte) 15, BigDecimal.valueOf(15L)}
        , new @Nullable Object[]{(short) 1561, BigDecimal.valueOf(1561L)}
        , new @Nullable Object[]{1569852, BigDecimal.valueOf(1569852L)}
        , new @Nullable Object[]{15474846464646L, BigDecimal.valueOf(15474846464646L)}
        , new @Nullable Object[]{15645.786f, BigDecimal.valueOf(15645.786f)}
        , new @Nullable Object[]{15456864.12565468, BigDecimal.valueOf(15456864.12565468)}
        , new @Nullable Object[]{new BigInteger("2516548487843546897665644"),
            new BigDecimal("2516548487843546897665644")}
        , new @Nullable Object[]{new BigDecimal("2516548487843546897665644.541864789"),
            new BigDecimal("2516548487843546897665644.541864789")}
        , new @Nullable Object[]{DtDate.of(2015, 4, 12), null}
        , new @Nullable Object[]{DtDateTime.of(2022, 12, 5, 10, 24), null}
        , new @Nullable Object[]{LocalDate.of(1995, 5, 16), null}
        , new @Nullable Object[]{LocalDateTime.of(2001, 8, 11, 12, 25, 5), null}
        , new @Nullable Object[]{DtUid.valueOf("10002321445985"), null}
        , new @Nullable Object[]{true, null}
        , new @Nullable Object[]{DbBoolean.FALSE, null}
        , new @Nullable Object[]{"Test string", null}
    );
  }

  @ParameterizedTest
  @MethodSource
  void convertTest(Object value, @Nullable BigDecimal result) {
    if (result == null) {
      assertThatThrownBy(() -> SqlTypeAdapterBigDecimal.getInstance().convert(value))
          .hasMessageContaining("Conversion not supported");
    } else {
      assertThat(SqlTypeAdapterBigDecimal.getInstance().convert(value))
          .isEqualTo(result);
    }
  }
}