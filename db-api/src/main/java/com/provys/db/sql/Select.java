package com.provys.db.sql;

import com.provys.db.dbcontext.DbConnection;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Immutable object representing sql statement, described in structured way. This statement can be
 * used to retrieve {@code SelectStatement} object for execution. If needed, it can also be cloned
 * to different dialect.
 */
@SuppressWarnings("CyclicClassDependency")// cyclic dependency on factory Context to support cloning
public interface Select extends Element {

  /**
   * Parse this select and return resulting statement, that can be used for data retrieval. Get
   * connection from underlying {@link Context}
   *
   * @return new SelectStatement; note that statement holds connection and prepared statement
   */
  SelectStatement parse();

  /**
   * Parse this select and return resulting statement, that can be used for data retrieval.
   *
   * @param connection is database connection to be used with new statement
   * @return new SelectStatement; note that statement holds connection and prepared statement
   */
  SelectStatement parse(DbConnection connection);

  /**
   * Transfer select to specified context.
   *
   * @param targetContext is target context
   * @param <S> is subtype of {@code Select} cloning will result in based on target context
   * @return select cloned to specified context
   */
  default <S extends Select> S transfer(Context<S, ?, ?, ?, ?, ?, ?> targetContext) {
    return transfer(targetContext, null);
  }

  /**
   * Transfer select to specified context.
   *
   * @param targetContext is target context
   * @param bindMap is new mapping of bind variables; when not specified, bind variables are left as
   *               they are
   * @param <S> is subtype of {@code Select} cloning will result in based on target context
   * @return select cloned to specified context
   */
  <S extends Select> S transfer(Context<S, ?, ?, ?, ?, ?, ?> targetContext,
      @Nullable BindMap bindMap);
}
