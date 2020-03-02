package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.NoDbSql;
import com.provys.provysdb.sqlbuilder.SelectBuilderT0;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;

/**
 * Sql builder without database. Allows to build select statement, but this statement cannot be
 * executed directly - it has to be either used with supplied connection, used as sub-select or sent
 * to different service for execution
 */
public class NoDbSqlImpl extends SqlImpl implements NoDbSql {

  /**
   * Create sql builder with supplied tokenizer.
   *
   * @param tokenizer is used to parse parts of statement supplied as string
   */
  public NoDbSqlImpl(SqlTokenizer tokenizer) {
    super(tokenizer);
  }

  public NoDbSqlImpl() {
    this(new DefaultSqlTokenizer());
  }

  @Override
  public SelectBuilderT0 select() {
    return new SelectBuilderT0Impl<>(this);
  }

  @Override
  public String toString() {
    return "NoDbSqlImpl{" + super.toString() + '}';
  }
}
