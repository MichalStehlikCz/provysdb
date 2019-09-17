package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

class SqlTableAliasImpl implements SqlTableAlias {

    @Nonnull
    private static final Logger LOG = LogManager.getLogger(SqlTableAliasImpl.class);

    @Nonnull
    private static final Pattern PATTERN = Pattern.compile("(?:[a-zA-Z][a-zA-Z0-9_#$]*)|(?:\"[^\"]*\")|(?:<<[0-9]+>>)");

    @Nonnull
    private static String validate(String aliasText) {
        if (aliasText.isBlank()) {
            throw new InternalException(LOG, "Blank text supplied as SQL table alias");
        }
        if (!PATTERN.matcher(aliasText).matches()) {
            throw new InternalException(LOG, "Supplied text is not valid SQL table alias - '" + aliasText + "'");
        }
        if ((aliasText.charAt(0) == '"') || (aliasText.charAt(0) == '<')) {
            return aliasText;
        }
        return aliasText.toLowerCase();
    }

    @Nonnull
    private final String alias;

    SqlTableAliasImpl(String alias) {
        this.alias = validate(alias);
    }

    @Nonnull
    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlTableAliasImpl that = (SqlTableAliasImpl) o;

        return getAlias().equals(that.getAlias());
    }

    @Override
    public int hashCode() {
        return getAlias().hashCode();
    }

    @Override
    public String toString() {
        return "SqlTableAliasImpl{" +
                "alias='" + alias + '\'' +
                '}';
    }
}
