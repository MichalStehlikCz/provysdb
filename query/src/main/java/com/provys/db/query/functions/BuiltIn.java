package com.provys.db.query.functions;

import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;

/**
 * Common ancestor for BuiltInFunction and ConditionOperator.
 * While we strictly distinguish expressions and conditions - mainly because condition is not usable
 * as expression in Oracle SQL and it is out main target - practically built-in functions and
 * operators operating on expressions and conditional operators are the same thing and we want to be
 * able to handle them in unified way.
 */
// we want to keep constants here, as these constants are basically used to circumvent fact that
// there is no enum hierarchy
@SuppressWarnings("java:S1214")
@Immutable
public interface BuiltIn extends BuiltInInt, Serializable {

  BuiltIn STRING_CHR = BuiltInFunction.STRING_CHR;
  BuiltIn STRING_CONCAT = BuiltInFunction.STRING_CONCAT;
  BuiltIn DATE_SYSDATE = BuiltInFunction.DATE_SYSDATE;
  BuiltIn ANY_NVL = BuiltInFunction.ANY_NVL;

  BuiltIn COND_OR = ConditionalOperator.COND_OR;
  BuiltIn COND_AND = ConditionalOperator.COND_AND;
  BuiltIn COND_NOT = ConditionalOperator.COND_NOT;

  BuiltIn COND_EQ_NONNULL = ConditionalOperator.COND_EQ_NONNULL;
  BuiltIn COND_EQ_NULLABLE = ConditionalOperator.COND_EQ_NULLABLE;
  BuiltIn COND_NOT_EQ_NONNULL = ConditionalOperator.COND_NOT_EQ_NONNULL;
  BuiltIn COND_NOT_EQ_NULLABLE = ConditionalOperator.COND_NOT_EQ_NULLABLE;
  BuiltIn COND_LT_NONNULL = ConditionalOperator.COND_LT_NONNULL;
  BuiltIn COND_LT_NULL_UNLIMITED = ConditionalOperator.COND_LT_NULL_UNLIMITED;
  BuiltIn COND_LT_OR_EQ_NONNULL = ConditionalOperator.COND_LT_OR_EQ_NONNULL;
  BuiltIn COND_LT_OR_EQ_NULL_UNLIMITED = ConditionalOperator.COND_LT_OR_EQ_NULL_UNLIMITED;
  BuiltIn COND_GT_NONNULL = ConditionalOperator.COND_GT_NONNULL;
  BuiltIn COND_GT_NULL_UNLIMITED = ConditionalOperator.COND_GT_NULL_UNLIMITED;
  BuiltIn COND_GT_OR_EQ_NONNULL = ConditionalOperator.COND_GT_OR_EQ_NONNULL;
  BuiltIn COND_GT_OR_EQ_NULL_UNLIMITED = ConditionalOperator.COND_GT_OR_EQ_NULL_UNLIMITED;
}
