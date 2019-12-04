package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Valid name of sql object. Supports simple name (text starting with letter and containing letters, numbers and
 * characters _, # and $), that is translated to lowercase or quoted name (enclosed in ", containing any printable
 * character, case sensitive)
 */
public interface SqlIdentifier {

    /**
     * @return textual representation of name (e.g. name itself, including delimiter)
     */
    @Nonnull
    String getText();

    /**
     * @return name as represented in database catalogue (e.g. ordinary name uppercased, without delimiters, ...)
     */
    @Nonnull
    String getDbName();
}
