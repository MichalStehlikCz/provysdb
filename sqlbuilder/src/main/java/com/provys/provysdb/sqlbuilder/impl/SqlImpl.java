package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.BindValueT;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.ConditionJoiner;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.ExpressionT;
import com.provys.provysdb.sqlbuilder.LiteralT;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.Sql;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.SqlTableColumn;
import com.provys.provysdb.sqlbuilder.SqlTableColumnT;
import com.provys.provysdb.sqlparser.SqlSymbol;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlImpl implements Sql {

  private final SqlTokenizer tokenizer;

  protected SqlImpl(SqlTokenizer tokenizer) {
    this.tokenizer = Objects.requireNonNull(tokenizer);
  }

  @Override
  public LiteralT<String> literal(String value) {
    return LiteralVarchar.of(value);
  }

  @Override
  public LiteralT<Byte> literal(byte value) {
    return LiteralByte.of(value);
  }

  @Override
  public LiteralT<Short> literal(short value) {
    return LiteralShort.of(value);
  }

  @Override
  public LiteralT<Integer> literal(int value) {
    return LiteralInt.of(value);
  }

  @Override
  public LiteralT<Long> literal(long value) {
    return LiteralLong.of(value);
  }

  @Override
  public LiteralT<BigInteger> literal(BigInteger value) {
    return LiteralBigInteger.of(value);
  }

  @Override
  public LiteralT<Float> literal(float value) {
    return LiteralFloat.of(value);
  }

  @Override
  public LiteralT<Double> literal(double value) {
    return LiteralDouble.of(value);
  }

  @Override
  public LiteralT<BigDecimal> literal(BigDecimal value) {
    return LiteralBigDecimal.of(value);
  }

  @Override
  public LiteralT<Boolean> literal(boolean value) {
    return LiteralBoolean.of(value);
  }

  @Override
  public LiteralT<DtUid> literal(DtUid value) {
    return LiteralDtUid.of(value);
  }

  @Override
  public LiteralT<DtDate> literal(DtDate value) {
    return LiteralDate.of(value);
  }

  @Override
  public LiteralT<DtDateTime> literal(DtDateTime value) {
    return LiteralDateTime.of(value);
  }

  @Override
  public LiteralT<String> literalNVarchar(String value) {
    return LiteralNVarchar.of(value);
  }

  @Override
  public BindName bind(String name) {
    return new BindNameImpl(name);
  }

  @Override
  public <T> BindValueT<T> bind(BindName bindName, @NonNull T value) {
    return BindValueImpl.ofObject(bindName.getName(), value);
  }

  @Override
  public <T> BindValueT<T> bind(String name, @NonNull T value) {
    return BindValueImpl.ofObject(name, value);
  }

  @Override
  public <T> BindValueT<T> bind(BindName bindName, @Nullable T value, Class<T> clazz) {
    return bind(bindName.getName(), value, clazz);
  }

  @Override
  public <T> BindValueT<T> bind(String name, @Nullable T value, Class<T> clazz) {
    return BindValueImpl.ofType(name, value, clazz);
  }

  @Override
  public <T> BindValueT<T> bindEmpty(String name, Class<T> clazz) {
    return BindValueImpl.ofType(name, null, clazz);
  }

  @Override
  public SqlIdentifierImpl name(String name) {
    return SqlIdentifierImpl.parse(name);
  }

  @Override
  public SqlTableColumn column(SqlIdentifier column) {
    return new SqlColumnSimple(null, column, null);
  }

  @Override
  public SqlTableColumn column(SqlIdentifier column, @Nullable SqlIdentifier alias) {
    return new SqlColumnSimple(null, column, alias);
  }

  @Override
  public SqlTableColumn column(@Nullable SqlTableAlias tableAlias, SqlIdentifier column) {
    return new SqlColumnSimple(tableAlias, column, null);
  }

  @Override
  public SqlTableColumn column(@Nullable SqlTableAlias tableAlias, SqlIdentifier column,
      SqlIdentifier alias) {
    return new SqlColumnSimple(tableAlias, column, alias);
  }

  @Override
  public SqlTableColumn column(String columnName) {
    return new SqlColumnSimple(null, name(columnName), null);
  }

  @Override
  public SqlTableColumn column(String tableAlias, String columnName) {
    return new SqlColumnSimple(tableAlias(tableAlias), name(columnName), null);
  }

  @Override
  public SqlTableColumn column(String tableAlias, String columnName, String alias) {
    return column(tableAlias(tableAlias), name(columnName), name(alias));
  }

  @Override
  public SqlColumn column(Expression expression, SqlIdentifier alias) {
    return new SqlColumnExpression(expression, alias);
  }

  @Override
  public SqlColumn column(Expression expression, String alias) {
    return column(expression, name(alias));
  }

  @Override
  public <T> SqlColumnT<T> column(ExpressionT<T> expression, @Nullable SqlIdentifier alias) {
    return new SqlColumnExpressionT<>(expression, alias);
  }

  @Override
  public <T> SqlColumnT<T> column(ExpressionT<T> expression, String alias) {
    return column(expression, name(alias));
  }

  @Override
  public <T> SqlColumnT<T> column(SqlColumn column, Class<T> clazz) {
    return new SqlColumnTImpl<>(column, clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(SqlTableColumn column, Class<T> clazz) {
    return new SqlTableColumnTImpl<>(column, clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(SqlIdentifier column, Class<T> clazz) {
    return column(column(column), clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
    return column(column(column, alias), clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column,
      Class<T> clazz) {
    return column(column(tableAlias, column), clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column,
      SqlIdentifier alias, Class<T> clazz) {
    return column(column(tableAlias, column, alias), clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(String columnName, Class<T> clazz) {
    return column(column(columnName), clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(String tableAlias, String columnName, Class<T> clazz) {
    return column(column(tableAlias, columnName), clazz);
  }

  @Override
  public <T> SqlTableColumnT<T> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return column(column(tableAlias, columnName, alias), clazz);
  }

  @Override
  public SqlColumn columnDirect(String columnSql) {
    return new SqlColumnSql(columnSql, null);
  }

  @Override
  public SqlColumn columnDirect(String sql, String alias) {
    return new SqlColumnSql(sql, name(alias));
  }

  @Override
  public SqlColumn columnDirect(String sql, @Nullable String alias, BindName... binds) {
    return columnDirect(sql, alias, Arrays.asList(binds));
  }

  @Override
  public SqlColumn columnDirect(String sql, @Nullable String alias,
      List<? extends BindName> binds) {
    return new SqlColumnSql(sql, (alias == null) ? null : name(alias), binds);
  }

  @Override
  public <T> SqlColumnT<T> columnDirect(String columnSql, Class<T> clazz) {
    return column(columnDirect(columnSql), clazz);
  }

  @Override
  public <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz) {
    return column(columnDirect(sql, alias), clazz);
  }

  @Override
  public <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz,
      BindName... binds) {
    return column(columnDirect(sql, alias, binds), clazz);
  }

  @Override
  public <T> SqlColumnT<T> columnDirect(String sql, String alias, List<? extends BindName> binds,
      Class<T> clazz) {
    return column(columnDirect(sql, alias, binds), clazz);
  }

  @Override
  public SqlColumn columnSql(String columnSql) {
    var builder = tokenizer.normalize(columnSql);
    return columnDirect(builder.build(), null, builder.getBinds());
  }

  @Override
  public SqlColumn columnSql(String sql, String alias) {
    return columnSql(sql, alias, Collections.emptyList());
  }

  @Override
  public SqlColumn columnSql(String sql, @Nullable String alias, BindValue... binds) {
    return columnSql(sql, alias, Arrays.asList(binds));
  }

  @Override
  public SqlColumn columnSql(String sql, @Nullable String alias,
      Collection<? extends BindValue> binds) {
    var builder = tokenizer.normalize(sql).applyBindValues(binds);
    return columnDirect(builder.build(), alias, builder.getBinds());
  }

  @Override
  public <T> SqlColumnT<T> columnSql(String columnSql, Class<T> clazz) {
    return column(columnSql(columnSql), clazz);
  }

  @Override
  public <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz) {
    return column(columnSql(sql, alias), clazz);
  }

  @Override
  public <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz, BindValue... binds) {
    return column(columnSql(sql, alias, binds), clazz);
  }

  @Override
  public <T> SqlColumnT<T> columnSql(String sql, String alias,
      Collection<? extends BindValue> binds,
      Class<T> clazz) {
    return column(columnSql(sql, alias, binds), clazz);
  }

  @Override
  public SqlTableAlias tableAlias(String tableAlias) {
    return new SqlTableAliasImpl(tableAlias);
  }

  @Override
  public SqlFrom from(SqlIdentifier tableName, SqlTableAlias alias) {
    return new SqlFromSimple(tableName, alias);
  }

  @Override
  public SqlFrom from(String tableName, String alias) {
    return from(name(tableName), tableAlias(alias));
  }

  @Override
  public SqlFrom from(Select select, SqlTableAlias alias) {
    return new SqlFromSelect(select, alias);
  }

  @Override
  public SqlFrom from(Select select, String alias) {
    return new SqlFromSelect(select, tableAlias(alias));
  }

  @Override
  public SqlFrom fromDirect(String sqlSelect, SqlTableAlias alias) {
    return new SqlFromSql(sqlSelect, alias);
  }

  @Override
  public SqlFrom fromDirect(String sqlSelect, String alias) {
    return fromDirect(sqlSelect, tableAlias(alias));
  }

  @Override
  public SqlFrom fromSql(String sqlSelect, SqlTableAlias alias) {
    var builder = tokenizer.normalize(sqlSelect);
    return new SqlFromSql(builder.build(), alias, builder.getBinds());
  }

  @Override
  public SqlFrom fromSql(String sqlSelect, String alias) {
    return fromSql(sqlSelect, tableAlias(alias));
  }

  @Override
  public SqlFrom fromDual() {
    return SqlFromDual.getInstance();
  }

  @Override
  public Condition conditionDirect(String conditionSql) {
    return new ConditionSimple(conditionSql);
  }

  @Override
  public Condition conditionDirect(String conditionSql, BindName... binds) {
    return conditionDirect(conditionSql, Arrays.asList(binds));
  }

  @Override
  public Condition conditionDirect(String conditionSql, List<? extends BindName> binds) {
    return new ConditionSimpleWithBinds(conditionSql, binds);
  }

  @Override
  public Condition conditionSql(String conditionSql) {
    return conditionSql(conditionSql, Collections.emptyList());
  }

  @Override
  public Condition conditionSql(String conditionSql, BindValue... binds) {
    return conditionSql(conditionSql, Arrays.asList(binds));
  }

  @Override
  public Condition conditionSql(String conditionSql, Collection<? extends BindValue> binds) {
    var builder = tokenizer.normalize(conditionSql).applyBindValues(binds);
    return new ConditionSimpleWithBinds(builder.build(), builder.getBinds());
  }

  @Override
  public Condition conditionAnd(Condition... whereConditions) {
    return conditionAnd(Arrays.asList(whereConditions));
  }

  @Override
  public Condition conditionAnd(Collection<Condition> whereConditions) {
    // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
    return new ConditionJoinerImpl(SqlConditionOperator.AND, whereConditions).build();
  }

  @Override
  public ConditionJoiner conditionAndJoiner() {
    return new ConditionJoinerImpl(SqlConditionOperator.AND);
  }

  @Override
  public Condition conditionOr(Condition... whereConditions) {
    return conditionOr(Arrays.asList(whereConditions));
  }

  @Override
  public Condition conditionOr(Collection<Condition> whereConditions) {
    // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
    return new ConditionJoinerImpl(SqlConditionOperator.OR, whereConditions).build();
  }

  @Override
  public ConditionJoiner conditionOrJoiner() {
    return new ConditionJoinerImpl(SqlConditionOperator.OR);
  }

  private static <T> Condition compare(ExpressionT<T> first, ExpressionT<T> second,
      SqlSymbol comparison) {
    return new ConditionCompare<>(first, second, comparison);
  }

  @Override
  public <T> Condition eq(ExpressionT<T> first, ExpressionT<T> second) {
    return compare(first, second, SqlSymbol.EQUAL);
  }

  @Override
  public <T> Condition notEq(ExpressionT<T> first, ExpressionT<T> second) {
    return compare(first, second, SqlSymbol.NOT_EQUAL);
  }

  @Override
  public <T> Condition lessThan(ExpressionT<T> first, ExpressionT<T> second) {
    return compare(first, second, SqlSymbol.LESS_THAN);
  }

  @Override
  public <T> Condition lessOrEqual(ExpressionT<T> first, ExpressionT<T> second) {
    return compare(first, second, SqlSymbol.LESS_OR_EQUAL);
  }

  @Override
  public <T> Condition greaterThan(ExpressionT<T> first, ExpressionT<T> second) {
    return compare(first, second, SqlSymbol.GRATER_THAN);
  }

  @Override
  public <T> Condition greaterOrEqual(ExpressionT<T> first, ExpressionT<T> second) {
    return compare(first, second, SqlSymbol.GREATER_OR_EQUAL);
  }

  @Override
  public <T> Condition isNull(ExpressionT<T> first) {
    return new ConditionIsNull(first);
  }

  @Override
  public <T> ExpressionT<T> nvl(ExpressionT<T> first, ExpressionT<? extends T> second) {
    return new FuncNvl<>(first, second);
  }

  @Override
  @SafeVarargs
  public final <T> ExpressionT<T> coalesce(ExpressionT<T> first,
      ExpressionT<? extends T>... expressions) {
    return new FuncCoalesce<>(first, expressions);
  }

  @Override
  public String toString() {
    return "SqlImpl{"
        + "tokenizer=" + tokenizer
        + '}';
  }
}
