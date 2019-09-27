package com.provys.provysdb.dbcontext.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked") // we cannot mock parametrised type SqlTypeAdapter
class SqlTypeMapTest {

    @Test
    void getAdapterTestSimple() {
        var map = new SqlTypeMap();
        var adapterInt = mock(SqlTypeAdapter.class);
        map.addAdapter(Integer.class, adapterInt);
        var adapterNumber = mock(SqlTypeAdapter.class);
        map.addAdapter(Number.class, adapterNumber);
        assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterInt);
        assertThat(map.getAdapter(Number.class)).isEqualTo(adapterNumber);
    }

    @Test
    void getAdapterTestInterface() {
        var map = new SqlTypeMap();
        var adapterNumber = mock(SqlTypeAdapter.class);
        map.addAdapter(Number.class, adapterNumber);
        assertThat(map.getAdapter(Integer.class)).isEqualTo(adapterNumber);
    }
}