package com.provys.db.query.elements;

import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Interface presents consumer that can be used to process expressions.
 */
public interface ExpressionConsumer extends ConditionConsumer {

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
   * Consume outer column expression (e.g. column enriched with (+) ), based on specified source
   * (identified by alias), column name and type.
   *
   * @param type   is type that given expression should yield
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   */
  void columnOuter(Class<?> type, @Nullable NamePath table, SimpleName column);

  /**
   * Consume function that will evaluate to supplied type, based on supplied function and using
   * arguments. Type of function and arguments are validated against function definition.
   *
   * @param type      is type of object expression returns
   * @param function  is built-in function, used for evaluation
   * @param arguments are arguments to be passed to function
   */
  void function(Class<?> type, BuiltInFunction function,
      Collection<? extends Expression<?>> arguments);

  /**
   * Consume literal expression of given type and value.
   *
   * @param type  is type of expression
   * @param value is expression value
   * @param <T>   is type of expression
   */
  <T> void literal(Class<T> type, @Nullable T value);
}
