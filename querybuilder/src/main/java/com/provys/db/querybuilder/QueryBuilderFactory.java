package com.provys.db.querybuilder;

import com.google.errorprone.annotations.ImmutableTypeParameter;
import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.elements.SelectT1;
import com.provys.db.query.elements.SelectT2;
import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.io.Serializable;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Static utility class for creating instances of individual query elements.
 */
public final class QueryBuilderFactory {

  private static ElementBuilderFactory getElementBuilderFactory() {
    return ElementBuilderFactory.getInstance();
  }

  /**
   * Get singleton instance of this factory.
   *
   * @return singleton instance of this factory
   */
  public static ElementBuilderFactory getInstance() {
    return ElementBuilderFactory.getInstance();
  }

  /**
   * Get expression builder, wrapping given expression.
   *
   * @param expression is expression to be used as basis for expression builder
   * @param <T>        is type of expression
   * @return expression builder, based on given expression
   */
  public static <T> ExpressionBuilder<T> expression(Expression<T> expression) {
    return getElementBuilderFactory().expression(expression);
  }

  /**
   * Create expression builder, based on supplied bind variable.
   *
   * @param type         is type of expression
   * @param bindVariable is bind variable that will be used as basis of expression
   * @param <T>          is type of expression
   * @return expression builder based on bind variable expression
   */
  public static <T> ExpressionBuilder<T> bind(Class<T> type,
      BindVariable bindVariable) {
    return getElementBuilderFactory().bind(type, bindVariable);
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type of expression
   * @return expression builder based on bind variable expression
   */
  public static <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(
      Class<T> type, BindName name, @Nullable T value) {
    return getElementBuilderFactory().bind(type, name, value);
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type of expression
   * @return expression builder based on bind variable expression
   */
  public static <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(
      Class<T> type, BindName name) {
    return getElementBuilderFactory().bind(type, name);
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type of expression
   * @return expression builder based on bind variable expression
   */
  public static <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(
      Class<T> type, String name, @Nullable T value) {
    return getElementBuilderFactory().bind(type, name, value);
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type of expression
   * @return expression builder based on bind variable expression
   */
  public static <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(
      Class<T> type, String name) {
    return getElementBuilderFactory().bind(type, name);
  }

  /**
   * Create column expression builder, based on specified source (identified by alias), column name
   * and type. Expression is validated against supplied context.
   *
   * @param type   is type that given expression should yield
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param <T>    is type of expression
   * @return expression builder, representing single column / property from source
   */
  public static <T> ExpressionBuilder<T> column(Class<T> type,
      @Nullable NamePath table,
      SimpleName column) {
    return getElementBuilderFactory().column(type, table, column);
  }

  /**
   * Create expression builder, based on supplied column. Column must be of type based on expression
   * - if column is other type (e.g. table.*), action fails.
   *
   * @param column is column expression should be based on
   * @param <T>    is type of expression
   * @return expression builder, representing single column / property from source
   */
  public static <T> ExpressionBuilder<T> column(SelectColumn<T> column) {
    return getElementBuilderFactory().column(column);
  }

  /**
   * Create outer column (e.g. column with (+) ) expression builder, based on specified source
   * (identified by alias), column name and type. Expression is validated against supplied context.
   *
   * @param type   is type that given expression should yield
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param <T>    is type of expression
   * @return expression builder, representing single column / property from source
   */
  public static <T> ExpressionBuilder<T> columnOuter(Class<T> type,
      @Nullable NamePath table,
      SimpleName column) {
    return getElementBuilderFactory().columnOuter(type, table, column);
  }

  /**
   * Create outer column (e.g. column with (+) ) expression builder, based on specified select
   * column. Used when using column definitions read from database.
   *
   * @param column is select column that is to be used as source for outer column expression
   * @param <T>    is type of expression
   * @return expression builder, representing single column / property from source
   */
  public static <T> ExpressionBuilder<T> columnOuter(SelectColumn<T> column) {
    return getElementBuilderFactory().columnOuter(column);
  }

  /**
   * Create function builder that will evaluate to supplied type, based on supplied function and
   * using arguments. Type of function and arguments are validated against function definition.
   *
   * @param type      is type of object expression returns
   * @param function  is built-in function, used for evaluation
   * @param arguments are arguments to be passed to function
   * @param <T>       is type of expression
   * @return function, representing built-in function applied on supplied arguments
   */
  public static <T> ExpressionBuilder<T> function(Class<T> type,
      BuiltInFunction function,
      Collection<? extends Expression<?>> arguments) {
    return getElementBuilderFactory().function(type, function, arguments);
  }

  /**
   * Create function builder that will evaluate to supplied type, based on supplied function and
   * using arguments. Type of function and arguments are validated against function definition.
   *
   * @param type             is type of object expression returns
   * @param function         is built-in function, used for evaluation
   * @param argumentBuilders are argument builders to be passed to function
   * @param <T>              is type of expression
   * @return function, representing built-in function applied on supplied arguments
   */
  public static <T> ExpressionBuilder<T> function(Class<T> type,
      BuiltInFunction function,
      ExpressionBuilder<?>... argumentBuilders) {
    return getElementBuilderFactory().function(type, function, argumentBuilders);
  }

  /**
   * Create new literal expression builder of given type and value.
   *
   * @param type  is type of expression
   * @param value is expression value
   * @param <T>   is type of expression
   * @return new expression builder based on literal, representing supplied value
   */
  public static <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> literal(
      Class<T> type, @Nullable T value) {
    return getElementBuilderFactory().literal(type, value);
  }

  /**
   * Create new literal expression builder of given value. Not that it does not work correctly if
   * value is of generic type, as only class information is preserved, but generic parameters are
   * lost.
   *
   * @param value is expression value
   * @param <T>   is type of expression
   * @return expression builder based on literal, representing supplied value
   */
  public static <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> literal(
      @NonNull T value) {
    return getElementBuilderFactory().literal(value);
  }

  /**
   * Create new condition builder, decorating supplied condition.
   *
   * @param condition is condition to be decorated
   * @return new condition builder
   */
  public static StartConditionBuilder condition(Condition condition) {
    return getElementBuilderFactory().condition(condition);
  }

  /**
   * Create builder, allowing construction of AND connected chain of conditions. Note that it is
   * also possible to use {@code condition()} and follow with appending further conditions.
   *
   * @return AND condition chain builder
   */
  public static AndConditionBuilder andCondition() {
    return getElementBuilderFactory().andCondition();
  }

  /**
   * Create builder, allowing construction of OR connected chain of conditions. Note that it is also
   * possible to use {@code condition()} and follow with appending further conditions.
   *
   * @return OR condition chain builder
   */
  public static OrConditionBuilder orCondition() {
    return getElementBuilderFactory().orCondition();
  }

  /**
   * Create select builder, allowing to add columns, from conditions, where clause etc.
   *
   * @return new empty select builder
   */
  public static SelectBuilderT0 select() {
    return getElementBuilderFactory().select();
  }

  /**
   * Create select builder based on supplied select statement - general version.
   *
   * @param select is select query builder should be based on
   * @return new select builder
   */
  public static SelectBuilder select(SelectT<?> select) {
    return getElementBuilderFactory().select(select);
  }

  /**
   * Create select builder based on supplied select statement with single column.
   *
   * @param select is single column select query this builder should be based on
   * @param <T1>   is type of the first column
   * @return new select builder, initialized based on supplied statement
   */
  public static <T1> SelectBuilderT1<T1> select(SelectT1<T1> select) {
    return getElementBuilderFactory().select(select);
  }

  /**
   * Create select builder based on supplied select statement with two columns.
   *
   * @param select is two column select query this builder is based on
   * @param <T1>   is type of the first column
   * @param <T2>   is type of the second column
   * @return new select builder, initialized based on supplied statement
   */
  public static <T1, T2> SelectBuilderT2<T1, T2> select(SelectT2<T1, T2> select) {
    return getElementBuilderFactory().select(select);
  }

  private QueryBuilderFactory() {
  }
}
