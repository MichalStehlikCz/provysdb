package com.provys.db.sqlparser;

import java.util.List;
import java.util.Scanner;

/**
 * Interface represents tokenizer, capable of parsing SQL source file into sequence of tokens. It
 * can be either used to retrieve stream of tokens itself or get list of binds aggregated from
 * tokens
 */
public interface SqlTokenizer {

  /**
   * Transform source code, represented by String, into list of tokens.
   *
   * @param source is string containing source code
   * @return list of tokens representing supplied code
   */
  List<SqlParsedToken> tokenize(String source);

  /**
   * Transform source code, represented by Scanner, into list of tokens.
   *
   * @param scanner is Scanner containing source code
   * @return list of tokens representing supplied code
   */
  List<SqlParsedToken> tokenize(Scanner scanner);
}
