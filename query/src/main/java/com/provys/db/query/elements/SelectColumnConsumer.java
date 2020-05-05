package com.provys.db.query.elements;

import com.provys.db.query.names.SimpleName;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Can be used to get insight about SelectColumn.
 */
public interface SelectColumnConsumer {

  /**
   * Consume select column, based on supplied expression, using alias.
   *
   * @param expression is expression column is based on
   * @param alias      is alias used for column, null means use no or default alias
   */
  void selectColumn(Expression<?> expression, @Nullable SimpleName alias);
}
