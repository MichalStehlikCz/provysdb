package com.provys.provysdb.sqlbuilder;

/**
 * Class holds name of table alias. It can be either name or alias in form {@code <<int>>}, where int is integer;
 * this form allows replacement with unique alias during select text preparation
 */
public interface SqlTableAlias {
    /**
     * @return alias text (e.g. potentially in form {@code <<int>>})
     */
    String getAliasText();

    /**
     * @return effective value of alias (e.g. actual value if indexed alias has been used originally)
     */
    String getAlias(SelectBuilder selectBuilder);
}
