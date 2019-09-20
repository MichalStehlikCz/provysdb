package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.sqlbuilder.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;

public class SelectStatementImpl implements SelectStatement {

    private static final Logger LOG = LogManager.getLogger(SelectStatementImpl.class);

    private final Select select;
    private final DbConnection connection;
    private final boolean closeConnection;
    private final DbPreparedStatement statement;

    SelectStatementImpl(Select select, Sql sqlContext) {
        this.select = select;
        this.connection = sqlContext.getConnection();
        this.closeConnection = true;
        try {
            this.statement = this.connection.prepareStatement(select.getSqlText());
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + select, e);
        }
    }

    SelectStatementImpl(Select select, DbConnection connection) {
        this.select = select;
        this.connection = connection;
        this.closeConnection = false;
        try {
            this.statement = this.connection.prepareStatement(select.getSqlText());
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + select, e);
        }
    }

    @Nonnull
    @Override
    public SelectStatement bindValue(SqlName bind, @Nullable Object value) {
        return this;
    }

    @Nonnull
    @Override
    public SelectStatement bindValue(String bind, @Nullable Object value) {
        return this;
    }

    @Nonnull
    @Override
    public <T> SelectStatement bindValue(BindVariableT<T> bind, @Nullable T value) {
        return this;
    }

    @Override
    public DbResultSet execute() {
        return statement.executeQuery();
    }

    @Override
    public void close() {

    }
}
