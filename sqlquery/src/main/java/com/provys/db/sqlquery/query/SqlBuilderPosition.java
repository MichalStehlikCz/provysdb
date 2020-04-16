package com.provys.db.sqlquery.query;

/**
 * Defines position in builder, can be used as context by individual appenders to determine need to
 * enclose expression in brackets etc.
 */
public enum SqlBuilderPosition {
  EXPR_BRACKET, // used to denote expression priority as never need brackets as it has its own
  EXPR_UNARY, // unary + / - operators
  EXPR_MULTIPLY, // * and /
  EXPR_ADD, // +, - and ||
  COMPARE, // =, !=, <, >, <=, >=
  OTHER_COMPARE, // LIKE, EXISTS, IN, IS NULL
  COND_NOT, // NOT - logical negation
  COND_AND, // AND - logical and
  COND_OR, // logical OR
  IN_BRACKET, // used when current position is surrounded by brackets
  WHERE, // where clause start
  GENERAL // used as top level context
}
