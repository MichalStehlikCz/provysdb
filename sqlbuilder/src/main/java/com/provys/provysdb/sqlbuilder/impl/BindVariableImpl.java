package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.BindVariableT;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents variable connected with select statement, its type and value.
 */
public class BindVariableImpl<T> implements BindVariableT<T> {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(BindVariableImpl.class);

    /**
     * Get bind variable with specified name and value. Type of variable is deferred from supplied value
     *
     * @param name is name of bind
     * @param value is value to be used for the bind
     * @return bind variable with given name and value
     */
    public static <T> BindVariableImpl<T> ofObject(SqlName name, T value) {
        return new BindVariableImpl<>(name, value);
    }

    public static <T> BindVariableImpl ofType(SqlName name, Class<T> type, @Nullable T value) {
        return new BindVariableImpl<>(name, type, value);
    }

    public static <T> BindVariableImpl ofType(SqlName name, Class<T> type) {
        return new BindVariableImpl<>(name, type, null);
    }

    @Nonnull
    private final SqlName name;
    @Nonnull
    private final Class<T> type;
    @Nullable
    private final T value;

    private BindVariableImpl(SqlName name, T value) {
        this.name = Objects.requireNonNull(name);
        //noinspection unchecked
        this.type = (Class<T>) value.getClass();
        this.value = value;
    }

    private BindVariableImpl(SqlName name, Class<T> type, @Nullable T value) {
        if ((value != null) && !type.isInstance(value)) {
            throw new InternalException(LOG, "Incorrect type of bind value for variable " + name +
                    " (type " + type + ", value " + value.getClass() + ")");
        }
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.value = value;
    }

    /**
     * @return name of bind variable
     */
    @Override
    @Nonnull
    public SqlName getName() {
        return name;
    }

    /**
     * @return type associated with bind variable
     */
    @Nonnull
    public Class<T> getType() {
        return type;
    }

    @Nonnull
    @Override
    public <U> Optional<U> getValue(Class<U> type) {
        if (!type.isAssignableFrom(this.type)) {
            throw new InternalException(LOG, "Cannot convert value of bind variable " + name + " from " + this.type +
                    " to " + type);
        }
        //noinspection unchecked
        return Optional.ofNullable((U) value);
    }

    @Nonnull
    @Override
    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    @Nonnull
    public BindVariable combine(BindVariable other) {
        if (other == this) {
            return this;
        }
        if (!name.equals(other.getName())) {
            throw new InternalException(LOG,
                    "Cannot combine bind variables with different names (" + name + "!=" + other.getName() + ")");
        }
        if (!type.equals(other.getType())) {
            throw new InternalException(LOG,
                    "Cannot combine bind variables with different types (" + type + "!=" + other.getType() + ")");
        }
        if (Objects.equals(other.getValue(Object.class).orElse(null), value)) {
            return this;
        }
        if (value == null) {
            return other;
        } else if (other.getValue(Object.class).isEmpty()) {
            return this;
        }
        throw new InternalException(LOG, "Cannot combine bind variable " + getName().getName() + " - values differ (" +
                this.value + "!=" + other.getValue(Object.class).orElse(null) + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BindVariableImpl)) return false;

        BindVariableImpl that = (BindVariableImpl) o;

        if (!name.equals(that.name)) return false;
        if (!type.equals(that.type)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BindVariable{" +
                "name=" + name +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

    @Override
    public void addSql(CodeBuilder builder) {

    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return null;
    }
}
