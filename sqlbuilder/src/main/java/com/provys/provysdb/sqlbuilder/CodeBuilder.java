package com.provys.provysdb.sqlbuilder;

/**
 * CodeBuilder is object that allows to build SQL statement (string) from lines.
 * It is used internally by Select to build SQL text from structured query.
 *
 * @author stehlik
 */
public interface CodeBuilder {

    /**
     * Appends piece of text to already existing code.
     *
     * @param text contains text to be added
     * @return returns self to support chaining
     */
    CodeBuilder append(String text);

    /**
     * Appends piece of text that might span multiple lines to already existing
     * code.
     * Inserts proper ident to newlines in supplied text; ident uses spaces, not currently set prefix and is increased
     * by specified amount against current level
     *
     * @param text contains text to be added
     * @param additionalIdent is number of characters that newlines should be idented above current level
     * @return returns self to support chaining
     */
    CodeBuilder appendWrapped(String text, int additionalIdent);

    /**
     * Appends piece of text that might span multiple lines to already existing
     * code.
     * Inserts proper ident to newlines in supplied text; ident uses spaces, not currently set prefix, but keeps level
     * with current length.
     *
     * @param text contains text to be added
     * @return returns self to support chaining
     */
    CodeBuilder appendWrapped(String text);

    /**
     * Finishes line in already existing code.
     *
     * @return returns self to support chaining
     */
    CodeBuilder appendLine();

    /**
     * Appends line of text to already existing code.
     *
     * @param line contains text to be added (without newline char)
     * @return returns self to support chaining
     */
    CodeBuilder appendLine(String line);

    /**
     * Increases ident level (using spaces).
     *
     * @param chars is number of additional characters, must be positive number
     * @return self to allow chaining
     */
    CodeBuilder increaseIdent(int chars);

    /**
     * Decrease ident level by cutting given number of characters from the end.
     *
     * @param chars is number of characters to be removed
     * @return self to allow chaining
     */
    CodeBuilder decreaseIdent(int chars);

    /**
     * Sets ident to given string.
     *
     * @param ident is new ident string
     * @return self to support chaining
     */
    CodeBuilder setIdent(String ident);

    /**
     * Sets ident to given string, left padded to specified length.
     *
     * @param ident is new ident string
     * @param chars is length to which ident should be left padded with spaces
     * @return self to support chaining
     */
    CodeBuilder setIdent(String ident, int chars);

    /**
     * Sets ident and first ident to given strings.
     *
     * @param firstIdent is new ident for the first line
     * @param regularIdent is new ident valid from the second line on
     * @return self to support chaining
     */
    CodeBuilder setIdent(String firstIdent, String regularIdent);

    /**
     * Sets ident and first ident to given strings, left padded to required
     * length.
     *
     * @param firstIdent is new ident for the first line
     * @param regularIdent is new ident valid from the second line on
     * @param chars is required ident length - supplied strings will be left
     * padded with spaces to reach this length
     * @return self to support chaining
     */
    CodeBuilder setIdent(String firstIdent, String regularIdent, int chars);

    /**
     * Appends given text to identation.
     *
     * @param text is string to be appended to ident
     * @return self to support chaining
     */
    CodeBuilder appendIdent(String text);

    /**
     * Appends given text to identation, using different ident on the first line.
     * Often used with list of parameters and such, as first line is spaces
     * only, starting from second line, comma is used. Any subsequent operation
     * ident will cancel first ident
     *
     * @param firstIdent is additional text appended to ident on the first line
     * @param regularIdent is additiona ltext added to ident starting from
     * second line
     * @return self to support chaining
     */
    CodeBuilder appendIdent(String firstIdent, String regularIdent);

    /**
     * Increase ident length by defined number of characters and replace ident
     * with specified text, left padded with spaces.
     *
     * @param ident new ident text, left padded ith spaces to required length
     * @param increaseBy is number of characters ident should be increased by
     * @return self to support chaining
     */
    CodeBuilder increaseIdent(String ident, int increaseBy);

