package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlparser.SqlSymbol;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@ApplicationScoped
public class SqlImpl implements Sql {

    private final SqlTokenizer tokenizer;

    SqlImpl(SqlTokenizer tokenizer) {
        this.tokenizer = Objects.requireNonNull(tokenizer);
    }

    public SqlImpl() {
        this(new DefaultSqlTokenizer());
    }
    /**
     * @return value of field tokenizer
     */
    SqlTokenizer getTokenizer() {
        return tokenizer;
    }

    @Nonnull
    public LiteralT<String> literal(String value) {
        return LiteralVarchar.of(value);
    }

    @Nonnull
    public LiteralT<String> literalNVarchar(String value) {
        return LiteralNVarchar.of(value);
    }

    @Nonnull
    public LiteralT<Byte> literal(byte value) {
        return LiteralByte.of(value);
    }

    @Nonnull
    public LiteralT<Short> literal(short value) {
        return LiteralShort.of(value);
    }

    @Nonnull
    public LiteralT<Integer> literal(int value) {
        return LiteralInt.of(value);
    }

    @Nonnull
    public LiteralT<Long> literal(long value) {
        return LiteralLong.of(value);
    }

    @Nonnull
    public LiteralT<BigInteger> literal(BigInteger value) {
        return LiteralBigInteger.of(value);
    }

    @Nonnull
    public LiteralT<Float> literal(float value) {
        return LiteralFloat.of(value);
    }

    @Nonnull
    public LiteralT<Double> literal(double value) {
        return LiteralDouble.of(value);
    }

    @Nonnull
    public LiteralT<BigDecimal> literal(BigDecimal value) {
        return LiteralBigDecimal.of(value);
    }

    @Nonnull
    public LiteralT<DtDate> literal(DtDate value) {
        return LiteralDate.of(value);
    }

    @Nonnull
    public LiteralT<DtDateTime> literal(DtDateTime value) {
        return LiteralDateTime.of(value);
    }

    @Nonnull
    public SqlIdentifierImpl name(String name) {
        return SqlIdentifierImpl.parse(name);
    }

    @Nonnull
    public SqlColumn column(SqlIdentifier column) {
        return new SqlColumnSimple(null, column, null);
    }

    @Nonnull
    public SqlColumn column(SqlIdentifier column, @Nullable SqlIdentifier alias) {
        return new SqlColumnSimple(null, column, alias);
    }

    @Nonnull
    public SqlColumn column(@Nullable SqlTableAlias tableAlias, SqlIdentifier column) {
        return new SqlColumnSimple(tableAlias, column, null);
    }

    @Nonnull
    public SqlColumn column(@Nullable SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias) {
        return new SqlColumnSimple(tableAlias, column, alias);
    }

    @Nonnull
    public SqlColumn column(String columnName) {
        return new SqlColumnSimple(null, name(columnName), null);
    }

    @Nonnull
    public SqlColumn column(String tableAlias, String columnName) {
        return new SqlColumnSimple(tableAlias(tableAlias), name(columnName), null);
    }

    @Nonnull
    public SqlColumn column(String tableAlias, String columnName, String alias) {
        return column(tableAlias(tableAlias), name(columnName), name(alias));
    }

    @Nonnull
    public SqlColumn columnDirect(String columnSql) {
        return new SqlColumnSql(columnSql, null);
    }

    @Nonnull
    public SqlColumn columnDirect(String columnSql, String alias) {
        return new SqlColumnSql(columnSql, name(alias));
    }

    @Nonnull
    public SqlColumn columnDirect(String columnSql, String alias, BindName... binds) {
        return columnDirect(columnSql, alias, Arrays.asList(binds));
    }

    @Nonnull
    public SqlColumn columnDirect(String columnSql, @Nullable String alias, List<BindName> binds) {
        return new SqlColumnSql(columnSql, (alias == null) ? null : name(alias), binds);
    }

    @Nonnull
    public SqlColumn columnSql(String sql) {
        var builder = tokenizer.getBinds(sql);
        return columnDirect(builder.build(), null, builder.getBinds());
    }

    @Nonnull
    public SqlColumn columnSql(String sql, String alias) {
        return columnSql(sql, alias, Collections.emptyList());
    }

    @Nonnull
    public SqlColumn columnSql(String sql, @Nullable String alias, BindVariable... binds) {
        return columnSql(sql, alias, Arrays.asList(binds));
    }

