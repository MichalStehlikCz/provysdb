package com.provys.provysdb.sqlbuilder;

public interface SqlWhere extends SqlElement {
    /**
     * @return true if condition is empty (returns true for all lines and its evaluation can be skipped)
     */
    boolean isEmpty();
}
