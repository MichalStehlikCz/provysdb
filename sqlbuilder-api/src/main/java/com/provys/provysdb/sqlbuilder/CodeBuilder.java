package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * CodeBuilder is object that allows to build SQL statement (string) from lines.
 * It is used internally by Select to build SQL text from structured query.
 *
 * @author stehlik
 */
@SuppressWarnings("UnusedReturnValue")
public interface CodeBuilder {

    /**
     * Appends piece of text to already existing code.
     *
     * @param text contains text to be added
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder append(String text);

    /**
     * Appends piece of text to already existing code; char variant
     *
     * @param character contains text to be added
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder append(char character);

    /**
     * Appends piece of text to already existing code; int variant
     *
     * @param number contains text to be added
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder append(int number);

    /**
     * Appends piece of text to already existing code; BigInteger variant
     *
     * @param number contains text to be added
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder append(BigInteger number);

    /**
     * Appends piece of text to already existing code; SqlName variant
     *
     * @param name contains text to be added
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder append(SqlIdentifier name);

    /**
     * Appends piece of text to already existing code; SqlTableAlias variant
     *
     * @param alias contains text to be added
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder append(SqlTableAlias alias);

    /**
     * Appends piece of text that might span multiple lines to already existing
     * code.
     * Inserts proper ident to newlines in supplied text; ident uses spaces, not currently set prefix and is increased
     * by specified amount against current level
     *
     * @param text contains text to be added
     * @param additionalIdent is number of characters that newlines should be idented above current level
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder appendWrapped(String text, int additionalIdent);

    /**
     * Appends piece of text that might span multiple lines to already existing
     * code.
     * Inserts proper ident to newlines in supplied text; ident uses spaces, not currently set prefix, but keeps level
     * with current length.
     *
     * @param text contains text to be added
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder appendWrapped(String text);

    /**
     * Finishes line in already existing code.
     *
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder appendLine();

    /**
     * Appends line of text to already existing code.
     *
     * @param line contains text to be added (without newline char)
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder appendLine(String line);

    /**
     * Retrieve ident builder, that allows to build more complex ident rules
     */
    @Nonnull
    CodeIdentBuilder identBuilder();

    /**
     * Sets ident to specified ident.
     *
     * @param ident is ident to be used
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder setIdent(CodeIdent ident);

    /**
     * Sets ident to given string.
     *
     * @param ident is new ident string
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder setIdent(String ident);

    /**
     * Sets ident to given string, left padded to specified length.
     *
     * @param ident is new ident string
     * @param chars is length to which ident should be left padded with spaces
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder setIdent(String ident, int chars);

    /**
     * Sets ident and first ident to given strings.
     *
     * @param firstIdent is new ident for the first line
     * @param ident is new ident valid from the second line on
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder setIdent(String firstIdent, String ident);

    /**
     * Sets ident and first ident to given strings, left padded to required
     * length.
     *
     * @param firstIdent is new ident for the first line
     * @param ident is new ident valid from the second line on
     * @param chars is required ident length - supplied strings will be left
     * padded with spaces to reach this length
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder setIdent(String firstIdent, String ident, int chars);

    /**
     * Increases ident level and sets ident to spaces, regardless of original ident. It is generally used for inside of
     * code block
     *
     * @param increaseBy is number of additional characters
     * @return returns self to support fluent build
     */
    @Nonnull
    CodeBuilder increasedIdent(int increaseBy);

    /**
     * Increase ident length by defined number of characters and replace ident
     * with specified text, left padded with spaces.
     *
     * @param ident new ident text, left padded ith spaces to required length
     * @param increaseBy is number of characters ident should be increased by
     * @return self to support chaining
     */
    @Nonnull
    CodeBuilder increasedIdent(String ident, int increaseBy);

    /**
     * Increase ident length by defined number of characters and replace ident
     * with specified text, left padded with spaces. First line will get
     * different ident
     *
     * @param firstIdent is ident text that will be used first line, after left
     * padding it with spaces to required length
     * @param ident is ident text that will be used from second line on
     * @param increaseBy is number of characters ident should be increased by
     * @return self to support chaining
     */
    @Nonnull
    CodeBuilder increasedIdent(String firstIdent, String ident, int increaseBy);

    /**
     * Returns to previous ident.
     *
     * @return self to allow chaining.
     */
    @Nonnull
    CodeBuilder popIdent();

    /**
     * Appends bind variable to end of binds collection
     *
     * @param bind is bind variable to be added to list of binds
     * @return self to support chaining
     */
    @Nonnull
    CodeBuilder addBind(BindName bind);

    /**
     * Appends list of bind variables to end of binds collection
     *
     * @param binds are bind variables to be added to list of binds
     * @return self to support chaining
     */
    @Nonnull
    CodeBuilder addBinds(List<BindName> binds);

    /**
     * Go through existing binds and if bind variable with matching name is found in supplied collection, combine bind
     * with supplied variable
     *
     * @param bindVariables are variables supplied to add type and value to binds
     * @return self to support chaining
     */
    @Nonnull
    CodeBuilder applyBindVariables(Iterable<BindVariable> bindVariables);

    /**
     * Method retrieves code, produced by CodeBuilder.
     *
     * @return code that has been built using this builder
     */
    @Nonnull
    String build();

    /**
     * @return list of binds, collected in builder
     */
    @Nonnull
    List<BindName> getBinds();
}
