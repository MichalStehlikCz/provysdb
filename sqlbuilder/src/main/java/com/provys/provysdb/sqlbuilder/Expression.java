package com.provys.provysdb.sqlbuilder;

/**
 * Expression - literal, column, bind, function call, condition or any combination of these,
 * connected by operands
 *
 * @param <T> is type of value expression evaluates to
 */
public interface Expression<T> {

  /**
   * Java type, corresponding to this expression's type.
   *
   * @return Java type this column should be mapped to. Used to find proper adapter for value
   *     retrieval
   */
  Class<T> getType();

  /**
   * Appends expression sql to code builder, used to construct statement. Unlike addColumnSql, does
   * not append column alias. Also adds associated binds. Used in any other place than when used as
   * column.
   * When used as column, alias clause is appended to expression.
   *
   * @param builder is CodeBuilder, used to construct sql text
   */
  void appendExpression(CodeBuilder builder);
}
