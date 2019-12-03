package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SelectBuilderT2Impl<S extends Sql, T1, T2> extends SelectBuilderBaseImpl<SelectBuilderT2Impl<S, T1, T2>, S>
        implements SelectBuilderT2<T1, T2> {

    private final SqlColumnT<T1> column1;
    private final SqlColumnT<T2> column2;

    public SelectBuilderT2Impl(S sql, SqlColumnT<T1> column1, SqlColumnT<T2> column2) {
        super(sql);
        this.column1 = Objects.requireNonNull(column1);
        this.column2 = Objects.requireNonNull(column2);
    }

    SelectBuilderT2Impl(S sql, SqlColumnT<T1> column1, SqlColumnT<T2> column2, List<SqlFrom> tables,
                        Collection<Condition> conditions) {
        super(sql, List.of(column1), tables, conditions);
        this.column1 = Objects.requireNonNull(column1);
        this.column2 = Objects.requireNonNull(column2);
    }

    @Nonnull
    @Override
    SelectBuilderT2Impl<S, T1, T2> self() {
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
    public SelectBuilderT2Impl<S, T1, T2> copy() {
        return new SelectBuilderT2Impl<>(getSql(), column1, column2, getTables(), getConditions());
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(SqlColumnT<T> column) {
        return new SelectBuilderImpl<>(getSql(), List.of(column1, column2, column), getTables(), getConditions());
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(SqlIdentifier column, Class<T> clazz) {
        if (getTables().isEmpty()) {
            return column(getSql().column(column, clazz));
        }
        return column(getSql().column(getTables().get(getTables().size() - 1).getAlias(), column, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        if (getTables().isEmpty()) {
            return column(getSql().column(column, alias, clazz));
        }
        return column(getSql().column(getTables().get(getTables().size() - 1).getAlias(), column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias,
                                         Class<T> clazz) {
        return column(getSql().column(tableAlias, column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(String columnName, Class<T> clazz) {
        if (getTables().isEmpty()) {
            return column(getSql().column(columnName, clazz));
        }
        return column(getSql().column(getTables().get(getTables().size()-1).getAlias(), getSql().name(columnName),
                clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, Class<T> clazz) {
        return column(getSql().column(tableAlias, columnName, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(getSql().column(tableAlias, columnName, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String columnSql, Class<T> clazz) {
        return column(getSql().columnDirect(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return column(getSql().columnDirect(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return column(getSql().columnDirect(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return column(getSql().columnDirect(columnSql, alias, binds, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, Class<T> clazz) {
        return column(getSql().columnSql(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz) {
        return column(getSql().columnSql(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return column(getSql().columnSql(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return column(getSql().columnSql(columnSql, alias, binds, clazz));
    }
}
