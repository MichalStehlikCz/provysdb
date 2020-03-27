package com.provys.db.sqldb.dbcontext;

import com.provys.db.dbcontext.SqlTypeAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class SqlTypeMapTest {

    @Test
    void getAdapterTestSimple() {
        @SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
        var adapterInt = (SqlTypeAdapter<Integer>) mock(SqlTypeAdapter.class);
        when(adapterInt.getType()).thenReturn(Integer.class);
        when(adapterInt.getName()).thenReturn("INTEGER");
        @SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
        var adapterNumber = (SqlTypeAdapter<Number>) mock(SqlTypeAdapter.class);
        when(adapterNumber.getType()).thenReturn(Number.class);
        when(adapterNumber.getName()).thenReturn("NUMBER");
        var map = new DefaultTypeMapImpl(List.of(adapterInt, adapterNumber));
        Assertions.assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterInt);
        Assertions.assertThat(map.getAdapter(Number.class)).isEqualTo(adapterNumber);
    }

    @Test
    void getAdapterTestInterface() {
        var adapterNumber = mock(SqlTypeAdapter.class);
        when(adapterNumber.getType()).thenReturn(Number.class);
        when(adapterNumber.getName()).thenReturn("NUMBER");
        var map = new DefaultTypeMapImpl(adapterNumber);
        Assertions.assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterNumber);
    }
}