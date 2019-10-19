package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

/**
 * Implements support for SQL strings that can act as name; simple wrapper on String class, non-mutable. Does
 * normalisation of supplied text, thus equals on sql identifiers is equivalent to two identifiers pointing to the same
 * object
 */
public class SqlIdentifierImpl implements com.provys.provysdb.sqlbuilder.SqlIdentifier {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(SqlIdentifierImpl.class);

    @Nonnull
    private static final Pattern PATTERN_ORDINARY = Pattern.compile("([A-Z][A-Z0-9_#$]*)");
    @Nonnull
    private static final Pattern PATTERN_DELIMITED = Pattern.compile("(\"(?:[^\"]|\"\")*\")");

    /**
     * Parse supplied sql text into identifier. Supports both ordinary and delimited names
     *
     * @param text is supplied sql text
     * @return parsed identifier
     */
    @Nonnull
    public static SqlIdentifierImpl parse(String text) {
        var result = text.trim();
        if (result.charAt(0) == '"') {
            // delimited identifier
            if (!PATTERN_DELIMITED.matcher(result).matches()) {
                throw new IllegalArgumentException("Invalid text supplied for delimited identifier: " + text);
            }
            result = result.substring(1, result.length()-1).trim().replace("\"\"", "\"");
        } else {
            // ordinary identifier
            result = result.toUpperCase();
            if (!PATTERN_ORDINARY.matcher(result).matches()) {
                throw new IllegalArgumentException("Invalid text supplied for ordinary identifier: " + text);
            }
        }
        return new SqlIdentifierImpl(result);
    }

    @Nonnull
    private static String validate(String name) {
        var result = name.trim();
        if (result.isEmpty()) {
            throw new InternalException(LOG, "Blank text supplied as SQL name");
        }
        if (result.contains("\n")) {
            throw new InternalException(LOG, "Invalid SQL delimited name - name cannot contain newline");
        }
        return result;
    }

    @Nonnull
    private final String name;
    private boolean delimited;

    private SqlIdentifierImpl(String name) {
        this.name = validate(name);
        delimited = !PATTERN_ORDINARY.matcher(this.name).matches();
    }

    @Nonnull
    @Override
    public String getText() {
        if (delimited) {
            return '"' + name.replace("\"", "\"\"") + '"';
        }
        return name.toLowerCase();
    }

    @Nonnull
    public String getDbName() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlIdentifierImpl sqlName = (SqlIdentifierImpl) o;

        return name.equals(sqlName.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Nonnull
    @Override
    public String toString() {
        return "SqlIdentifierImpl{" + name + "}";
    }
}
