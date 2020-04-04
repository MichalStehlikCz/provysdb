package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;

class SqlLiteralTypeHandlerMapTest {

  @Test
  void getAdapterTestSimple() {
    @SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
    var handlerInt = (SqlLiteralTypeHandler<Integer>) mock(SqlLiteralTypeHandler.class);
    when(handlerInt.getType()).thenReturn(Integer.class);
    @SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
    var handlerNumber = (SqlLiteralTypeHandler<Number>) mock(SqlLiteralTypeHandler.class);
    when(handlerNumber.getType()).thenReturn(Number.class);
    var map = new SqlLiteralTypeHandlerMap(List.of(handlerInt, handlerNumber));
    assertThat(map.getHandler(Integer.class)).isEqualTo(handlerInt);
    assertThat(map.getHandler(Number.class)).isEqualTo(handlerNumber);
  }

  @Test
  void getAdapterTestInterface() {
    var handlerNumber = mock(SqlLiteralTypeHandler.class);
    when(handlerNumber.getType()).thenReturn(Number.class);
    var map = new SqlLiteralTypeHandlerMap(handlerNumber);
    assertThat(map.getHandler(Integer.class)).isEqualTo(handlerNumber);
  }
}