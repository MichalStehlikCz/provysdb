package com.provys.provysdb.sqlparser;

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
