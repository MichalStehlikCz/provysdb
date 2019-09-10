package com.provys.provysdb.sqlparser;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Represents token parsed from Sql source. Usually retrieved via tokenizer
 */
public interface SqlToken {
    /**
     * @return line of source token was parsed from
     */
    int getLine();

    /**
     * @return position in line where token starts
     */
    int getPos();

    /**
     * @return token type
     */
    @Nonnull
    SqlTokenType getType();

    /**
     * @return token subtype; only available for certain token types as indicated by {@code SqlTokenType.isWithSubtypes}
     * property of token type
     */
    @Nonnull
    Optional<SqlTokenSubtype> getSubtype();
}
