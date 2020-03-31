package sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sql.BindName;
import sqlbuilder.BindValueBuilder;
import com.provys.provysdb.sql.CodeBuilder;
import sqlbuilder.Condition;
import sqlbuilder.SelectExpressionBuilder;
import com.provys.provysdb.sql.Select;
import sqlbuilder.Sql;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import sqlbuilder.FromClause;
import com.provys.provysdb.sql.SimpleName;
import sqlbuilder.QueryAlias;
import com.provys.db.sqldb.sql.codebuilder.CodeBuilderImpl;
import com.provys.db.sqldb.sql.name.elements.ConditionJoined;
import com.provys.db.sqldb.sql.name.elements.SqlConditionOperator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({"UnusedReturnValue", "ClassReferencesSubclass"})
public abstract class SelectBuilderBaseImpl<T extends SelectBuilderBaseImpl<T, S>, S extends Sql> {

  private final S sql;
  private final Map<SimpleName, SqlColumn> columnByName;
  private final List<FromClause> tables;
  private final Map<QueryAlias, FromClause> tableByAlias;
  private final List<Condition> conditions;

  SelectBuilderBaseImpl(S sql) {
    this.sql = Objects.requireNonNull(sql);
    columnByName = new ConcurrentHashMap<>(5);
    tables = new ArrayList<>(2);
    tableByAlias = new ConcurrentHashMap<>(2);
    conditions = new ArrayList<>(5);
  }

  SelectBuilderBaseImpl(S sql, Collection<SqlColumn> columns, List<FromClause> tables,
      Collection<Condition> conditions) {
    this.sql = Objects.requireNonNull(sql);
    this.columnByName = columns.stream()
        .filter(column -> column.getAlias() != null)
        .collect(Collectors
            .toConcurrentMap(column -> column.getOptAlias().orElseThrow(), Function.identity()));
    this.tables = new ArrayList<>(tables);
    this.tableByAlias = tables.stream()
        .collect(Collectors.toConcurrentMap(FromClause::getAlias, Function.identity()));
    this.conditions = new ArrayList<>(conditions);
  }

  void mapColumn(SimpleName alias, SqlColumn column) {
    if (columnByName.putIfAbsent(alias, column) != null) {
      throw new InternalException(
          "Attempt to insert duplicate column to column list (" + alias.getText()
              + " , " + this.toString() + ')');
    }
  }

  /**
   * Value of field com.provys.db.sql.
   *
   * @return value of field com.provys.db.sql
   */
  public S getSql() {
    return sql;
  }

  /**
   * Tables in from clause.
   *
   * @return value of field tables
   */
  public List<FromClause> getTables() {
    return Collections.unmodifiableList(tables);
  }

  /**
   * Conditions.
   *
   * @return value of field conditions
   */
  public List<Condition> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  abstract T self();

  /**
   * Alias of table that has been latest added.
   *
   * @return alias of table that has been added last, empty Optional if there are no tables.
   */
  Optional<QueryAlias> getLastTableAlias() {
    if (getTables().isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(tables.get(tables.size() - 1).getAlias());
  }

  public abstract List<SqlColumn> getColumns();

  List<SqlColumn> getModifiableColumns() {
    return new ArrayList<>(getColumns());
  }

  SelectBuilderImpl<S> columnUntyped(SqlColumn column) {
    var columns = getModifiableColumns();
    columns.add(column);
    return new SelectBuilderImpl<>(sql, columns, Collections.unmodifiableList(tables),
        Collections.unmodifiableList(conditions));
  }

  public SelectBuilderImpl<S> column(SimpleName column) {
    return columnUntyped(sql.column(column));
  }

  public SelectBuilderImpl<S> column(SimpleName column, SimpleName alias) {
    return columnUntyped(sql.column(column, alias));
  }

  public SelectBuilderImpl<S> column(QueryAlias tableAlias, SimpleName column,
      SimpleName alias) {
    return columnUntyped(sql.column(tableAlias, column, alias));
  }

  /**
   * Add column to list of columns. It is expected to come from last item, added to from clause. If
   * no items were added to from clause, column is added as is, without table alias
   *
   * @param columnName is name of column; it should be column in last item, added to from clause. It
   *                   must be valid column name (in "" or first character letter and remaining
   *                   letters, numbers and characters $ and #). Use columnSql to add columns based
   *                   on com.provys.db.sql expressions
   * @return self to support fluent build
   */
  public SelectBuilderImpl<S> column(String columnName) {
    return getLastTableAlias()
        .map(tableAlias -> columnUntyped(sql.column(tableAlias, sql.name(columnName))))
        .orElseGet(() -> column(sql.name(columnName)));
  }

  public SelectBuilderImpl<S> column(String tableAlias, String columnName) {
    return columnUntyped(sql.column(tableAlias, columnName));
  }

  public SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias) {
    return columnUntyped(sql.column(tableAlias, columnName, alias));
  }

  public SelectBuilderImpl<S> column(SelectExpressionBuilder expression, SimpleName alias) {
    return columnUntyped(sql.column(expression, alias));
  }

  public SelectBuilderImpl<S> column(SelectExpressionBuilder expression, String alias) {
    return columnUntyped(sql.column(expression, alias));
  }

  public SelectBuilderImpl<S> columnDirect(String columnSql) {
    return columnUntyped(sql.columnDirect(columnSql));
  }

