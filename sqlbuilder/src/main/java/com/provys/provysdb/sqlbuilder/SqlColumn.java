package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Represents column definition in select statement
 */
public interface SqlColumn extends SqlElement {

    /**
     * @return alias this column is associated with; empty optional if column has no alias. Note that if it is simple
     * column, its name is also used as alias
     */
    @Nonnull
    Optional<SqlIdentifier> getAlias();

    /**
     * Create new column with alias replaced with specified one
     *
     * @param alias is alias that should be used for new column
     * @return column with specified alias
     */
    @Nonnull
    SqlColumn withAlias(SqlIdentifier alias);
}
