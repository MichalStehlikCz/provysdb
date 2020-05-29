package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.ImmutableTypeParameter;
import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.functions.ConditionalOperator;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.io.Serializable;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Allows creation of all types of query elements. Created elements are serializable and can be
 * serialized / deserialized to / from Xml using Jackson.
 */
@Immutable
public final class ElementFactory {

  private static final ElementFactory INSTANCE = new ElementFactory();

  /**
   * Get singleton instance of query factory.
   *
   * @return singleton instance of query factory
   */
  public static ElementFactory getInstance() {
    return INSTANCE;
  }

  private ElementFactory() {
  }

  /**
   * Create expression, based on supplied bind variable.
   *
   * @param type         is type of expression
   * @param bindVariable is bind variable that will be used as basis of expression
   * @param <T>          is type parameter denoting type of expression
   * @return expression based on bind variable
   */
  public <T> Expression<T> bind(Class<T> type, BindVariable bindVariable) {
    return new ExpressionBind<>(type, bindVariable);
  }

  /**
   * Create bind expression based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type parameter denoting type of expression
   * @return expression based on bind variable
   */
  public <@ImmutableTypeParameter T extends Serializable> Expression<T> bind(Class<T> type,
      BindName name, @Nullable T value) {
    return new ExpressionBind<>(type, new BindVariable(name, type, value));
  }

  /**
   * Create bind expression based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type parameter denoting type of expression
   * @return expression based on bind variable
   */
  public <@ImmutableTypeParameter T extends Serializable> Expression<T> bind(Class<T> type,
      BindName name) {
    return bind(type, name, null);
  }

  /**
   * Create bind expression based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type parameter denoting type of expression
   * @return expression based on bind variable
   */
  public <@ImmutableTypeParameter T extends Serializable> Expression<T> bind(Class<T> type,
      String name, @Nullable T value) {
    return new ExpressionBind<>(type, new BindVariable(name, type, value));
  }

  /**
   * Create bind expression based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type parameter denoting type of expression
   * @return expression based on bind variable
   */
  public <@ImmutableTypeParameter T extends Serializable> Expression<T> bind(Class<T> type,
      String name) {
    return bind(type, name, null);
  }

  /**
   * Create untyped bind expression based on supplied parameters.
   *
   * @param name is name of bind variable
   * @return expression based on bind variable
   */
  public Expression<Object> bind(BindName name) {
    return new ExpressionBind<>(Object.class, new BindVariable(name));
  }

  /**
   * Create untyped bind expression based on supplied parameters.
   *
   * @param name is name of bind variable
   * @return expression based on bind variable
   */
  public Expression<Object> bind(String name) {
    return new ExpressionBind<>(Object.class, new BindVariable(name));
  }

  /**
   * Create column expression, based on specified source (identified by alias), column name and
   * type. Expression is validated against supplied context.
   *
   * @param type        is type that given expression should yield
   * @param table       is alias identifying source
   * @param column      is name of column, evaluated in context of source
   * @param fromContext is context in which sources are evaluated; if left empty, no validation is
   *                    performed
   * @param <T>         is type parameter denoting type of expression
   * @return expression, representing single column / property from source
   */
  public <T> ExpressionColumn<T> column(Class<T> type, @Nullable NamePath table, SimpleName column,
      @Nullable FromContext fromContext) {
    return new ExpressionColumn<>(type, table, column, fromContext);
  }

  /**
   * Create column expression, based on specified source (identified by alias), column name and
   * type.
   *
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param type   is type that given expression should yield
   * @param <T>    is type parameter denoting type of expression
   * @return expression, representing single column / property from source
   */
  public <T> ExpressionColumn<T> column(Class<T> type, @Nullable NamePath table,
      SimpleName column) {
    return column(type, table, column, null);
  }

