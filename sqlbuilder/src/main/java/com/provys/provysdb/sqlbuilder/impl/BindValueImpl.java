package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Represents variable connected with select statement, its type and value.
 */
class BindValueImpl<T> extends BindNameImpl implements BindValueT<T> {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(BindValueImpl.class);

    /**
     * Get bind variable with specified name and value. Type of variable is deferred from supplied value
     *
     * @param name is name of bind
     * @param value is value to be used for the bind
     * @return bind variable with given name and value
     * @param <T> is type of value held by this variable
     */
    @Nonnull
    static <T> BindValueImpl<T> ofObject(String name, T value) {
        return new BindValueImpl<>(name, value);
    }

    /**
     * Get bind variable with specified name, type and value
     *
     * @param name is name of bind
     * @param type is type new bind variable should get
     * @param value is value to be used for the bind
     * @return bind variable with given name, type and value
     * @param <T> is type of value held by this variable
     */
    @Nonnull
    static <T> BindValueImpl<T> ofType(String name, @Nullable T value, Class<T> type) {
        return new BindValueImpl<>(name, type, value);
    }

    @Nonnull
    private final Class<T> type;
    @Nullable
    private final T value;

    private BindValueImpl(String name, T value) {
        super(name);
        //noinspection unchecked
        this.type = (Class<T>) value.getClass();
        this.value = value;
    }

    private BindValueImpl(String name, Class<T> type, @Nullable T value) {
        super(name);
        if ((value != null) && !type.isInstance(value)) {
            throw new InternalException(LOG, "Incorrect type of bind value for variable " + name +
                    " (type " + type + ", value " + value.getClass() + ")");
        }
        this.type = Objects.requireNonNull(type);
        this.value = value;
    }

    /**
     * @return type associated with bind variable
     */
    @Override
    @Nonnull
    public Class<T> getType() {
        return type;
    }

    @Nonnull
    @Override
    public <U> Optional<U> getValue(Class<U> type) {
        if (!type.isAssignableFrom(this.type)) {
            throw new InternalException(LOG, "Cannot convert value of bind variable " + getName() + " from " +
                    this.type + " to " + type);
        }
        //noinspection unchecked
        return Optional.ofNullable((U) value);
    }

    @Nonnull
    @Override
    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    @Nonnull
    @Override
    public BindValueT<T> withValue(@Nullable Object value) {
        if (Objects.equals(this.value, value)) {
            return this;
        }
        if ((value != null) && (!type.isAssignableFrom(value.getClass()))) {
            throw new InternalException(LOG, "Cannot set value to bind variable " + getName() + ", type " + this.type +
                    ", value type " + value.getClass());
        }
        //noinspection unchecked
        return new BindValueImpl<>(getName(), type, (T) value);
    }

    @Override
    @Nonnull
    public BindValueT<T> combine(BindName other) {
        if (other == this) {
            return this;
        }
        if (!getName().equals(other.getName())) {
            throw new InternalException(LOG,
                    "Cannot combine bind variables with different names (" + getName() + "!=" + other.getName() + ")");
        }
        if (!(other instanceof BindValue)) {
            return this;
        }
        var otherVariable = (BindValue) other;
        if (!type.equals(otherVariable.getType())) {
            throw new InternalException(LOG,
                    "Cannot combine bind variables with different types (" + type + "!=" + otherVariable.getType() +
                            ")");
        }
        if (Objects.equals(otherVariable.getValue(Object.class).orElse(null), value)) {
            return this;
        }
        if (value == null) {
            if (!(otherVariable instanceof BindValueT)) {
                throw new InternalException(LOG,
                        "Class implementing BindValue is expected to implement BindValueT as well");
            }
            //noinspection unchecked - we verified that values are of the same type few lines above
            return (BindValueT<T>) otherVariable;
        } else if (otherVariable.getValue(Object.class).isEmpty()) {
            return this;
        }
        throw new InternalException(LOG, "Cannot combine bind variable " + getName() + " - values differ (" +
                this.value + "!=" + otherVariable.getValue(Object.class).orElse(null) + ")");
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BindValueImpl<?> that = (BindValueImpl<?>) o;

        if (!type.equals(that.type)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BindVariableImpl{" +
                "type=" + type +
                ", value=" + value +
                "} " + super.toString();
    }
}
