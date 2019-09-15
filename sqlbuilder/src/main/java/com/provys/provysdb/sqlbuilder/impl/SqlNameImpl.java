package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.SqlName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

/**
 * Implements support for SQL strings that can act as name; simple wrapper on String class
 */
class SqlNameImpl implements SqlName {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(SqlNameImpl.class);

    @Nonnull
    private static final Pattern PATTERN = Pattern.compile("(?:[a-zA-Z][a-zA-Z0-9_#$]*)|(\"[^\"]*\")");

    @Nonnull
    private static String validate(String name) {
        if (name.isBlank()) {
            throw new InternalException(LOG, "Blank text supplied as SQL name");
        }
        if (!PATTERN.matcher(name).matches()) {
            throw new InternalException(LOG, "Supplied name is not valid SQL name - '" + name + "'");
        }
        if (name.charAt(0) == '"') {
            return name;
        }
        return name.toLowerCase();
    }

    @Nonnull
    private final String name;

    SqlNameImpl(String name) {
        this.name = validate(name);
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlNameImpl sqlName = (SqlNameImpl) o;

        return getName().equals(sqlName.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Nonnull
    @Override
    public String toString() {
        return "SqlName{" + name + "}";
    }
}
