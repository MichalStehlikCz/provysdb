package com.provys.db.query.elements;

import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface QueryConsumer {

  /**
   * Consume expression, based on supplied bind variable.
   *
   * @param type         is type of expression
   * @param bindVariable is bind variable that will be used as basis of expression
   */
  void bind(Class<?> type, BindVariable bindVariable);

  /**
   * Consume column expression, based on specified source (identified by alias), column name and
   * type.
   *
   * @param type   is type that given expression should yield
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   */
  void column(Class<?> type, @Nullable NamePath table, SimpleName column);

  /**
   * Consume function that will evaluate to supplied type, based on supplied function and using
   * arguments. Type of function and arguments are validated against function definition.
   *
   * @param type      is type of object expression returns
   * @param function  is built-in function, used for evaluation
   * @param arguments are arguments to be passed to function
   */
  void function(Class<?> type, Function function, Collection<? extends Expression<?>> arguments);

  /**
   * Consume literal expression of given type and value.
   *
   * @param type  is type of expression
   * @param value is expression value
   * @param <T>   is type of expression
   */
  <T> void literal(Class<T> type, @Nullable T value);

  /**
   * Consume select query based on supplied select, from and where clauses.
   *
   * @param selectClause is select (projection) part of query
   * @param fromClause   is from (sources) part of query
   * @param whereClause  is where (conditions) part of query
   */
  void select(SelectClause selectClause, FromClause fromClause, @Nullable Condition whereClause);

  /**
   * Consume select query based on supplied columns, from and where clauses.
   *
   * @param columns     is collection of columns
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   */
  void select(Collection<? extends SelectColumn<?>> columns, FromClause fromClause,
      @Nullable Condition whereClause);

  /**
   * Consume select query based on supplied column, from and where clauses.
   *
   * @param column1     is the first and only column of query
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   */
  default void select(SelectColumn<?> column1, FromClause fromClause,
      @Nullable Condition whereClause) {
    select(List.of(column1), fromClause, whereClause);
  }

  /**
   * Consume select query based on supplied columns, from and where clauses.
   *
   * @param column1     is the first column of query
   * @param column2     is the second column of query
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   */
  default void select(SelectColumn<?> column1, SelectColumn<?> column2, FromClause fromClause,
      @Nullable Condition whereClause) {
    select(List.of(column1, column2), fromClause, whereClause);
  }

  /**
   * Consume select part of query, based on supplied columns.
   *
   * @param columns is collection of columns to be used for result projection
   */
  void selectColumns(Collection<? extends SelectColumn<?>> columns);

  /**
   * Consume select column, based on supplied expression, using alias.
   *
   * @param expression is expression column is based on
   * @param alias      is alias used for column, null means use no or default alias
   */
  void selectColumn(Expression<?> expression, @Nullable SimpleName alias);

  /**
   * Consume from clause based on supplied from elements.
   *
   * @param fromElements is collection of from elements this from clause is based on
   */
  void from(Collection<? extends FromElement> fromElements);

  /**
   * Consume from element, based on supplied table and optionally using supplied alias.
   *
   * @param tableName is name, identifying data source
   * @param alias     is alias to be used by columns to refer to this source
   */
  void fromTable(NamePath tableName, @Nullable SimpleName alias);

  /**
   * Consume from element, based on select statement.
   *
   * @param select is select statement from element is based on
   * @param alias is alias used to refer to this from element
   */
  void fromSelect(Select select, @Nullable SimpleName alias);

  /**
   * Consume from element, based on dual pseudo-table (or however no table in from is
   * represented).
   *
   * @param alias is alias used to refer to this from element
   */
  void fromDual(@Nullable SimpleName alias);

  /**
   * Create new condition with equals comparison of two expressions.
   *
   * @param expression1 is the first operand of comparison
   * @param expression2 is the second operand of comparison
   * @param <T> is type of items being compared
   */
  <T> void eq(Expression<T> expression1, Expression<T> expression2);
}
