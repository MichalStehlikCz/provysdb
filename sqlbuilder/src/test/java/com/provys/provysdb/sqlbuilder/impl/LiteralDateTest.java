package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class LiteralDateTest {

    @Nonnull
    static Stream<Object[]> addSqlTest() {
        return Stream.of(
                new Object[]{LiteralDate.of(DtDate.of(2012, 10, 25)), "DATE'2012-10-25'"}
                , new Object[]{LiteralDate.of(DtDate.of(2025, 11, 30)), "DATE'2025-11-30'"}
                , new Object[]{LiteralDate.of(DtDate.PRIV), "DATE'1000-01-02'"}
                , new Object[]{LiteralDate.of(DtDate.ME), "DATE'1000-01-01'"}
                , new Object[]{LiteralDate.of(DtDate.MIN), "DATE'1000-01-03'"}
                , new Object[]{LiteralDate.of(DtDate.MAX), "DATE'5000-01-01'"}
        );
    }

    @ParameterizedTest
    @MethodSource
    void addSqlTest(LiteralDate value, String result) {
        var builder = new CodeBuilderImpl();
        value.addSql(builder);
        assertThat(builder.build()).isEqualTo(result);
    }
}