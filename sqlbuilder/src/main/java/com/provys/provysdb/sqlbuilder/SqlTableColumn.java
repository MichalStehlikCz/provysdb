package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Table column is extension of regular SqlColumn that adds option to use change table alias
 */
public interface SqlTableColumn extends SqlColumn {

    /**
     * returns same column with different alias. Often used to re/alias columns based on attributes, generated from
     * meta-information, that have default alias. Note that given column is non/mutable class, returned value is
     * different column instance
     *
     * @param alias is alias that should be used for new column
     * @return column with the same content but different alias
     */
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
