package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.SqlTypeAdapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Optional;

class SqlAdapterToOptional<T> implements SqlTypeAdapter<Optional<T>> {

    @Nonnull
    private final SqlTypeAdapter<T> typeAdapter;

    SqlAdapterToOptional(SqlTypeAdapter<T> typeAdapter) {
        this.typeAdapter = Objects.requireNonNull(typeAdapter);
    }

    @Override
    public Class<Optional<T>> getType() {
        //noinspection unchecked
        return (Class<Optional<T>>) (Class<?>) Optional.class;
    }

    @Override
    public int getSqlType() {
        return typeAdapter.getSqlType();
    }

    @Nonnull
    @Override
    public Optional<String> getTypeName() {
        return typeAdapter.getTypeName();
    }

    @Nonnull
    @Override
    public Optional<T> readValue(ResultSet resultSet, int columnIndex) {
        return typeAdapter.readOptionalValue(resultSet, columnIndex);
    }

    @Nonnull
    @Override
    public Optional<T> readValue(ResultSet resultSet, String columnLabel) {
        return typeAdapter.readOptionalValue(resultSet, columnLabel);
    }

    @Nonnull
    @Override
    public Optional<Optional<T>> readOptionalValue(ResultSet resultSet, int columnIndex) {
        return Optional.of(readValue(resultSet, columnIndex));
    }

    @Nonnull
    @Override
    public Optional<Optional<T>> readOptionalValue(ResultSet resultSet, String columnLabel) {
        return Optional.of(readValue(resultSet, columnLabel));
    }

    @Override
    @SuppressWarnings("squid:S2789") // we are enforced to have nullable optional parameter - required by interface
    public void bindValue(PreparedStatement statement, int parameterIndex, @Nullable Optional<T> value) {
        //noinspection OptionalAssignedToNull - we are enforced to have nullable optional parameter - required by interface
        typeAdapter.bindValue(statement, parameterIndex, (value == null) ? null : value.orElse(null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlAdapterToOptional<?> that = (SqlAdapterToOptional<?>) o;
        return typeAdapter.equals(that.typeAdapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeAdapter);
    }

    @Override
    public String toString() {
        return "SqlAdapterToOptional{" +
                "typeAdapter=" + typeAdapter +
                '}';
    }
}
