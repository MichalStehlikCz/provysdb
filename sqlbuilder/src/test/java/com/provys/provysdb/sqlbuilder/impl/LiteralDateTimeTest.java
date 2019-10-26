package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class LiteralDateTimeTest {

    @Nonnull
    static Stream<Object[]> addSqlTest() {
        return Stream.of(
                new Object[]{LiteralDateTime.of(DtDateTime.of(2012, 10, 25, 12, 25)),
                        "TO_DATE('2012-10-25T12:25:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
                , new Object[]{LiteralDateTime.of(DtDateTime.of(2025, 11, 30, 18, 10, 45)),
                        "TO_DATE('2025-11-30T18:10:45', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
                , new Object[]{LiteralDateTime.of(DtDateTime.PRIV),
                        "TO_DATE('1000-01-02T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
                , new Object[]{LiteralDateTime.of(DtDateTime.ME),
                        "TO_DATE('1000-01-01T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
                , new Object[]{LiteralDateTime.of(DtDateTime.MIN),
                        "TO_DATE('1000-01-03T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
                , new Object[]{LiteralDateTime.of(DtDateTime.MAX),
                        "TO_DATE('5000-01-01T00:00:00', 'YYYY-MM-DD\"T\"HH24:MI:SS')"}
        );
    }

    @ParameterizedTest
    @MethodSource
    void addSqlTest(LiteralDateTime value, String result) {
        var builder = new CodeBuilderImpl();
        value.addSql(builder);
        assertThat(builder.build()).isEqualTo(result);
    }
}