package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Condition represents conditional statement (e.g. statement with database return type BOOLEAN). It can be used in
 * WHERE clause and combined using logical operators
 */
public interface Condition extends SqlElement, ExpressionT<DbBoolean> {
    /**
     * @return true if condition is empty (returns true for all lines and its evaluation can be skipped)
     */
    boolean isEmpty();

    /**
     * All conditions are of database boolean type... and this method preserves this information in runtime
     *
     * @return DbBoolean.class
     */
    @Nonnull
    @Override
    default Class<DbBoolean> getType() {
        return DbBoolean.class;
    }
}
