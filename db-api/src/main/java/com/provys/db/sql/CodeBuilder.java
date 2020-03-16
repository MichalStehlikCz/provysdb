package com.provys.db.sql;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * CodeBuilder is object that allows to build SQL statement (string) from lines. It is used
 * internally by Select to build SQL text from structured query.
 *
 * @author stehlik
 */
@SuppressWarnings("UnusedReturnValue")
public interface CodeBuilder {

  /**
   * Appends piece of text to already existing code.
   *
   * @param text contains text to be added
   * @return self to support fluent build
   */
  CodeBuilder append(String text);

  /**
   * Appends piece of text to already existing code; char variant.
   *
   * @param character contains text to be added
   * @return self to support fluent build
   */
  CodeBuilder append(char character);

  /**
   * Appends piece of text to already existing code; int variant.
   *
   * @param number contains text to be added
   * @return self to support fluent build
   */
  CodeBuilder append(int number);

  /**
   * Appends piece of text to already existing code; BigInteger variant.
   *
   * @param number contains text to be added
   * @return self to support fluent build
   */
  CodeBuilder append(BigInteger number);

  /**
   * Use function that appends some text to this builder. Note that it should not be used for
   * function implementation, as it would lead to circular dependence. Construct is to allow use of
   * element's append functions in fluent build of statement via CodeBuilder
   *
   * @param appendFunction is function that accepts builder and appends appropriate text to it
   * @return self to support fluent build
   */
  CodeBuilder apply(Consumer<? super CodeBuilder> appendFunction);

  /**
   * Use function that appends some text to this builder's StringBuilder.
   *
   * @param appendFunction is function that accepts builder and appends appropriate text to it
   * @return self to support fluent build
   */
  CodeBuilder applyString(Consumer<? super StringBuilder> appendFunction);

  /**
   * Appends piece of text that might span multiple lines to already existing code. Inserts proper
   * ident to newlines in supplied text; ident uses spaces, not currently set prefix and is
   * increased by specified amount against current level
   *
   * @param text            contains text to be added
   * @param additionalIdent is number of characters that newlines should be idented above current
   *                        level
   * @return self to support fluent build
   */
  CodeBuilder appendWrapped(String text, int additionalIdent);

  /**
   * Appends piece of text that might span multiple lines to already existing code. Inserts proper
   * ident to newlines in supplied text; ident uses spaces, not currently set prefix, but keeps
   * level with current length.
   *
   * @param text contains text to be added
   * @return self to support fluent build
   */
  CodeBuilder appendWrapped(String text);

  /**
   * Finishes line in already existing code.
   *
   * @return self to support fluent build
   */
  CodeBuilder appendLine();

  /**
   * Appends line of text to already existing code.
   *
   * @param line contains text to be added (without newline char)
   * @return self to support fluent build
   */
  CodeBuilder appendLine(String line);

  /**
   * Produce new ident builder.
   *
   * @return ident builder, that allows to build more complex ident rules
   */
  CodeIdentBuilder identBuilder();

  /**
   * Sets ident to specified ident.
   *
   * @param ident is ident to be used
   * @return self to support fluent build
   */
  CodeBuilder setIdent(CodeIdent ident);

  /**
   * Sets ident to given string.
   *
   * @param ident is new ident string
   * @return self to support fluent build
   */
  CodeBuilder setIdent(String ident);

  /**
   * Sets ident to given string, left padded to specified length.
   *
   * @param ident is new ident string
   * @param chars is length to which ident should be left padded with spaces
   * @return self to support fluent build
   */
  CodeBuilder setIdent(String ident, int chars);

  /**
   * Sets ident and first ident to given strings.
   *
   * @param firstIdent is new ident for the first line
   * @param ident      is new ident valid from the second line on
   * @return self to support fluent build
   */
  CodeBuilder setIdent(String firstIdent, String ident);

  /**
   * Sets ident and first ident to given strings, left padded to required length.
   *
   * @param firstIdent is new ident for the first line
   * @param ident      is new ident valid from the second line on
   * @param chars      is required ident length - supplied strings will be left padded with spaces
   *                   to reach this length
   * @return self to support fluent build
   */
  CodeBuilder setIdent(String firstIdent, String ident, int chars);

  /**
   * Increases ident level and sets ident to spaces, regardless of original ident. It is generally
   * used for inside of code block
   *
   * @param increaseBy is number of additional characters
   * @return self to support fluent build
   */
  CodeBuilder increasedIdent(int increaseBy);

  /**
   * Increase ident length by defined number of characters and replace ident with specified text,
   * left padded with spaces.
   *
   * @param ident      new ident text, left padded ith spaces to required length
   * @param increaseBy is number of characters ident should be increased by
   * @return self to support fluent build
   */
  CodeBuilder increasedIdent(String ident, int increaseBy);

  /**
   * Increase ident length by defined number of characters and replace ident with specified text,
   * left padded with spaces. First line will get different ident
   *
   * @param firstIdent is ident text that will be used first line, after left padding it with spaces
   *                   to required length
   * @param ident      is ident text that will be used from second line on
   * @param increaseBy is number of characters ident should be increased by
   * @return self to support fluent build
   */
  CodeBuilder increasedIdent(String firstIdent, String ident, int increaseBy);

  /**
   * Returns to previous ident.
   *
   * @return self to support fluent build
   */
  CodeBuilder popIdent();

  /**
   * Add single bind variable to end of the list.
   *
   * @param bind is bind variable to be added
   * @return self to support fluent build
   */
  CodeBuilder addBind(BindVariable bind);

  /**
   * Add all binds from supplied collection to the end of the list.
   *
   * @param binds is collection of binds to be added
   * @return self to support fluent build
   */
  CodeBuilder addBinds(Collection<? extends BindVariable> binds);

  /**
   * Add binds from list of binds with position to end of list.
   *
   * @param binds is collection of binds, each of them with positions. It is expected that positions
   *              are one-based list with no conflicts
   * @return self to support fluent build
   */
  CodeBuilder addBindsWithPos(Collection<BindWithPos> binds);

  /**
   * Build Sql string from code builder.
   *
   * @return String that has been built
   */
  String build();

  /**
   * Retrieve list of bind variables with their positions. Positions are one indexed - we use them
   * in Jdbc prepared statement that is one indexed
   *
   * @return list of bind variables with their positions
   */
  Collection<BindWithPos> getBindsWithPos();

  /**
   * Retrieve list of values, assigned to bind variables during build.
   *
   * @return list of values, assigned to bind variables during build
   */
  Map<BindName, Object> getBindValues();
}
