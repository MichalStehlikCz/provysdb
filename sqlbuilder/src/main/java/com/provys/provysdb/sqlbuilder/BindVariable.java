package com.provys.provysdb.sqlbuilder;

import com.provys.common.exception.InternalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class BindVariable {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(BindVariable.class);

    @Nonnull
    private final SqlName name;
    @Nullable
    private final Object value;

    public BindVariable(SqlName name, @Nullable Object value) {
        this.name = Objects.requireNonNull(name);
        this.value = value;
    }

    @Nonnull
    public SqlName getName() {
        return name;
    }

    @Nonnull
    public Optional<Object> getValue() {
        return Optional.ofNullable(value);
    }

    @Nonnull
    public BindVariable combine(BindVariable other) {
        if (equals(other)) {
            return this;
        }
        if (!name.equals(other.name)) {
            throw new InternalException(LOG,
                    "Cannot combine bind variables with different names (" + name + "!=" + other.name + ")");
        }
        if (value == null) {
            return other;
        } else if (other.value == null) {
            return this;
        }
        throw new InternalException(LOG, "Cannot combine bind variable " + getName().getName() + " - values differ (" +
                this.value + "!=" + other.value + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BindVariable)) return false;

        BindVariable that = (BindVariable) o;

        if (!name.equals(that.name)) return false;
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
                ", value=" + value +
                '}';
    }
}
