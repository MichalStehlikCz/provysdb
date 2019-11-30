package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SelectBuilderT1Impl<T1> extends SelectBuilderTImpl<SelectBuilderT1Impl<T1>>
        implements SelectBuilderT1<T1> {

    @Nonnull
    private final SqlColumnT<T1> column1;

    SelectBuilderT1Impl(DbSql sql, SqlColumnT<T1> column1) {
        super(sql);
        this.column1 = Objects.requireNonNull(column1);
    }

    SelectBuilderT1Impl(DbSql sql, SqlColumnT<T1> column1, List<SqlFrom> tables, Collection<Condition> conditions) {
        super(sql, List.of(column1), tables, conditions);
        this.column1 = Objects.requireNonNull(column1);
    }

    @Nonnull
    @Override
    SelectBuilderT1Impl<T1> self() {
        return this;
    }

    @Nonnull
    @Override
    public List<SqlColumn> getColumns() {
        return List.of(column1);
    }

    @Nonnull
    @Override
    protected List<SqlColumn> getModifiableColumns() {
        return List.of(column1);
    }

    @Nonnull
    @Override
    public SelectBuilderT1Impl<T1> copy() {
        return new SelectBuilderT1Impl<>(sql, column1, tables, conditions);
    }

    @Nonnull
    private <T> SelectBuilderT2<T1, T> column(SqlColumnT<T> column) {
        return new SelectBuilderT2Impl<>(sql, column1, column, tables, conditions);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> column(SqlIdentifier column, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(column, clazz));
        }
        return column(sql.column(tables.get(tables.size() - 1).getAlias(), column, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(column, alias, clazz));
        }
        return column(sql.column(tables.get(tables.size() - 1).getAlias(), column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias,
                                         Class<T> clazz) {
        return column(sql.column(tableAlias, column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> column(String columnName, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(columnName, clazz));
        }
        return column(sql.column(tables.get(tables.size()-1).getAlias(), sql.name(columnName), clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> column(String tableAlias, String columnName, Class<T> clazz) {
        return column(sql.column(tableAlias, columnName, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(sql.column(tableAlias, columnName, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnDirect(String columnSql, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return column(sql.columnDirect(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, alias, binds, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnSql(String columnSql, Class<T> clazz) {
        return column(sql.columnSql(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return column(sql.columnSql(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnSql(String columnSql, String alias, Class<T> clazz, BindVariable... binds) {
        return column(sql.columnSql(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT2<T1, T> columnSql(String columnSql, String alias, Iterable<BindVariable> binds,
                                                Class<T> clazz) {
        return column(sql.columnSql(columnSql, alias, binds, clazz));
    }

    @Nonnull
    public SelectStatementT1Impl<T1> prepare() {
        var builder = builder();
        return new SelectStatementT1Impl<>(builder.build(), builder.getBinds(), sql, column1);
    }
}
