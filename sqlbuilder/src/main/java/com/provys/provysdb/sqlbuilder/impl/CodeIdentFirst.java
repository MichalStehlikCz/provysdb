package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;

/**
 * Ident that has different prefix on the first line and subsequent lines. Note that unlike other ident classes, this
 * one is mutable (state indicates if the first line has been used already or not)
 */
class CodeIdentFirst implements CodeIdent {

    /**
     * Create ident manager with specified first and regular line prefixes. Typically used when managing parameter
     * lists, where starting on second line, , character is added
     *
     * @param firstIdent is ident (prefix) to be used on the first line
     * @param ident is ident (prefix) to be used on all lines starting with second
     * @return new ident manager with required characteristics
     */
    @Nonnull
    static CodeIdentFirst of(String firstIdent, String ident) {
        return new CodeIdentFirst(firstIdent, ident);
    }

    @Nonnull
    private final String firstIdent;
    @Nonnull
    private final String ident;
    private boolean firstActive;

    private CodeIdentFirst(String firstIdent, String ident) {
        this.firstIdent = firstIdent;
        this.ident = ident;
        this.firstActive = true;
    }

    @Nonnull
    @Override
    public String get() {
        return firstActive ? firstIdent : ident;
    }

    @Override
    public void use(StringBuilder builder) {
        builder.append(get());
        firstActive = false;
    }

    @Nonnull
    @Override
    public CodeIdent copy() {
        var result = CodeIdentFirst.of(firstIdent, ident);
        if (!firstActive) {
            result.firstActive = false;
        }
        return result;
    }
}
