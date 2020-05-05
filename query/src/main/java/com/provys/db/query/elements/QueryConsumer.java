package com.provys.db.query.elements;

import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface QueryConsumer extends ConditionConsumer, SelectConsumer, SelectClauseConsumer,
    SelectColumnConsumer {

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
   * @param alias  is alias used to refer to this from element
   */
  void fromSelect(SelectT<?> select, @Nullable SimpleName alias);

  /**
   * Consume from element, based on dual pseudo-table (or however no table in from is represented).
   *
   * @param alias is alias used to refer to this from element
   */
  void fromDual(@Nullable SimpleName alias);

  /**
   * Consume keyword. Used by tokens produced by parsing sql using tokenizer.
   *
   * @param keyword is keyword to be consumed
   */
  void keyword(String keyword);

  /**
   * Consume name (potentially segmented). Used by tokens, produced by parsing sql using tokenizer.
   *
   * @param namePath is name to be processed
   */
  void name(NamePath namePath);

  /**
   * Consume symbol. Used by tokens, produced by parsing sql using tokenizer.
   *
   * @param symbol is symbol to be processed
   */
  void symbol(String symbol);

  /**
   * Consume simple (rest of line) comment - {@code --} in SQL.
   *
   * @param comment is comment text
   */
  void simpleComment(String comment);

  /**
   * Consume multi-line comment - {code /*} in SQL.
   *
   * @param comment is comment text
   */
  void longComment(String comment);
}