    @Nonnull
    public SqlColumn columnSql(String sql, @Nullable String alias, Iterable<BindVariable> binds) {
        var builder = tokenizer.getBinds(sql).applyBindVariables(binds);
        return columnDirect(builder.build(), alias, builder.getBinds());
    }

    private <T> SqlColumnT<T> column(SqlColumn column, Class<T> clazz) {
        return new SqlColumnTImpl<>(column, clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> column(SqlIdentifier column, Class<T> clazz) {
        return column(column(column), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return column(column(column, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column, Class<T> clazz) {
        return column(column(tableAlias, column), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return column(column(tableAlias, column, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> column(String columnName, Class<T> clazz) {
        return column(column(columnName), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> column(String tableAlias, String columnName, Class<T> clazz) {
        return column(column(tableAlias, columnName), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(column(tableAlias, columnName, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnDirect(String sql, Class<T> clazz) {
        return column(columnDirect(sql), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz) {
        return column(columnDirect(sql, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz, BindName... binds) {
        return column(columnDirect(sql, alias, binds), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz) {
        return column(columnDirect(sql, alias, binds), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnSql(String sql, Class<T> clazz) {
        return column(columnSql(sql), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz) {
        return column(columnSql(sql, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz, BindVariable... binds) {
        return column(columnSql(sql, alias, binds), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<T> columnSql(String sql, String alias, Iterable<BindVariable> binds, Class<T> clazz) {
        return column(columnSql(sql, alias, binds), clazz);
    }

    @Nonnull
    private <T> SqlColumnT<Optional<T>> columnOptional(SqlColumn column, Class<T> clazz) {
        return new SqlColumnOptionalT<>(column, clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptional(SqlIdentifier column, Class<T> clazz) {
        return columnOptional(column(column), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptional(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return columnOptional(column(column, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptional(SqlTableAlias tableAlias, SqlIdentifier column, Class<T> clazz) {
        return columnOptional(column(tableAlias, column), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptional(SqlTableAlias tableAlias, SqlIdentifier column,
                                                      SqlIdentifier alias, Class<T> clazz) {
        return columnOptional(column(tableAlias, column, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptional(String columnName, Class<T> clazz) {
        return columnOptional(column(columnName), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptional(String tableAlias, String columnName, Class<T> clazz) {
        return columnOptional(column(tableAlias, columnName), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptional(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return columnOptional(column(tableAlias, columnName, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalDirect(String columnSql, Class<T> clazz) {
        return columnOptional(columnDirect(columnSql), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalDirect(String sql, String alias, Class<T> clazz) {
        return columnOptional(columnDirect(sql, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalDirect(String sql, String alias, Class<T> clazz, BindName... binds) {
        return columnOptional(columnDirect(sql, alias, binds), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalDirect(String sql, String alias, List<BindName> binds, Class<T> clazz) {
        return columnOptional(columnDirect(sql, alias, binds), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalSql(String columnSql, Class<T> clazz) {
        return columnOptional(columnSql(columnSql), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalSql(String sql, String alias, Class<T> clazz) {
        return columnOptional(columnSql(sql, alias), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalSql(String sql, String alias, Class<T> clazz, BindVariable... binds) {
        return columnOptional(columnSql(sql, alias, binds), clazz);
    }

    @Nonnull
    public <T> SqlColumnT<Optional<T>> columnOptionalSql(String sql, String alias, Iterable<BindVariable> binds, Class<T> clazz) {
        return columnOptional(columnSql(sql, alias, binds), clazz);
    }

    @Nonnull
    public SqlTableAlias tableAlias(String tableAlias) {
        return new SqlTableAliasImpl(tableAlias);
    }

    @Nonnull
    public SqlFrom from(SqlIdentifier tableName, SqlTableAlias alias) {
        return new SqlFromSimple(tableName, alias);
    }

    @Nonnull
    public SqlFrom from(String tableName, String alias) {
        return from(name(tableName), tableAlias(alias));
    }

    @Nonnull
    public SqlFrom fromDirect(String sqlSelect, SqlTableAlias alias) {
        return new SqlFromSql(sqlSelect, alias);
    }

    @Nonnull
    public SqlFrom fromDirect(String sqlSelect, String alias) {
        return fromDirect(sqlSelect, tableAlias(alias));
    }

    @Nonnull
    public SqlFrom fromSql(String sqlSelect, SqlTableAlias alias) {
        var builder = tokenizer.getBinds(sqlSelect);
        return new SqlFromSql(builder.build(), alias, builder.getBinds());
    }

    @Nonnull
    public SqlFrom fromSql(String sqlSelect, String alias) {
        return fromSql(sqlSelect, tableAlias(alias));
    }

    @Nonnull
    public SqlFrom from(Select select, SqlTableAlias alias) {
        return new SqlFromSelect(select, alias);
    }

    @Nonnull
    public SqlFrom from(Select select, String alias) {
        return new SqlFromSelect(select, tableAlias(alias));
    }

    @Nonnull
    public SqlFrom fromDual() {
        return SqlFromDual.getInstance();
    }

    @Nonnull
    public Condition conditionDirect(String conditionSql) {
        return new ConditionSimple(conditionSql);
    }

    @Nonnull
    public Condition conditionDirect(String conditionSql, BindName... binds) {
        return conditionDirect(conditionSql, Arrays.asList(binds));
    }

    @Nonnull
    public Condition conditionDirect(String conditionSql, List<BindName> binds) {
        return new ConditionSimpleWithBinds(conditionSql, binds);
    }

    @Nonnull
    public Condition conditionSql(String conditionSql) {
        return conditionSql(conditionSql, Collections.emptyList());
    }

    @Nonnull
    public Condition conditionSql(String conditionSql, BindVariable... binds) {
        return conditionSql(conditionSql, Arrays.asList(binds));
    }

    @Nonnull
    public Condition conditionSql(String conditionSql, Iterable<BindVariable> binds) {
        var builder = tokenizer.getBinds(conditionSql).applyBindVariables(binds);
        return new ConditionSimpleWithBinds(builder.build(), builder.getBinds());
    }

    @Nonnull
    public Condition conditionAnd(Condition... whereConditions) {
        return conditionAnd(Arrays.asList(whereConditions));
    }

    @Nonnull
    public Condition conditionAnd(Collection<Condition> whereConditions) {
        // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
        return new ConditionJoinerImpl(SqlConditionOperator.AND, whereConditions).build();
    }

    @Nonnull
    public ConditionJoiner conditionAndJoiner() {
        return new ConditionJoinerImpl(SqlConditionOperator.AND);
    }

    @Nonnull
    public Condition conditionOr(Condition... whereConditions) {
        return conditionOr(Arrays.asList(whereConditions));
    }

    @Nonnull
    public Condition conditionOr(Collection<Condition> whereConditions) {
        // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
        return new ConditionJoinerImpl(SqlConditionOperator.OR, whereConditions).build();
    }

    @Nonnull
    public ConditionJoiner conditionOrJoiner() {
        return new ConditionJoinerImpl(SqlConditionOperator.OR);
    }

    @Nonnull
    private <T> Condition compare(ExpressionT<T> first, ExpressionT<T> second, SqlSymbol comparison) {
        return new ConditionCompare(first, second, comparison);
    }

    @Nonnull
    public <T> Condition eq(ExpressionT<T> first, ExpressionT<T> second) {
        return compare(first, second, SqlSymbol.EQUAL);
    }

    @Nonnull
    public <T> Condition notEq(ExpressionT<T> first, ExpressionT<T> second) {
        return compare(first, second, SqlSymbol.NOT_EQUAL);
    }

    @Nonnull
    public <T> Condition lessThan(ExpressionT<T> first, ExpressionT<T> second) {
        return compare(first, second, SqlSymbol.LESS_THAN);
    }

    @Nonnull
    public <T> Condition lessOrEqual(ExpressionT<T> first, ExpressionT<T> second) {
        return compare(first, second, SqlSymbol.LESS_OR_EQUAL);
    }

    @Nonnull
    public <T> Condition greaterThan(ExpressionT<T> first, ExpressionT<T> second) {
        return compare(first, second, SqlSymbol.GRATER_THAN);
    }

    @Nonnull
    public <T> Condition greaterOrEqual(ExpressionT<T> first, ExpressionT<T> second) {
        return compare(first, second, SqlSymbol.GREATER_OR_EQUAL);
    }

    @Nonnull
    public <T> Condition isNull(ExpressionT<T> first) {
        return new ConditionIsNull(first);
    }
}
