package com.provys.provysdb.sqlbuilder;

public interface SqlElement {

    /**
     * Adds sql text to string builder, used to construct statement; also add associated binds
     *
     * @param builder is StringBuilder, used to construct sql text
     */
    void addSql(CodeBuilder builder);
}
