package com.provys.provysdb.sqlbuilder;

/**
 * Class holds name of table alias. It can be either name or alias in form {@code <<int>>}, where int is integer;
 * this form allows replacement with unique alias during select text preparation
 */
public interface SqlTableAlias {
    /**
     * @return alias (potentially in form {@code <<int>>})
     */
    String getAlias();
}
