package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT0;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderT0Impl;
import java.util.Collection;

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
    public DbSelectBuilderT0Impl copy() {
        return new DbSelectBuilderT0Impl(getSelectBuilder().copy());
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> column(SqlColumnT<T> column) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(column));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> column(SqlIdentifier column, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(column, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(column, alias, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(tableAlias, column, alias, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> column(String columnName, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(columnName, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> column(String tableAlias, String columnName, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(tableAlias, columnName, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().column(tableAlias, columnName, alias, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, alias, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, alias, clazz, binds));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, Collection<? extends BindName> binds, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnDirect(columnSql, alias, binds, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, alias, clazz));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, alias, clazz, binds));
    }

    @Override
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Collection<? extends BindValue> binds, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(getSelectBuilder().columnSql(columnSql, alias, binds, clazz));
    }

    @Override
    public Select build() {
        throw new InternalException("Cannot build select statement with no columns " + this);
    }

    @Override
    public String toString() {
        return "DbSelectBuilderT0Impl{" + super.toString() + '}';
    }
}
