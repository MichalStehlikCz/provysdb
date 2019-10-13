package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * Default implementation of bind name. Provides validation and normalisation of name of bind variable (as everything
 * else in SQL, bind names are case insensitive. Bind name mus be string that starts with letter and contains only
 * letters, numbers and underscore.
 */
class BindNameImpl implements BindName {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(BindNameImpl.class);

    @Nonnull
    private static final Pattern NAME_PATTERN = Pattern.compile("([A-Z][A-Z0-9_]*)");

    @Nonnull
    private static String validateName(String name) {
        var result = name.trim().toUpperCase();
        if (!NAME_PATTERN.matcher(result).matches()) {
            throw new InternalException(LOG, "Invalid bind name " + name);
        }
        return result;
    }

    @Nonnull
    private final String name;

    BindNameImpl(String name) {
        this.name = validateName(name);
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public BindVariable withValue(@Nullable Object value) {
        return BindVariableImpl.ofObject(getName(), value);
    }

    @Nonnull
    @Override
    public BindName combine(BindName other) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BindNameImpl bindName = (BindNameImpl) o;

        return name.equals(bindName.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "BindNameImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
