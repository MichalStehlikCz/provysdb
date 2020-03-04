package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT1;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderT1Impl;

import javax.annotation.Nonnull;
import java.util.List;

class DbSelectBuilderT1Impl<T1> extends DbSelectBuilderBaseImpl<DbSelectBuilderT1Impl<T1>, SelectBuilderT1Impl<DbSql, T1>>
        implements DbSelectBuilderT1<T1> {

    DbSelectBuilderT1Impl(DbSql sql, SqlColumnT<T1> column) {
        super(new SelectBuilderT1Impl<>(sql, column));
    }

    DbSelectBuilderT1Impl(SelectBuilderT1Impl<DbSql, T1> selectBuilder) {
        super(selectBuilder);
    }

    @Nonnull
    @Override
    protected DbSelectBuilderT1Impl<T1> self() {
        return this;
    }
    
    @Override
    @Nonnull
    public DbSelectBuilderT1Impl<T1> copy() {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().copy());
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> column(SqlColumnT<T> column) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(column));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> column(SqlIdentifier column, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(column, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(column, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(tableAlias, column, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> column(String columnName, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(columnName, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> column(String tableAlias, String columnName, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(tableAlias, columnName, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(tableAlias, columnName, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnDirect(columnSql, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnDirect(columnSql, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnDirect(columnSql, alias, clazz, binds));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnDirect(columnSql, alias, binds, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnSql(columnSql, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnSql(columnSql, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnSql(columnSql, alias, clazz, binds));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnSql(columnSql, alias, binds, clazz));
    }

    @Override
    @Nonnull
    public SelectStatementT1Impl<T1> prepare() {
        var builder = builder();
        return new SelectStatementT1Impl<>(builder.build(), builder.getBinds(), getSql(), getSelectBuilder().getColumn1());
    }
}
