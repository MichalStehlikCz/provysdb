package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT0;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderT0Impl;

import javax.annotation.Nonnull;
import java.util.List;

class DbSelectBuilderT0Impl extends DbSelectBuilderBaseImpl<DbSelectBuilderT0Impl, SelectBuilderT0Impl<DbSql>>
        implements DbSelectBuilderT0 {

    DbSelectBuilderT0Impl(DbSql sql) {
        super(new SelectBuilderT0Impl<>(sql));
    }

    private DbSelectBuilderT0Impl(SelectBuilderT0Impl<DbSql> selectBuilder) {
        super(selectBuilder);
    }

    @Override
    protected DbSelectBuilderT0Impl self() {
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl copy() {
        return new DbSelectBuilderT0Impl(getSelectBuilder().copy());
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlColumnT<T> column) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(column));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlIdentifier column, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(column, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(column, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(tableAlias, column, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(String columnName, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(columnName, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(String tableAlias, String columnName, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(tableAlias, columnName, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(tableAlias, columnName, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, alias, clazz, binds));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, alias, binds, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, alias, clazz, binds));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, alias, binds, clazz));
    }

    @Nonnull
    @Override
    public Select build() {
        throw new InternalException("Cannot build select statement with no columns " + this);
    }
}
