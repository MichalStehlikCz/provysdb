package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT2;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderT2Impl;

import javax.annotation.Nonnull;
import java.util.List;

class DbSelectBuilderT2Impl<T1, T2>
        extends DbSelectBuilderBaseImpl<DbSelectBuilderT2Impl<T1, T2>, SelectBuilderT2Impl<DbSql, T1, T2>>
        implements DbSelectBuilderT2<T1, T2> {

    DbSelectBuilderT2Impl(DbSql sql, SqlColumnT<T1> column1, SqlColumnT<T2> column2) {
        super(new SelectBuilderT2Impl<>(sql, column1, column2));
    }

    DbSelectBuilderT2Impl(SelectBuilderT2Impl<DbSql, T1, T2> selectBuilder) {
        super(selectBuilder);
    }

    @Nonnull
    @Override
    protected DbSelectBuilderT2Impl<T1, T2> self() {
        return this;
    }

    @Nonnull
    @Override
    public DbSelectBuilderT2Impl<T1, T2> copy() {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().copy());
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl column(SqlColumnT<T> column) {
        return new DbSelectBuilderImpl(getSelectBuilder().column(column));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl column(SqlIdentifier column, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().column(column, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().column(column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().column(tableAlias, column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl column(String columnName, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().column(columnName, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl column(String tableAlias, String columnName, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().column(tableAlias, columnName, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().column(tableAlias, columnName, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnDirect(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnDirect(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, alias, binds, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnSql(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, alias, binds, clazz));
    }
}
