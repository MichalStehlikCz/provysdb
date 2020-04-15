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

  BuiltIn COND_OR = ConditionalOperator.COND_OR;
  BuiltIn COND_AND = ConditionalOperator.COND_AND;
  BuiltIn COND_NOT = ConditionalOperator.COND_NOT;
}
