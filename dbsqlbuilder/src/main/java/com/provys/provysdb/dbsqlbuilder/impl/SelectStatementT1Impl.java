package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbRowMapper;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatementT1;
import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;

class SelectStatementT1Impl<T1> extends SelectStatementTImpl<SelectStatementT1Impl<T1>>
        implements SelectStatementT1<T1> {

    @Nonnull
    private final SqlColumnT<T1> column1;

    SelectStatementT1Impl(String sqlText, List<BindName> binds, DbSql sqlContext, SqlColumnT<T1> column1) {
        super(sqlText, binds, sqlContext);
        this.column1 = column1;
    }

    SelectStatementT1Impl(Select select, DbSql sqlContext, SqlColumnT<T1> column1) {
        super(select, sqlContext);
        this.column1 = column1;
    }

    SelectStatementT1Impl(Select select, DbConnection connection, SqlColumnT<T1> column1) {
        super(select, connection);
        this.column1 = column1;
    }

    @Nonnull
    @Override
    SelectStatementT1Impl<T1> self() {
        return this;
    }

    @Nonnull
    private DbRowMapper<T1> getRowMapper() {
        return ((resultSet, rowNumber)
                -> getStatement().getAdapterMap().getAdapter(column1.getType()).readValue(resultSet, 1));
    }

    @Nonnull
    @Override
    public T1 fetchOne() {
        return fetchOne(getRowMapper());
    }

    @Nonnull
    @Override
    public List<T1> fetch() {
        return fetch(getRowMapper());
    }

    @Nonnull
    @Override
    public Stream<T1> stream() {
        return stream(getRowMapper());
    }

    @Nonnull
    @Override
    public T1 fetchOneNoClose() {
        return fetchOneNoClose(getRowMapper());
    }

    @Nonnull
    @Override
    public List<T1> fetchNoClose() {
        return fetchNoClose(getRowMapper());
    }

    @Nonnull
    @Override
    public Stream<T1> streamNoClose() {
        return streamNoClose(getRowMapper());
    }
}