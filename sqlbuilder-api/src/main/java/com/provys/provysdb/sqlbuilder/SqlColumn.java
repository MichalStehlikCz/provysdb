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
    Optional<SqlName> getAlias();
}
