package com.provys.provysdb.sqlbuilder;

public interface SqlColumnT<T> extends SqlColumn, ExpressionT<T> {

    /**
     * @return Java type this column should be mapped to. Used to find proper adapter for value retrieval
     */
    Class<T> getType();
}
