package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindWithPos;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue") // builder can be used outside this package
public interface SqlBuilder<B extends SqlBuilder<B>> extends QueryConsumer {

  /**
   * Get current position in builder (type of position).
   *
   * @return type of position
   */
  SqlBuilderPosition getPosition();

  /**
   * Put position on position stack.
   *
   * @param position is position to be placed on top of position stack
   * @return self to support fluent build
   */
  B pushPosition(SqlBuilderPosition position);

  /**
   * Remove position from top of the stack.
   *
   * @return self to support fluent build
   */
  B popPosition();

  /**
   * Append string to builder.
   *
   * @param text is text to be appended
   * @return self to support fluent build
   */
  B append(String text);

  /**
   * Append string to builder and end line.
   *
   * @param text is text to be appended
   * @return self to support fluent build
   */
  B appendLine(String text);

  /**
   * End line in builder.
   *
   * @return self to support fluent build
   */
  B appendLine();

  /**
   * Increases ident level and sets ident to spaces, regardless of original ident. It is generally
   * used for inside of code block
   *
   * @param increaseBy is number of additional characters
   * @return self to support fluent build
   */
  B increasedIdent(int increaseBy);

  /**
   * Returns to previous ident.
   *
   * @return self to support fluent build
   */
  B popIdent();

  /**
   * Get empty clone of this builder. Can be used to assemble part of statement (for example
   * evaluate function arguments)
   *
   * @return empty clone of this builder
   */
  B getClone();

  /**
   * Sql text built in sql builder.
   *
   * @return sql text built in this builder
   */
  String getSql();

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
