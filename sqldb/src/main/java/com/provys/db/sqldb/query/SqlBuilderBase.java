package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Select;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class SqlBuilderBase<F extends StatementFactory, Q extends Select,
    S extends SelectStatement> implements SqlBuilder<F, Q, S> {

  private final F statementFactory;
  private final Q query;

  SqlBuilderBase(F statementFactory, Q query) {
    this.statementFactory = statementFactory;
    this.query = query;
  }

  /**
   * Value of field factory.
   *
   * @return value of field factory
   */
  @Override
  public F getStatementFactory() {
    return statementFactory;
  }

  /**
   * Value of field query.
   *
   * @return value of field query
   */
  @Override
  public Q getQuery() {
    return query;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlBuilderBase<?, ?, ?> that = (SqlBuilderBase<?, ?, ?>) o;
    return statementFactory.equals(that.statementFactory)
        && query.equals(that.query);
  }

  @Override
  public int hashCode() {
    int result = statementFactory.hashCode();
    result = 31 * result + query.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlBuilderBase{"
        + "query=" + query
        + ", statementFactory=" + statementFactory
        + '}';
  }
}
