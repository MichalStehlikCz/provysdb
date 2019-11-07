package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.List;

class SelectStatementImpl extends SelectStatementTImpl<SelectStatementImpl> implements SelectStatement {

    SelectStatementImpl(String sqlText, List<BindName> binds, Sql sqlContext) {
        super(sqlText, binds, sqlContext);
    }

    SelectStatementImpl(Select select, Sql sqlContext) {
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
