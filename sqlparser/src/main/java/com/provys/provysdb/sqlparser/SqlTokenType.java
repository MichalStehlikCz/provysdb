package com.provys.provysdb.sqlparser;

public enum SqlTokenType {

    NAME(true),
    COMMENT(true),
    SYMBOL(true),
    LITERAL(true);

    private final boolean withSubtypes;

    SqlTokenType(boolean withSubtypes) {
        this.withSubtypes = withSubtypes;
    }

    /**
     * @return indication if subtypes are used with this token type
     */
    public boolean isWithSubtypes() {
        return withSubtypes;
    }
}