  /**
   * Create outer column expression (column with (+) sign, used to indicate outer join), based on
   * specified column.
   *
   * @param column is column expression is based on
   * @param <T>    is type parameter denoting type of expression
   * @return expression, representing single column / property from source, usable in outer join
   */
  public <T> Expression<T> columnOuter(ExpressionColumn<T> column) {
    return new ExpressionColumnOuter<>(column);
  }

  /**
   * Create outer column expression (column with (+) sign, used to indicate outer join), based on
   * specified source (identified by alias), column name and type. Expression is validated against
   * supplied context.
   *
   * @param type        is type that given expression should yield
   * @param table       is alias identifying source
   * @param column      is name of column, evaluated in context of source
   * @param fromContext is context in which sources are evaluated; if left empty, no validation is
   *                    performed
   * @param <T>         is type parameter denoting type of expression
   * @return expression, representing single column / property from source
   */
  public <T> Expression<T> columnOuter(Class<T> type, @Nullable NamePath table, SimpleName column,
      @Nullable FromContext fromContext) {
    return columnOuter(column(type, table, column, fromContext));
  }

  /**
   * Create outer column expression (column with (+) sign, used to indicate outer join), based on
   * specified source (identified by alias), column name and type.
   *
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param type   is type that given expression should yield
   * @param <T>    is type parameter denoting type of expression
   * @return expression, representing single column / property from source
   */
  public <T> Expression<T> columnOuter(Class<T> type, @Nullable NamePath table, SimpleName column) {
    return columnOuter(type, table, column, null);
  }

  /**
   * Create function that will evaluate to supplied type, based on supplied function and using
   * arguments. Type of function and arguments are validated against function definition.
   *
   * @param type      is type of object expression returns
   * @param function  is built-in function, used for evaluation
   * @param arguments are arguments to be passed to function
   * @param <T>       is type parameter denoting type of expression
   * @return function, representing built-in function applied on supplied arguments
   */
  public <T> Expression<T> function(Class<T> type, BuiltInFunction function,
      Collection<? extends Expression<?>> arguments) {
    return new ExpressionFunction<>(type, function, arguments);
  }

  /**
   * Create new literal expression of given type and value.
   *
   * @param type  is type of expression
   * @param value is expression value
   * @param <T>   is parameter denoting type of expression
   * @return new literal, representing supplied value
   */
  public <@ImmutableTypeParameter T extends Serializable> Expression<T> literal(Class<T> type,
      @Nullable T value) {
    return new Literal<>(type, value);
  }

  /**
   * Create new literal expression of given value. Not that it does not work correctly if value is
   * of generic type, as only class information is preserved, but generic parameters are lost.
   *
   * @param value is expression value
   * @param <T>   is parameter denoting type of expression
   * @return new literal, representing supplied value
   */
  public <@ImmutableTypeParameter T extends Serializable> Expression<T> literal(@NonNull T value) {
    return new Literal<>(value);
  }

