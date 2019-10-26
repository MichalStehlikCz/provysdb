package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class SqlBase implements Sql {

    private final DbContext dbContext;
    private final SqlTokenizer tokenizer;

    SqlBase(DbContext dbContext, SqlTokenizer tokenizer) {
        this.dbContext = Objects.requireNonNull(dbContext);
        this.tokenizer = Objects.requireNonNull(tokenizer);
    }

    SqlBase(DbContext dbContext) {
        this(dbContext, new DefaultSqlTokenizer());
    }

    @Nonnull
    DbContext getDbContext() {
        return dbContext;
    }

    @Nonnull
    @Override
    public SelectBuilder select() {
        return new SelectBuilderImpl(this);
    }

    @Nonnull
    @Override
    public LiteralT<String> literal(String value) {
        return LiteralVarchar.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<String> literalNVarchar(String value) {
        return LiteralNVarchar.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<Byte> literal(byte value) {
        return LiteralByte.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<Short> literal(short value) {
        return LiteralShort.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<Integer> literal(int value) {
        return LiteralInt.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<Long> literal(long value) {
        return LiteralLong.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<BigInteger> literal(BigInteger value) {
        return LiteralBigInteger.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<Float> literal(float value) {
        return LiteralFloat.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<Double> literal(double value) {
        return LiteralDouble.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<BigDecimal> literal(BigDecimal value) {
        return LiteralBigDecimal.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<DtDate> literal(DtDate value) {
        return LiteralDate.of(value);
    }

    @Nonnull
    @Override
    public LiteralT<DtDateTime> literal(DtDateTime value) {
        return LiteralDateTime.of(value);
    }

    @Nonnull
    @Override
    public SqlIdentifierImpl name(String name) {
        return SqlIdentifierImpl.parse(name);
    }

    @Nonnull
    @Override
    public SqlColumn column(SqlIdentifier column) {
        return column(null, column, null);
    }

    @Nonnull
    @Override
    public SqlColumn column(SqlIdentifier column, @Nullable SqlIdentifier alias) {
        return column(null, column, alias);
    }

    @Nonnull
    @Override
    public SqlColumn column(@Nullable SqlTableAlias tableAlias, SqlIdentifier column) {
        return new SqlColumnSimple(tableAlias, column, null);
    }

    @Nonnull
    @Override
    public SqlColumn column(@Nullable SqlTableAlias tableAlias, SqlIdentifier column, @Nullable SqlIdentifier alias) {
        return new SqlColumnSimple(tableAlias, column, alias);
    }

    @Nonnull
    @Override
    public SqlColumn column(@Nullable String tableAlias, String columnName, @Nullable String alias) {
        return column((tableAlias == null) ? null : tableAlias(tableAlias), name(columnName),
                (alias == null) ? null : name(alias));
    }

    @Nonnull
    @Override
    public SqlColumn column(String tableAlias, String columnName) {
        return column(tableAlias, columnName, null);
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql) {
        return columnSql(columnSql, null);
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql, @Nullable String alias) {
        return columnSql(columnSql, alias, Collections.emptyList());
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql, @Nullable String alias, BindName... binds) {
        return columnSql(columnSql, alias, Arrays.asList(binds));
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql, @Nullable String alias, List<BindName> binds) {
        return new SqlColumnSql(columnSql, (alias == null) ? null : name(alias), binds);
    }

    @Nonnull
    @Override
    public SqlColumn columnParse(String columnSql) {
        return columnParse(columnSql, null);
    }

    @Nonnull
    @Override
    public SqlColumn columnParse(String sql, @Nullable String alias) {
        return columnParse(sql, alias, Collections.emptyList());
    }

    @Nonnull
    @Override
    public SqlColumn columnParse(String sql, @Nullable String alias, BindVariable... binds) {
        return columnParse(sql, alias, Arrays.asList(binds));
    }

    @Nonnull
    @Override
    public SqlColumn columnParse(String sql, @Nullable String alias, Collection<BindVariable> binds) {
        var builder = tokenizer.getBinds(sql).applyBindVariables(binds);
        return columnSql(builder.build(), alias, builder.getBinds());
    }

    @Nonnull
    @Override
    public SqlTableAlias tableAlias(String tableAlias) {
        return new SqlTableAliasImpl(tableAlias);
    }

    @Nonnull
    @Override
    public SqlFrom from(SqlIdentifier tableName, SqlTableAlias alias) {
        return new SqlFromSimple(tableName, alias);
    }

    @Nonnull
    @Override
    public SqlFrom from(String tableName, String alias) {
        return from(name(tableName), tableAlias(alias));
    }

    @Nonnull
    @Override
    public SqlFrom fromSql(String sqlSelect, SqlTableAlias alias) {
        return new SqlFromSql(sqlSelect, alias);
    }

    @Nonnull
    @Override
    public SqlFrom fromSql(String sqlSelect, String alias) {
        return fromSql(sqlSelect, tableAlias(alias));
    }

    @Nonnull
    @Override
    public SqlFrom from(Select select, SqlTableAlias alias) {
        return new SqlFromSelect(select, alias);
    }

    @Nonnull
    @Override
    public SqlFrom from(Select select, String alias) {
        return new SqlFromSelect(select, tableAlias(alias));
    }

    @Nonnull
    @Override
    public SqlFrom fromDual() {
        return SqlFromDual.getInstance();
    }

    @Nonnull
    @Override
    public SqlWhere whereSql(String conditionSql) {
        return new SqlWhereSimple(conditionSql);
    }

    @Nonnull
    @Override
    public SqlWhere whereSql(String conditionSql, BindName... binds) {
        return whereSql(conditionSql, Arrays.asList(binds));
    }

    @Nonnull
    @Override
    public SqlWhere whereSql(String conditionSql, List<BindName> binds) {
        return new SqlWhereSimpleWithBinds(conditionSql, binds);
    }

    @Nonnull
    @Override
    public SqlWhere whereAnd(SqlWhere... whereConditions) {
        return whereAnd(Arrays.asList(whereConditions));
    }

    @Nonnull
    @Override
    public SqlWhere whereAnd(Collection<SqlWhere> whereConditions) {
        // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
        return new SqlWhereJoinerImpl(SqlConditionOperator.AND, whereConditions).build();
    }

    @Nonnull
    @Override
    public SqlWhereJoiner whereAndJoiner() {
        return new SqlWhereJoinerImpl(SqlConditionOperator.AND);
    }

    @Nonnull
    @Override
    public SqlWhere whereOr(SqlWhere... whereConditions) {
        return whereOr(Arrays.asList(whereConditions));
    }

    @Nonnull
    @Override
    public SqlWhere whereOr(Collection<SqlWhere> whereConditions) {
        // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
        return new SqlWhereJoinerImpl(SqlConditionOperator.OR, whereConditions).build();
    }

    @Nonnull
    @Override
    public SqlWhereJoiner whereOrJoiner() {
        return new SqlWhereJoinerImpl(SqlConditionOperator.OR);
    }
}
