package com.provys.db.querybuilder;

/**
 * All builders allow extraction of bind variables... as defined in this ancestor.
 */
interface BuilderBase {

  /**
   * Append binds present in this builder to combiner.
   *
   * @param combiner is bind variable combiner, used to collect the binds
   */
  void appendBinds(BindVariableCombiner combiner);
}