  /**
   * Create new select query based on supplied select, from and where clauses, that can refer to
   * outside context and remap bind variables on creation.
   *
   * @param selectClause  is select (projection) part of query
   * @param fromClause    is from (sources) part of query
   * @param whereClause   is where (conditions) part of query
   * @param parentContext is parent context, that can define additional sources, available in query
   * @param bindMap       is bind map, used to remap bind variables on creation
   * @return created select query
   */
  public Select select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause, @Nullable FromContext parentContext,
      @Nullable BindMap bindMap) {
    return new SelectImpl(selectClause, fromClause, whereClause, parentContext, bindMap);
  }

  /**
   * Create new select query based on supplied select, from and where clauses, that can refer to
   * outside context.
   *
   * @param selectClause  is select (projection) part of query
   * @param fromClause    is from (sources) part of query
   * @param whereClause   is where (conditions) part of query
   * @param parentContext is parent context, that can define additional sources, available in query
   * @return created select query
   */
  public Select select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause, @Nullable FromContext parentContext) {
    return select(selectClause, fromClause, whereClause, parentContext, null);
  }

  /**
   * Create new top level select query based on supplied select, from and where clauses.
   *
   * @param selectClause is select (projection) part of query
   * @param fromClause   is from (sources) part of query
   * @param whereClause  is where (conditions) part of query
   * @return created select query
   */
  public Select select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause) {
    return select(selectClause, fromClause, whereClause, null, null);
  }

  /**
   * Create new top level select query based on supplied list of columns, from and where clauses.
   *
   * @param columns     is list of columns for select clause
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   * @param bindMap     is bind map, used to remap bind variables on creation
   * @return created select query
   */
  public Select select(Collection<? extends SelectColumn<?>> columns, FromClause fromClause,
      @Nullable Condition whereClause, @Nullable BindMap bindMap) {
    return select(selectColumns(columns), fromClause, whereClause, null, bindMap);
  }

  /**
   * Create new top level select query based on supplied list of columns, from and where clauses.
   *
   * @param columns     is list of columns for select clause
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   * @return created select query
   */
  public Select select(Collection<? extends SelectColumn<?>> columns, FromClause fromClause,
      @Nullable Condition whereClause) {
    return select(columns, fromClause, whereClause, null);
  }

  /**
   * Create select query based on supplied column, from and where clauses.
   *
   * @param column1       is the first and only column of query
   * @param fromClause    is from (sources) part of query
   * @param whereClause   is where (conditions) part of query
   * @param parentContext is parent context, that can define additional sources, available in query
   * @param bindMap       is bind map, used to remap bind variables on creation
   * @param <T1>          is type of the first column
   * @return new single column select query
   */
  public <T1> SelectT1<T1> select(SelectColumn<T1> column1, FromClause fromClause,
      @Nullable Condition whereClause, @Nullable FromContext parentContext,
      @Nullable BindMap bindMap) {
    return new SelectT1Impl<>(column1, fromClause, whereClause, parentContext, bindMap);
  }

  /**
   * Create select query based on supplied column, from and where clauses.
   *
   * @param column1     is the first and only column of query
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   * @param <T1>        is type of the first column
   * @return new single column select query
   */
  public <T1> SelectT1<T1> select(SelectColumn<T1> column1, FromClause fromClause,
      @Nullable Condition whereClause) {
    return select(column1, fromClause, whereClause, null, null);
  }

  /**
   * Consume select query based on supplied columns, from and where clauses.
   *
   * @param column1       is the first column of query
   * @param column2       is the second column of query
   * @param fromClause    is from (sources) part of query
   * @param whereClause   is where (conditions) part of query
   * @param parentContext is parent context, that can define additional sources, available in query
   * @param bindMap       is bind map, used to remap bind variables on creation
   * @param <T1>          is type of the first column
   * @param <T2>          is type of the second column
   * @return new two column select query
   */
  public <T1, T2> SelectT2<T1, T2> select(SelectColumn<T1> column1, SelectColumn<T2> column2,
      FromClause fromClause, @Nullable Condition whereClause, @Nullable FromContext parentContext,
      @Nullable BindMap bindMap) {
    return new SelectT2Impl<>(column1, column2, fromClause, whereClause, parentContext, bindMap);
  }

  /**
   * Consume select query based on supplied columns, from and where clauses.
   *
   * @param column1     is the first column of query
   * @param column2     is the second column of query
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   * @param <T1>        is type of the first column
   * @param <T2>        is type of the second column
   * @return new two column select query
   */
  public <T1, T2> SelectT2<T1, T2> select(SelectColumn<T1> column1, SelectColumn<T2> column2,
      FromClause fromClause, @Nullable Condition whereClause) {
    return select(column1, column2, fromClause, whereClause, null, null);
  }

  /**
   * Create select part of query, based on supplied columns and using remapping via supplied map.
   *
   * @param columns is collection of columns to be used for result projection
   * @param bindMap is bind map used for bind variable translation
   * @return select clause based on supplied columns
   */
  public SelectClause selectColumns(Collection<? extends SelectColumn<?>> columns,
      @Nullable BindMap bindMap) {
    return new SelectClauseColumns(columns, bindMap);
  }

  /**
   * Create select part of query, based on supplied columns.
   *
   * @param columns is collection of columns to be used for result projection
   * @return select clause based on supplied columns
   */
  public SelectClause selectColumns(Collection<? extends SelectColumn<?>> columns) {
    return selectColumns(columns, null);
  }

  /**
   * Create select column, based on supplied expression, using alias and with optional bind
   * remapping based on supplied map.
   *
   * @param expression is expression column is based on
   * @param alias      is alias used for column, null means use no or default alias
   * @param bindMap    is bind map to be used for column re-mapping
   * @param <T>        is type denoting return type of this column
   * @return new column with given properties
   */
  public <T> SelectColumn<T> selectColumn(Expression<T> expression, @Nullable SimpleName alias,
      @Nullable BindMap bindMap) {
    return new ColumnExpression<>(expression, alias, bindMap);
  }

  /**
   * Create select column, based on supplied expression, using (optionally) alias.
   *
   * @param expression is expression column is based on
   * @param alias      is alias used for column, null means use no or default alias
   * @param <T>        is type denoting return type of this column
   * @return new column with given properties
   */
  public <T> SelectColumn<T> selectColumn(Expression<T> expression, @Nullable SimpleName alias) {
    return selectColumn(expression, alias, null);
  }

  /**
   * Create from clause based on supplied from elements, potentially remapping binds via supplied
   * bind map.
   *
   * @param fromElements is collection of from elements this from clause is based on
   * @param bindMap      is bind map to be used for translation of binds, contained in expressions
   * @return from clause based on supplied from elements
   */
  public FromClause from(Collection<? extends FromElement> fromElements,
      @Nullable BindMap bindMap) {
    return new DefaultFromClause(fromElements, bindMap);
  }

  /**
   * Create from clause based on supplied from elements.
   *
   * @param fromElements is collection of from elements this from clause is based on
   * @return from clause based on supplied from elements
   */
  public FromClause from(Collection<? extends FromElement> fromElements) {
    return from(fromElements, null);
  }

  /**
   * Create new from element, based on supplied table and optionally using supplied alias.
   *
   * @param tableName is name, identifying data source
   * @param alias     is alias to be used by columns to refer to this source
   * @return new from element
   */
  public FromElement fromTable(NamePath tableName, @Nullable SimpleName alias) {
    return new FromTable(tableName, alias);
  }

  /**
   * Create new from element, based on select statement and optionally remap bind variables.
   *
   * @param select  is select statement from element is based on
   * @param alias   is alias used to refer to this from element
   * @param bindMap is used to map binds in supplied select
   * @return new from element, based on select statement
   */
  public FromElement fromSelect(SelectT<?> select, @Nullable SimpleName alias,
      @Nullable BindMap bindMap) {
    return new FromSelect(select, alias, bindMap);
  }

  /**
   * Create new from element, based on select statement.
   *
   * @param select is select statement from element is based on
   * @param alias  is alias used to refer to this from element
   * @return new from element, based on select statement
   */
  public FromElement fromSelect(SelectT<?> select, @Nullable SimpleName alias) {
    return new FromSelect(select, alias, null);
  }

  /**
   * Create new from element, based on dual pseudo-table (or however no table in from is
   * represented).
   *
   * @param alias is alias used to refer to this from element
   * @return new from element, based on dual pseudo-table
   */
  public FromElement fromDual(@Nullable SimpleName alias) {
    return new FromDual(alias);
  }

  /**
   * Build condition based on supplied operator and arguments.
   *
   * @param operator  is condition operator
   * @param arguments are supplied arguments
   * @return condition based on supplied operation and arguments
   */
  public Condition condition(ConditionalOperator operator,
      Collection<? extends Expression<?>> arguments) {
    return new ConditionOperation(operator, arguments);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    return o != null && getClass() == o.getClass();
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "ElementFactory{}";
  }
}