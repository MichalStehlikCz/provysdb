package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.SqlElement;

import javax.annotation.Nonnull;

/**
 * Interface represents Sql token - name, symbol, ...
 */
public interface SqlToken extends SqlElement {

    /**
     * @return token type
     */
    @Nonnull
    SqlTokenType getType();

    /**
     * @return if space before is needed
     */
     SpaceMode spaceBefore();

    /**
     * @return if space after is needed
     */
    SpaceMode spaceAfter();
}
