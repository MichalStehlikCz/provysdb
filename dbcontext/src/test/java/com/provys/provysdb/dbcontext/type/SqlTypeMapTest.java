package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
class SqlTypeMapTest {

    @Test
    void getAdapterTestSimple() {
        var adapterInt = mock(SqlTypeAdapter.class);
        when(adapterInt.getType()).thenReturn(Integer.class);
        var adapterNumber = mock(SqlTypeAdapter.class);
        when(adapterNumber.getType()).thenReturn(Number.class);
        var map = new SqlTypeMapImpl(List.of(adapterInt, adapterNumber));
        assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterInt);
        assertThat(map.getAdapter(Number.class)).isEqualTo(adapterNumber);
    }

    @Test
    void getAdapterTestInterface() {
        var adapterNumber = mock(SqlTypeAdapter.class);
        when(adapterNumber.getType()).thenReturn(Number.class);
        var map = new SqlTypeMapImpl(adapterNumber);
        assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterNumber);
    }
}