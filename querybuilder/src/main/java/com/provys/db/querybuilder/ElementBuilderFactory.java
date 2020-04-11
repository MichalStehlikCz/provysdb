package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.Function;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.ArrayList;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Factory capable of producing all element builders.
 */
public final class ElementBuilderFactory {

  private static final ElementBuilderFactory INSTANCE = new ElementBuilderFactory();

  /**
   * Get singleton instance of this factory.
   *
   * @return singleton instance of this factory
   */
  public static ElementBuilderFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Query factory, used to produce query elements.
   */
  private final ElementFactory elementFactory = ElementFactory.getInstance();

  /**
   * No point producing multiple instances - at least not until there are multiple implementations
   * of ElementFactory and we want to inject them on creation...
   */
  private ElementBuilderFactory() {
  }

  /**
   * Get expression builder, wrapping given expression.
   *
   * @param expression is expression to be used as basis for expression builder
   * @param <T>        is type of expression
   * @return expression builder, based on given expression
   */
  public <T> ExpressionBuilder<T> expression(Expression<T> expression) {
    return new DecoratingExpressionBuilder<>(expression, elementFactory);
  }

  /**
   * Create expression builder, based on supplied bind variable.
   *
   * @param type         is type of expression
   * @param bindVariable is bind variable that will be used as basis of expression
   * @param <T>          is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <T> ExpressionBuilder<T> bind(Class<T> type, BindVariable bindVariable) {
    return expression(elementFactory.bind(type, bindVariable));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <T> ExpressionBuilder<T> bind(Class<T> type, BindName name, @Nullable T value) {
    return expression(elementFactory.bind(type, name, value));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <T> ExpressionBuilder<T> bind(Class<T> type, BindName name) {
    return expression(elementFactory.bind(type, name));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <T> ExpressionBuilder<T> bind(Class<T> type, String name, @Nullable T value) {
    return expression(elementFactory.bind(type, name, value));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <T> ExpressionBuilder<T> bind(Class<T> type, String name) {
    return expression(elementFactory.bind(type, name));
  }

  /**
   * Create column expression builder, based on specified source (identified by alias), column name
   * and type. Expression is validated against supplied context.
   *
   * @param type   is type that given expression should yield
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param <T>    is type parameter denoting type of expression
   * @return expression builder, representing single column / property from source
   */
  public <T> ExpressionBuilder<T> column(Class<T> type, @Nullable NamePath table,
      SimpleName column) {
    return expression(elementFactory.column(type, table, column));
  }

  /**
   * Create function builder that will evaluate to supplied type, based on supplied function and
   * using arguments. Type of function and arguments are validated against function definition.
   *
   * @param type      is type of object expression returns
   * @param function  is built-in function, used for evaluation
   * @param arguments are arguments to be passed to function
   * @param <T>       is type parameter denoting type of expression
   * @return function, representing built-in function applied on supplied arguments
   */
  public <T> ExpressionBuilder<T> function(Class<T> type, Function function,
      Collection<? extends Expression<?>> arguments) {
    return expression(elementFactory.function(type, function, arguments));
  }

  /**
   * Create function builder that will evaluate to supplied type, based on supplied function and
   * using arguments. Type of function and arguments are validated against function definition.
   *
   * @param type             is type of object expression returns
   * @param function         is built-in function, used for evaluation
   * @param argumentBuilders are argument builders to be passed to function
   * @param <T>              is type parameter denoting type of expression
   * @return function, representing built-in function applied on supplied arguments
   */
  public <T> ExpressionBuilder<T> function(Class<T> type, Function function,
      ExpressionBuilder<?>... argumentBuilders) {
    var arguments = new ArrayList<Expression<?>>(argumentBuilders.length);
    for (var argumentBuilder : argumentBuilders) {
      arguments.add(argumentBuilder.build());
    }
    return function(type, function, arguments);
  }

  /**
   * Create new literal expression builder of given type and value.
   *
   * @param type  is type of expression
   * @param value is expression value
   * @param <T>   is parameter denoting type of expression
   * @return new expression builder based on literal, representing supplied value
   */
  public <T> ExpressionBuilder<T> literal(Class<T> type, @Nullable T value) {
    return expression(elementFactory.literal(type, value));
  }

  /**
   * Create new literal expression builder of given value. Not that it does not work correctly if
   * value is of generic type, as only class information is preserved, but generic parameters are
   * lost.
   *
   * @param value is expression value
   * @param <T>   is parameter denoting type of expression
   * @return expression builder based on literal, representing supplied value
   */
  public <T> ExpressionBuilder<T> literal(@NonNull T value) {
    return expression(elementFactory.literal(value));
  }

  /**
   * Create new condition builder, decorating supplied condition.
   *
   * @param condition is condition to be decorated
   * @return new condition builder
   */
  public ConditionBuilder condition(Condition condition) {
    return new DecoratingConditionBuilder(condition, elementFactory);
  }

  @Override
  public String toString() {
    return "ElementBuilderFactory{"
        + "elementFactory=" + elementFactory
        + '}';
  }
}
