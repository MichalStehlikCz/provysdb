package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatement;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.Select;
import java.util.Collection;

class SelectStatementImpl extends SelectStatementTImpl<SelectStatementImpl> implements
    SelectStatement {

  SelectStatementImpl(String sqlText, Collection<? extends BindName> binds, DbSql sqlContext) {
    super(sqlText, binds, sqlContext);
  }

  SelectStatementImpl(Select select, DbSql sqlContext) {
    super(select, sqlContext);
  }

  SelectStatementImpl(Select select, DbConnection connection) {
    super(select, connection);
  }

  @Override
  SelectStatementImpl self() {
    return this;
  }

  @Override
  public String toString() {
    return "SelectStatementImpl{" + super.toString() + '}';
  }
}
