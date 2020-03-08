package com.provys.provysdb.sqlbuilder;

import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Builder for column expression - can produce column or an expression. Builder generally consists
 * of expression builder and optional alias
 *
 * @param <T> is type of value returned by this expression / column
 */
public interface SelectExpressionBuilder<T> {

  /**
   * Java type, corresponding to this expression's type.
   *
   * @return Java type this column should be mapped to. Used to find proper adapter for value
   *     retrieval
   */
  Class<T> getType();

  /**
   * Alias this column is associated with.  Note that if it is simple column, its name is also used
   * as alias
   *
   * @return alias this column is associated with. Note that if it is simple column, its name is
   *     also used as alias
   */
  @Nullable Identifier getAlias();

  /**
   * Alias this column is associated with, Optional version.
   *
   * @return alias this column is associated with, empty optional if alias is absent
   */
  default Optional<Identifier> getOptAlias() {
    return Optional.ofNullable(getAlias());
  }

  /**
   * Create new column with alias replaced by specified one.
   *
   * @param newAlias is alias that should be used for new column
   * @return column with specified alias
   */
  SelectExpressionBuilder<T> as(Identifier newAlias);

  /**
   * Create new column with alias replaced by specified one.
   *
   * @param newAlias is alias that should be used for new column
   * @return column with specified alias
   */
  SelectExpressionBuilder<T> as(String newAlias);

  /**
   * Build column based on information about from clause. Might connect individual expressions to
   * from clause items.
   *
   * @param fromContext is content of from clause. Note that it is reason why items must be placed
   *                   in from clause before dependent items are built
   * @return built column
   */
  SelectColumn<T> buildColumn(FromContext fromContext);

  Expression<T> buildExpression()
}