    /**
     * Increase ident length by defined number of characters and replace ident
     * with specified text, left padded with spaces. First line will get
     * different ident
     *
     * @param firstIdent is ident text that will be used first line, after left
     * padding it with spaces to required length
     * @param regularIdent is ident text that will be used from second line on
     * @param increaseBy is number of characters ident should be increased by
     * @return self to support chaining
     */
    CodeBuilder increaseIdent(String firstIdent, String regularIdent, int increaseBy);

    /**
     * Set identation to specified text temporarily.
     * Temporary ident means that it is possible to return to previous setting
     * using removeTempIdent method instead of decreaseIdent
     *
     * @param ident is new ident to be used
     * @return self to support chaining
     */
    CodeBuilder setTempIdent(String ident);

    /**
     * Set temporary text to identation, using different ident on the first
     * line.
     * Often used with list of parameters and such, as first line is spaces
     * only, starting from second line, comma is used. Temporary ident means
     * that it is possible to return to previous setting using removeTempIdent
     * method instead of decreaseIdent
     *
     * @param firstIdent is additional text appended to ident on the first line
     * @param regularIdent is additiona ltext added to ident starting from
     * second line
     * @return self to support chaining
     */
    CodeBuilder setTempIdent(String firstIdent, String regularIdent);

    /**
     * Adds temporary text to identation.
     * Temporary ident means that it is possible to return to previous setting
     * using removeTempIdent method instead of decreaseIdent
     *
     * @param text is string to be appended to ident
     * @return self to support chaining
     */
    CodeBuilder appendTempIdent(String text);

    /**
     * Appends temporary text to identation, using different ident on the first
     * line.
     * Often used with list of parameters and such, as first line is spaces
     * only, starting from second line, comma is used. Temporary ident means
     * that it is possible to return to previous setting using removeTempIdent
     * method instead of decreaseIdent
     *
     * @param firstIdent is additional text appended to ident on the first line
     * @param regularIdent is additiona ltext added to ident starting from
     * second line
     * @return self to support chaining
     */
    CodeBuilder appendTempIdent(String firstIdent, String regularIdent);

    /**
     * Increase ident length by defined number of characters and replace ident
     * with spaces.
     * Temporary ident means that it is possible to return to previous setting
     * using removeTempIdent method instead of decreaseIdent
     *
     * @param increaseBy is number of characters ident should be increased by
     * @return self to support chaining
     */
    CodeBuilder increaseTempIdent(int increaseBy);

    /**
     * Increase ident length by defined number of characters and replace ident
     * with specified text, left padded with spaces.
     * Temporary ident means that it is possible to return to previous setting
     * using removeTempIdent method instead of decreaseIdent
     *
     * @param ident new ident text, left padded ith spaces to required length
     * @param increaseBy is number of characters ident should be increased by
     * @return self to support chaining
     */
    CodeBuilder increaseTempIdent(String ident, int increaseBy);

    /**
     * Increase ident length by defined number of characters and replace ident
     * with specified text, left padded with spaces. First line will get
     * different ident. Temporary ident means that it is possible to return to
     * previous setting using removeTempIdent method instead of decreaseIdent
     *
     * @param firstIdent is ident text that will be used first line, after left
     * padding it with spaces to required length
     * @param regularIdent is ident text that will be used from second line on
     * @param increaseBy is number of characters ident should be increased by
     * @return self to support chaining
     */
    CodeBuilder increaseTempIdent(String firstIdent, String regularIdent,
                                         int increaseBy);

    /**
     * Increase temporary ident to build AND condition.
     * If already inside AND condition, does only increases temp ident level.
     * Otherwise, increases indent by 2 and sets ident to AND and first ident
     * to nothing
     *
     * @return self to support chaining
     */
    CodeBuilder increaseTempIdentAnd();

    /**
     * Increase temporary ident to build OR condition.
     * If already inside OR condition, does only increases temp ident level.
     * Otherwise, increases indent by 2 and sets ident to OR and first ident
     * to nothing
     *
     * @return self to support chaining
     */
    CodeBuilder increaseTempIdentOr();

    /**
     * Returns to previous ident.
     *
     * @return self to allow chaining.
     */
    CodeBuilder popIdent();

    /**
     * Method retrieves code, produced by CodeBuilder.
     *
     * @return code that has been built using this builder
     */
    String build();
}
