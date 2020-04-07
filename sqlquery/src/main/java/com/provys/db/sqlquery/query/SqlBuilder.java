package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindWithPos;
import java.util.Collection;
import java.util.Map;

public interface SqlBuilder<B extends SqlBuilder<B>> extends QueryConsumer {

  /**
   * Append string to builder.
   *
   * @param text is text to be appended
   */
  void append(String text);

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