  public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias) {
    return columnUntyped(sql.columnDirect(sqlColumn, alias));
  }

  public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias, BindName... binds) {
    return columnUntyped(sql.columnDirect(sqlColumn, alias, binds));
  }

  public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias,
      Collection<? extends BindName> binds) {
    return columnUntyped(sql.columnDirect(sqlColumn, alias, binds));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql) {
    return columnUntyped(sql.columnSql(columnSql));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql, String alias) {
    return columnUntyped(sql.columnSql(columnSql, alias));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql, String alias, BindValueBuilder... binds) {
    return columnUntyped(sql.columnSql(columnSql, alias, binds));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql, String alias,
      Collection<? extends BindValueBuilder> binds) {
    return columnUntyped(sql.columnSql(columnSql, alias, binds));
  }

  private void mapTable(QueryAlias alias, FromClause table) {
    if (tableByAlias.putIfAbsent(alias, table) != null) {
      throw new InternalException(
          "Attempt to insert duplicate table to from list (" + alias.getAlias()
              + " , " + this.toString() + ')');
    }
  }

  /**
   * Add table to from clause of the statement.
   *
   * @param table is table definition (potentially with join condition)
   * @return self to support fluent build
   */
  public T from(FromClause table) {
    mapTable(table.getAlias(), table);
    tables.add(table);
    return self();
  }

  public T from(SimpleName tableName, QueryAlias alias) {
    return from(sql.from(tableName, alias));
  }

  public T from(String tableName, String alias) {
    return from(sql.from(tableName, alias));
  }

  public T from(Select select, QueryAlias alias) {
    return from(sql.from(select, alias));
  }

  public T from(Select select, String alias) {
    return from(sql.from(select, alias));
  }

  public T fromDirect(String sqlSelect, QueryAlias alias) {
    return from(sql.fromDirect(sqlSelect, alias));
  }

  public T fromDirect(String sqlSelect, String alias) {
    return from(sql.fromDirect(sqlSelect, alias));
  }

  public T fromSql(String sqlSelect, QueryAlias alias) {
    return from(sql.fromSql(sqlSelect, alias));
  }

  public T fromSql(String sqlSelect, String alias) {
    return from(sql.fromSql(sqlSelect, alias));
  }

  public T fromDual() {
    return from(sql.fromDual());
  }

  /**
   * Add where condition.
   *
   * @param where is com.provys.db.sql where condition to be added (or null, in tht case nothing is added)
   * @return self to support fluent build
   */
  public T where(@Nullable Condition where) {
    if ((where != null) && !where.isEmpty()) {
      conditions.add(where);
    }
    return self();
  }

  public T whereDirect(String conditionSql) {
    return where(sql.conditionDirect(conditionSql));
  }

  public T whereDirect(String conditionSql, BindName... binds) {
    return where(sql.conditionDirect(conditionSql, binds));
  }

  public T whereDirect(String conditionSql, Collection<? extends BindName> binds) {
    return where(sql.conditionDirect(conditionSql, binds));
  }

  public T whereSql(String conditionSql) {
    return where(sql.conditionSql(conditionSql));
  }

  public T whereSql(String conditionSql, BindValueBuilder... binds) {
    return where(sql.conditionSql(conditionSql, binds));
  }

  public T whereSql(String conditionSql, Collection<? extends BindValueBuilder> binds) {
    return where(sql.conditionSql(conditionSql, binds));
  }

  public T whereAnd(Condition... whereConditions) {
    return where(sql.conditionAnd(whereConditions));
  }

  public T whereAnd(Collection<Condition> whereConditions) {
    return where(sql.conditionAnd(whereConditions));
  }

  public T whereOr(Condition... whereConditions) {
    return where(sql.conditionOr(whereConditions));
  }

  public T whereOr(Collection<Condition> whereConditions) {
    return where(sql.conditionOr(whereConditions));
  }

  private static void addConditions(Iterable<? extends Condition> conditions, CodeBuilder builder) {
    for (var condition : conditions) {
      if ((condition instanceof ConditionJoined)
          && ((ConditionJoined) condition).getOperator() == SqlConditionOperator.AND) {
        addConditions(((ConditionJoined) condition).getConditions(), builder);
      } else {
        condition.addSql(builder);
      }
    }
  }

  /**
   * Create copy of itself. Non-mutable objects can be returned as they are but mutable objects
   * (including this) has to be deep copied
   *
   * @return copy of itself
   */
  public abstract T copy();

  /**
   * Internal method, used to create new CodeBuilder and populate it with SELECT, based on this
   * select builder.
   *
   * @return code builder with populated items from this select builder
   */
  public CodeBuilder builder() {
    var builder = new CodeBuilderImpl()
        .appendLine("SELECT")
        .increasedIdent("", ", ", 4);
    for (var column : getColumns()) {
      column.addSql(builder);
      column.getOptAlias()
          .ifPresent(alias -> builder.append(' ').append(alias));
      builder.appendLine();
    }
    builder.popIdent()
        .appendLine("FROM")
        .increasedIdent("", ", ", 4);
    for (var table : tables) {
      table.addSql(builder);
      builder.appendLine();
    }
    builder.popIdent();
    if (!conditions.isEmpty()) {
      builder.appendLine("WHERE")
          .increasedIdent("", "AND ", 6);
      addConditions(conditions, builder);
      builder.popIdent();
    }
    return builder;
  }

  public Select build() {
    var builder = builder();
    return new SelectImpl(builder.build(), builder.getBindsWithPos());
  }

  @Override
  public String toString() {
    return "SelectBuilderBaseImpl{"
        + "com.provys.db.sql=" + sql
        + ", columnByName=" + columnByName
        + ", tables=" + tables
        + ", tableByAlias=" + tableByAlias
        + ", conditions=" + conditions
        + '}';
  }
}
