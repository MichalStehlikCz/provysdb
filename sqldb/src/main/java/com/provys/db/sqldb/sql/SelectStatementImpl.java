package com.provys.db.sqldb.sql;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.query.BindName;
import com.provys.db.query.BindWithPos;
import com.provys.db.query.SelectStatement;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

class SelectStatementImpl extends SelectStatementTImpl<SelectStatementImpl> implements
    SelectStatement {

  SelectStatementImpl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, ? extends @Nullable Object> bindValues,
      DbConnection connection) {
    super(sqlText, binds, bindValues, connection);
  }

  SelectStatementImpl(String sqlText, DbConnection connection) {
    super(sqlText, connection);
  }

  SelectStatementImpl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, ? extends @Nullable Object> bindValues, DbContext dbContext) {
    super(sqlText, binds, bindValues, dbContext);
  }

  SelectStatementImpl(String sqlText, DbContext dbContext) {
    super(sqlText, dbContext);
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
