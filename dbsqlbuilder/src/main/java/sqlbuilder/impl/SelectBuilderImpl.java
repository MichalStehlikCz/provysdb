package sqlbuilder.impl;

import com.provys.provysdb.sql.BindName;
import sqlbuilder.BindValueBuilder;
import sqlbuilder.Condition;
import sqlbuilder.SelectExpressionBuilder;
import sqlbuilder.SelectBuilder;
import sqlbuilder.Sql;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import sqlbuilder.FromClause;
import com.provys.provysdb.sql.SimpleName;
import sqlbuilder.QueryAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SelectBuilderImpl<S extends Sql> extends
    SelectBuilderBaseImpl<SelectBuilderImpl<S>, S> implements SelectBuilder {

  private final List<SqlColumn> columns;

  public SelectBuilderImpl(S sql) {
    super(sql);
    columns = new ArrayList<>(5);
  }

  SelectBuilderImpl(S sql, List<SqlColumn> columns, List<FromClause> tables,
      Collection<Condition> conditions) {
    super(sql, columns, tables, conditions);
    this.columns = new ArrayList<>(columns);
  }

  @Override
  SelectBuilderImpl<S> self() {
    return this;
  }

  @Override
  public List<SqlColumn> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  @Override
  SelectBuilderImpl<S> columnUntyped(SqlColumn column) {
    column.getOptAlias()
        .ifPresent(alias -> mapColumn(alias, column));
    columns.add(column);
    return this;
  }

  @Override
  public SelectBuilderImpl<S> column(SqlColumn column) {
    return columnUntyped(column);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SimpleName column, Class<T> clazz) {
    return column(column);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SimpleName column, SimpleName alias,
      Class<T> clazz) {
    return column(column, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(QueryAlias tableAlias, SimpleName column,
      SimpleName alias, Class<T> clazz) {
    return column(tableAlias, column, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String columnName, Class<T> clazz) {
    return column(columnName);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, Class<T> clazz) {
    return column(tableAlias, columnName);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return column(tableAlias, columnName, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SelectExpressionBuilder<T> expression, SimpleName alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SelectExpressionBuilder<T> expression, String alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String columnSql, Class<T> clazz) {
    return columnDirect(columnSql);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias, Class<T> clazz) {
    return columnDirect(sql, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias, Class<T> clazz,
      BindName... binds) {
    return columnDirect(sql, alias, binds);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias,
      Collection<? extends BindName> binds, Class<T> clazz) {
    return columnDirect(sql, alias, binds);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, Class<T> clazz) {
    return columnSql(columnSql);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz) {
    return columnSql(columnSql, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz,
      BindValueBuilder... binds) {
    return columnSql(columnSql, alias, binds);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias,
      Collection<? extends BindValueBuilder> binds, Class<T> clazz) {
    return columnSql(columnSql, alias, binds);
  }

  @Override
  public SelectBuilderImpl<S> copy() {
    return new SelectBuilderImpl<>(getSql(), columns, getTables(), getConditions());
  }

  @Override
  public String toString() {
    return "SelectBuilderImpl{"
        + "columns=" + columns
        + ", " + super.toString() + '}';
  }
}
