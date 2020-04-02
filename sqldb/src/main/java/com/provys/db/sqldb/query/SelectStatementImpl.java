package com.provys.db.sqldb.query;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindWithPos;
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
