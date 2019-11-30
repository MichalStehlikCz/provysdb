package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SelectBuilderT2Impl<T1, T2> extends SelectBuilderTImpl<SelectBuilderT2Impl<T1, T2>>
        implements SelectBuilderT2<T1, T2> {

    private final SqlColumnT<T1> column1;
    private final SqlColumnT<T2> column2;

    SelectBuilderT2Impl(DbSql sql, SqlColumnT<T1> column1, SqlColumnT<T2> column2) {
        super(sql);
        this.column1 = Objects.requireNonNull(column1);
        this.column2 = Objects.requireNonNull(column2);
    }

    SelectBuilderT2Impl(DbSql sql, SqlColumnT<T1> column1, SqlColumnT<T2> column2, List<SqlFrom> tables,
                        Collection<Condition> conditions) {
        super(sql, List.of(column1), tables, conditions);
        this.column1 = Objects.requireNonNull(column1);
        this.column2 = Objects.requireNonNull(column2);
    }

    @Nonnull
    @Override
    SelectBuilderT2Impl<T1, T2> self() {
        return this;
    }

    @Nonnull
    @Override
    public List<SqlColumn> getColumns() {
        return List.of(column1, column2);
    }

    @Nonnull
    @Override
    protected List<SqlColumn> getModifiableColumns() {
        return List.of(column1, column2);
    }

    @Nonnull
    @Override
    public SelectBuilderT2Impl<T1, T2> copy() {
        return new SelectBuilderT2Impl<>(sql, column1, column2, tables, conditions);
    }

    @Nonnull
    private <T> SelectBuilder column(SqlColumnT<T> column) {
        return new SelectBuilderImpl(sql, List.of(column1, column2, column), tables, conditions);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(SqlIdentifier column, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(column, clazz));
        }
        return column(sql.column(tables.get(tables.size() - 1).getAlias(), column, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(column, alias, clazz));
        }
        return column(sql.column(tables.get(tables.size() - 1).getAlias(), column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias,
                                         Class<T> clazz) {
        return column(sql.column(tableAlias, column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(String columnName, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(columnName, clazz));
        }
        return column(sql.column(tables.get(tables.size()-1).getAlias(), sql.name(columnName), clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(String tableAlias, String columnName, Class<T> clazz) {
        return column(sql.column(tableAlias, columnName, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(sql.column(tableAlias, columnName, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String columnSql, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String columnSql, String alias, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return column(sql.columnDirect(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, alias, binds, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, Class<T> clazz) {
        return column(sql.columnSql(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, String alias, Class<T> clazz) {
        return column(sql.columnSql(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, String alias, Class<T> clazz, BindVariable... binds) {
        return column(sql.columnSql(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, String alias, Iterable<BindVariable> binds, Class<T> clazz) {
        return column(sql.columnSql(columnSql, alias, binds, clazz));
    }
}
