package com.provys.provysdb.sqlbuilder.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SqlImplTest {

    @Test
    void eq() {
        var builder = new CodeBuilderImpl();
        var sql = new NoDbSqlImpl();
        var first = sql.literal(100);
        var second = sql.literal(200);
        var condition = sql.eq(first, second);
        assertThat(condition.isEmpty()).isFalse();
        condition.addSql(builder);
        assertThat(builder.build()).isEqualTo("(100=200)");
        assertThat(builder.getBinds()).isEmpty();
    }

    @Test
    void eqSame() {
        var sql = new NoDbSqlImpl();
        var first = sql.literal(100);
        var second = sql.literal(100);
        var condition = sql.eq(first, second);
        assertThat(condition.isEmpty()).isTrue();
    }

    @Test
    void isNull() {
        var builder = new CodeBuilderImpl();
        var sql = new NoDbSqlImpl();
        var expression = sql.literal(100);
        var condition = sql.isNull(expression);
        assertThat(condition.isEmpty()).isFalse();
        condition.addSql(builder);
        assertThat(builder.build()).isEqualTo("(100 IS NULL)");
        assertThat(builder.getBinds()).isEmpty();
    }
}
