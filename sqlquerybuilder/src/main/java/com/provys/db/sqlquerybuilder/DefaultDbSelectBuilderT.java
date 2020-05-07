package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import com.provys.db.querybuilder.SelectBuilderT;
import com.provys.db.sqlquery.query.StatementFactory;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class DefaultDbSelectBuilderT<S extends SelectBuilderT<S>,
    T extends DefaultDbSelectBuilderT<S, T>> {

  private final S selectBuilder;
  private final StatementFactory statementFactory;

  DefaultDbSelectBuilderT(S selectBuilder, StatementFactory statementFactory) {
    this.selectBuilder = selectBuilder;
    this.statementFactory = statementFactory;
  }

  /**
   * Value of field selectBuilder.
   *
   * @return value of field selectBuilder
   */
  protected S getSelectBuilder() {
    return selectBuilder;
  }

  /**
   * Statement factory.
   *
   * @return statement factory this builder uses to produce select statement
   */
  protected StatementFactory getStatementFactory() {
    return statementFactory;
  }

  /**
   * Function returns self pointer (this) with proper type.
   *
   * @return self pointer
   */
  protected abstract T self();

  /**
   * Clone database builder with new select builder.
   *
   * @param newSelectBuilder is select builder to be used in new DbSelectBuilder
   * @return new DbSelectBuilder with select builder replaced by indicated one
   */
  protected abstract T clone(S newSelectBuilder);

  /**
   * SelectBuilder interface makes no assumptions about various methods modifying given builder or
   * creating new builder. As we should not make assumptions about implementation, we use this
   * method to either create new instance if new SelectBuilder has been created or self if existing
   * SelectBuilder has been modified.
   *
   * @param newSelectBuilder is SelectBuilder to be used in produced DbSelectBuilder
   * @return self (if newSelectBuilder is current one) or new database select builder based on
   *     specified SelectBuilder
   */
  protected T withSelectBuilder(S newSelectBuilder) {
    // we do not need deep comparison, this is only to prevent unnecessary cloning
    //noinspection ObjectEquality
    if (newSelectBuilder == selectBuilder) {
      return self();
    }
    return clone(newSelectBuilder);
  }

  public T from(FromElement fromElement) {
    return withSelectBuilder(selectBuilder.from(fromElement));
  }

  public T fromTable(NamePath tableName, @Nullable SimpleName alias) {
    return withSelectBuilder(selectBuilder.fromTable(tableName, alias));
  }

  public T fromSelect(SelectT<?> select, @Nullable SimpleName alias) {
    return withSelectBuilder(selectBuilder.fromSelect(select, alias));
  }

  public T fromDual(@Nullable SimpleName alias) {
    return withSelectBuilder(selectBuilder.fromDual(alias));
  }

  public T where(Condition condition) {
    return withSelectBuilder(selectBuilder.where(condition));
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultDbSelectBuilderT<?, ?> that = (DefaultDbSelectBuilderT<?, ?>) o;
    return selectBuilder.equals(that.selectBuilder)
        && statementFactory.equals(that.statementFactory);
  }

  @Override
  public int hashCode() {
    int result = selectBuilder.hashCode();
    result = 31 * result + statementFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultDbSelectBuilderT{"
        + "selectBuilder=" + selectBuilder
        + ", statementFactory=" + statementFactory
        + '}';
  }
}
