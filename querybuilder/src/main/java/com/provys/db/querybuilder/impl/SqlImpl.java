package com.provys.db.querybuilder.impl;

import com.provys.db.querybuilder.BindValueBuilder;
import com.provys.db.querybuilder.Condition;
import com.provys.db.querybuilder.FromClause;
import com.provys.db.querybuilder.elements.ElementFactory;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import com.provys.db.querybuilder.QueryAlias;
import com.provys.db.sqlquery.queryold.name.elements.SqlColumnExpressionT;
import sqlparser.SqlTokenizer;
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
    return columnDirect(builder.build(), null, builder.getBindsWithPos());
  }

  @Override
  public SqlColumn columnSql(String sql, String alias) {
    return columnSql(sql, alias, Collections.emptyList());
  }

  @Override
  public SqlColumn columnSql(String sql, @Nullable String alias, BindValueBuilder... binds) {
    return columnSql(sql, alias, Arrays.asList(binds));
  }

  @Override
  public SqlColumn columnSql(String sql, @Nullable String alias,
      Collection<? extends BindValueBuilder> binds) {
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
  public <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz, BindValueBuilder... binds) {
    return column(columnSql(sql, alias, binds), clazz);
  }

  @Override
  public <T> SqlColumn<T> columnSql(String sql, String alias,
      Collection<? extends BindValueBuilder> binds,
      Class<T> clazz) {
    return column(columnSql(sql, alias, binds), clazz);
  }

  @Override
  public FromClause fromSql(String sqlSelect, QueryAlias alias) {
    var builder = tokenizer.normalize(sqlSelect);
    return fromDirect(builder.build(), alias, builder.getBindsWithPos());
  }

  @Override
  public FromClause fromSql(String sqlSelect, String alias) {
    return fromSql(sqlSelect, tableAlias(alias));
  }

  @Override
  public Condition conditionSql(String conditionSql) {
    return conditionSql(conditionSql, Collections.emptyList());
  }

  @Override
  public Condition conditionSql(String conditionSql, BindValueBuilder... binds) {
    return conditionSql(conditionSql, Arrays.asList(binds));
  }

  @Override
  public Condition conditionSql(String conditionSql, Collection<? extends BindValueBuilder> binds) {
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
