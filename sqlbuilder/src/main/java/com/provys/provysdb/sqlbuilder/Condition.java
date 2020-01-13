package com.provys.provysdb.sqlbuilder;

/**
 * Condition represents conditional statement (e.g. statement with database return type BOOLEAN). It can be used in
 * WHERE clause and combined using logical operators
 */
public interface Condition extends SqlElement, ExpressionT<DbBoolean> {
    /**
     * @return true if condition is empty (returns true for all lines and its evaluation can be skipped)
     */
    boolean isEmpty();
}
