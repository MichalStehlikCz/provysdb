package com.provys.db.defaultdb.types;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class SqlTypeMapTest {

  @Test
  void getAdapterTestSimple() {
    @SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
        var adapterInt = (SqlTypeAdapter<Integer>) mock(SqlTypeAdapter.class);
    when(adapterInt.getType()).thenReturn(Integer.class);
    @SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
        var adapterNumber = (SqlTypeAdapter<Number>) mock(SqlTypeAdapter.class);
    when(adapterNumber.getType()).thenReturn(Number.class);
    var map = new SqlTypeMap(List.of(adapterInt, adapterNumber));
    assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterInt);
    assertThat(map.getAdapter(Number.class)).isEqualTo(adapterNumber);
  }

  @Test
  void getAdapterTestInterface() {
    var adapterNumber = mock(SqlTypeAdapter.class);
    when(adapterNumber.getType()).thenReturn(Number.class);
    var map = new SqlTypeMap(adapterNumber);
    assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterNumber);
  }
}