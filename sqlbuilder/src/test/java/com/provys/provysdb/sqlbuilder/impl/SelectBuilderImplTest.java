package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.Sql;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class SelectBuilderImplTest {

    private static final Sql sql = new SqlImpl();

    @Test
    void select1FromDualTest() {
        assertThat(new SelectBuilderImpl(sql).fromDual().columnSql("1").build())
                .isEqualTo(new SelectImpl("SELECT\n    1\nFROM\n    dual\n", Collections.emptyList()));
    }

    @Test
    void selectColumnFromTableTest() {
        assertThat(new SelectBuilderImpl(sql).from("Table", "TableAlias").column("Column").build())
                .isEqualTo(new SelectImpl("SELECT\n    tablealias.column\nFROM\n    table tablealias\n",
                        Collections.emptyList()));
    }
}