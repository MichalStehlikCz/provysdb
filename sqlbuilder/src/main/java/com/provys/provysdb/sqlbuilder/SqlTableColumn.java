package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Table column is extension of regular SqlColumn that adds option to use change table alias
 */
public interface SqlTableColumn extends SqlColumn {

    @Nonnull
    SqlTableColumn withAlias(SqlIdentifier alias);

    /**
     * Replace column's current table alias with specified one
     *
     * @param tableAlias is new alias for table column is based on
     * @return new table column with table alias replaced with specified one
     */
    @Nonnull
    SqlTableColumn withTableAlias(SqlTableAlias tableAlias);
}
