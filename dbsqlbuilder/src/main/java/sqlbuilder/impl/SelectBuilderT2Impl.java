package sqlbuilder.impl;

import com.provys.provysdb.sql.BindName;
import sqlbuilder.BindValueBuilder;
import sqlbuilder.Condition;
import sqlbuilder.SelectExpressionBuilder;
import sqlbuilder.SelectBuilderT2;
import sqlbuilder.Sql;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import sqlbuilder.FromClause;
import com.provys.provysdb.sql.SimpleName;
import sqlbuilder.QueryAlias;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SelectBuilderT2Impl<S extends Sql, T1, T2> extends
    SelectBuilderBaseImpl<SelectBuilderT2Impl<S, T1, T2>, S>
    implements SelectBuilderT2<T1, T2> {

  private final SqlColumn<T1> column1;
  private final SqlColumn<T2> column2;

  /**
   * Create new builder based on specified statement builder with two supplied columns.
   *
   * @param sql     is parent select builder
   * @param column1 is the first column
   * @param column2 is the second column
   */
  public SelectBuilderT2Impl(S sql, SqlColumn<T1> column1, SqlColumn<T2> column2) {
    super(sql);
    this.column1 = Objects.requireNonNull(column1);
    this.column2 = Objects.requireNonNull(column2);
  }

  SelectBuilderT2Impl(S sql, SqlColumn<T1> column1, SqlColumn<T2> column2, List<FromClause> tables,
      Collection<Condition> conditions) {
    super(sql, List.of(column1), tables, conditions);
    this.column1 = Objects.requireNonNull(column1);
    this.column2 = Objects.requireNonNull(column2);
  }

  @Override
  SelectBuilderT2Impl<S, T1, T2> self() {
    return this;
  }

  @Override
  public List<SqlColumn> getColumns() {
    return List.of(column1, column2);
  }

  @Override
  protected List<SqlColumn> getModifiableColumns() {
    return List.of(column1, column2);
  }

  @Override
  public SelectBuilderT2Impl<S, T1, T2> copy() {
    return new SelectBuilderT2Impl<>(getSql(), column1, column2, getTables(), getConditions());
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SqlColumn<T> column) {
    return new SelectBuilderImpl<>(getSql(), List.of(column1, column2, column), getTables(),
        getConditions());
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SimpleName column, Class<T> clazz) {
    if (getTables().isEmpty()) {
      return column(getSql().column(column, clazz));
    }
    return column(
        getSql().column(getTables().get(getTables().size() - 1).getAlias(), column, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SimpleName column, SimpleName alias,
      Class<T> clazz) {
    if (getTables().isEmpty()) {
      return column(getSql().column(column, alias, clazz));
    }
    return column(
        getSql().column(getTables().get(getTables().size() - 1).getAlias(), column, alias, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> column(QueryAlias tableAlias, SimpleName column,
      SimpleName alias,
      Class<T> clazz) {
    return column(getSql().column(tableAlias, column, alias, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String columnName, Class<T> clazz) {
    if (getTables().isEmpty()) {
      return column(getSql().column(columnName, clazz));
    }
    return column(getSql()
        .column(getTables().get(getTables().size() - 1).getAlias(), getSql().name(columnName),
            clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, Class<T> clazz) {
    return column(getSql().column(tableAlias, columnName, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return column(getSql().column(tableAlias, columnName, alias, clazz));
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
    return column(getSql().columnDirect(columnSql, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String columnSql, String alias, Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, alias, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String columnSql, String alias, Class<T> clazz,
      BindName... binds) {
    return column(getSql().columnDirect(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String columnSql, String alias,
      Collection<? extends BindName> binds, Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, alias, binds, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, Class<T> clazz) {
    return column(getSql().columnSql(columnSql, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz) {
    return column(getSql().columnSql(columnSql, alias, clazz));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz,
      BindValueBuilder... binds) {
    return column(getSql().columnSql(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias,
      Collection<? extends BindValueBuilder> binds, Class<T> clazz) {
    return column(getSql().columnSql(columnSql, alias, binds, clazz));
  }

  @Override
  public String toString() {
    return "SelectBuilderT2Impl{"
        + "column1=" + column1
        + ", column2=" + column2
        + ", " + super.toString() + '}';
  }
}
