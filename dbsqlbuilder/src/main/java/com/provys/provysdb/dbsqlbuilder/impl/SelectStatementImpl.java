package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatement;
import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.List;

class SelectStatementImpl extends SelectStatementTImpl<SelectStatementImpl> implements SelectStatement {

    SelectStatementImpl(String sqlText, List<BindName> binds, DbSql sqlContext) {
        super(sqlText, binds, sqlContext);
    }

    SelectStatementImpl(Select select, DbSql sqlContext) {
        super(select, sqlContext);
    }

    SelectStatementImpl(Select select, DbConnection connection) {
        super(select, connection);
    }

    @Nonnull
    @Override
    SelectStatementImpl self() {
        return this;
    }
}
