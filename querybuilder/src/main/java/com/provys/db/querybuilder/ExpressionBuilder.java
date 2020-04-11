package com.provys.db.querybuilder;

import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.names.SimpleName;

/**
 * Builder used to produce expressions. Expression builder itself is immutable - this way, it is
 * possible to safely expose builder for example in entity meta-interface
 *
 * @param <T> is Java type corresponding to values of given expression
 */
public interface ExpressionBuilder<T> {

  /**
   * Get type of expression being built.
   *
   * @return type of expression being built
   */
  Class<T> getType();

  /**
   * Build expression prepared by this builder.
   *
   * @return expression prepared by this builder
   */
  Expression<T> build();

  /**
   * Build column from this expression, using supplied alias.
   *
   * @param alias  is alias this column will get
   * @return select column based on given expression
   */
  SelectColumn<T> as(SimpleName alias);

  /**
   * Build column from this expression, using supplied alias.
   *
   * @param alias  is alias this column will get
   * @return select column based on given expression
   */
  default SelectColumn<T> as(String alias) {
    return as(SimpleName.valueOf(alias));
  }

  /**
   * Build condition by comparing this expression to other.
   *
   * @param compareWith is expression to be compared with
   * @return condition produced by comparison of this builder with other
   */
  ConditionBuilder eq(Expression<T> compareWith);

  /**
   * Build condition by comparing this expression to other.
   *
   * @param compareWith is expression to be compared with
   * @return condition produced by comparison of this builder with other
   */
  default ConditionBuilder eq(ExpressionBuilder<T> compareWith) {
    return eq(compareWith.build());
  }
}
