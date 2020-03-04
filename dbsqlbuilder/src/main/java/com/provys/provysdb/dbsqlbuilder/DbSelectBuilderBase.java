package com.provys.provysdb.dbsqlbuilder;

interface DbSelectBuilderBase {

  /**
   * Build select statement from builder.
   *
   * @return select statement based on builder's content
   */
  SelectStatement prepare();
}
