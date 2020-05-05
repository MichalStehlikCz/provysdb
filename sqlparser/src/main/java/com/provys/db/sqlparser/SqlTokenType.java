package com.provys.db.sqlparser;

/**
 * Types of tokens that can be parsed out of SQL source.
 */
public enum SqlTokenType {
  NAME,
  COMMENT,
  KEYWORD,
  SYMBOL,
  LITERAL,
  BIND
}
