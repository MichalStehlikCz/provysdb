package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.elements.ElementFactory;
import com.provys.provysdb.sqlbuilder.elements.SqlColumnExpressionT;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class SqlImpl extends ElementFactory {

  private final SqlTokenizer tokenizer;

  protected SqlImpl(SqlTokenizer tokenizer) {
    this.tokenizer = Objects.requireNonNull(tokenizer);
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
  public <T> SqlColumn<T> columnSql(String columnSql, Class<T> clazz) {
    return column(columnSql(columnSql), clazz);
  }

  @Override
  public <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz) {
    return column(columnSql(sql, alias), clazz);
  }

  @Override
  public <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz, BindValue... binds) {
    return column(columnSql(sql, alias, binds), clazz);
  }

  @Override
  public <T> SqlColumn<T> columnSql(String sql, String alias,
      Collection<? extends BindValue> binds,
      Class<T> clazz) {
    return column(columnSql(sql, alias, binds), clazz);
  }

  @Override
  public SqlFrom fromSql(String sqlSelect, SqlTableAlias alias) {
    var builder = tokenizer.normalize(sqlSelect);
    return fromDirect(builder.build(), alias, builder.getBinds());
  }

  @Override
  public SqlFrom fromSql(String sqlSelect, String alias) {
    return fromSql(sqlSelect, tableAlias(alias));
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
    return conditionDirect(builder.build(), builder.getBinds());
  }

  @Override
  public String toString() {
    return "SqlImpl{"
        + "tokenizer=" + tokenizer
        + '}';
  }
}
