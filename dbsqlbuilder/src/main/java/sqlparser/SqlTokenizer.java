package sqlparser;

import com.provys.provysdb.sql.CodeBuilder;
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

  /**
   * Retrieve bind variables from supplied source and normalize it.
   *
   * @param source is source code supplied as String
   * @return CodeBuilder containing normalized text of SQL statement and binds parsed from SQL
   *     statement
   */
  CodeBuilder normalize(String source);

  /**
   * Retrieve bind variables from supplied source and normalize it.
   *
   * @param scanner is source code supplied as Scanner
   * @return CodeBuilder containing normalized text of SQL statement and binds parsed from SQL
   *     statement
   */
  CodeBuilder normalize(Scanner scanner);
}
