package com.provys.provysdb.sqlparser.impl;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.StringParser;
import com.provys.common.exception.InternalException;
import com.provys.common.exception.RegularException;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.impl.*;
import com.provys.provysdb.sqlparser.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static com.provys.provysdb.sqlparser.SpaceMode.*;

/**
 * Tokenizer is used to parse supplied text into tokens
 */
public class DefaultSqlTokenizer implements SqlTokenizer {

    private static final Logger LOG = LogManager.getLogger(DefaultSqlTokenizer.class);

    private final int maxTokens;

    public DefaultSqlTokenizer() {
        maxTokens = 1000000;
    }

    public DefaultSqlTokenizer(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    @Override
    public List<SqlParsedToken> tokenize(String source) {
        try (Scanner scanner = new Scanner(source)) {
            return tokenize(scanner);
        }
    }

    @Override
    public List<SqlParsedToken> tokenize(Scanner scanner) {
        var sqlScanner = new SqlScanner(scanner);
        var tokens = new ArrayList<SqlParsedToken>(50);
        while (sqlScanner.hasNext()) {
            tokens.add(sqlScanner.next());
            if (tokens.size() > maxTokens) {
                throw new InternalException(LOG, "Maximal number of parsed tokens exceeded");
            }
        }
        return tokens;
    }

    @Override
    public CodeBuilder getBinds(String source) {
        try (Scanner scanner = new Scanner(source)) {
            return getBinds(scanner);
        }
    }

    @Override
    public CodeBuilder getBinds(Scanner scanner) {
        var builder = new CodeBuilderImpl();
        var sqlScanner = new SqlScanner(scanner);
        SpaceMode afterPrev = null;
        while (sqlScanner.hasNext()) {
            var token = sqlScanner.next();
            if ((afterPrev != null) &&
                    (((token.spaceBefore() == FORCE) && (afterPrev != FORCE_NONE)) ||
                            ((afterPrev == FORCE) && (token.spaceBefore() != FORCE_NONE)) ||
                            ((token.spaceBefore() == NORMAL) && (afterPrev == NORMAL)))) {
                builder.append(' ');
            }
            token.addSql(builder);
            afterPrev = token.spaceAfter();
        }
        return builder;
    }

    private static class SqlScanner implements Iterator<SqlParsedToken> {

        /**
         * Check if supplied character is whitespace
         */
        private static boolean isWhiteSpace(char character) {
            return (character == ' ') || (character == '\t') || (character == '\n');
        }

        @Nonnull
        private final Scanner scanner;
        @Nullable
        private StringParser currentLine;
        private int line;

        private SqlScanner(Scanner scanner) {
            this.scanner = scanner;
            if (scanner.hasNextLine()) {
                this.currentLine = new StringParser(scanner.nextLine());
            } else {
                this.currentLine = null;
            }
            line = 1; // we want to index lines from 1 in output
        }

        /**
         * Read next character.
         * If placed at the end of line, returns newline character and sets endOfLine flag.
         */
        private char nextChar() {
            if (currentLine == null) {
                throw new NoSuchElementException("Cannot read next character - end of file reached");
            }
            if (currentLine.hasNext()) {
                return currentLine.next();
            }
            if (scanner.hasNextLine()) {
                currentLine = new StringParser(scanner.nextLine());
                line++;
            } else {
                currentLine = null;
            }
            return '\n';
        }

        /**
         * Get next character, but do not navigate to it.
         */
        private char peekChar() {
            if (currentLine == null) {
                throw new NoSuchElementException("Cannot check next character - end of file reached");
            }
            if (currentLine.hasNext()) {
                return currentLine.peek();
            }
            return '\n';
        }

        /**
         * Check if there is next character
         */
        private boolean hasNextChar() {
            return (currentLine != null);
        }

        /**
         * @return position on current line; position in tokenizer is one indexed, as when displayed to users, it makes
         * more sense, moreover it is what SQL users are used to
         */
        private int getPos() {
            if (currentLine == null) {
                throw new IllegalStateException("Cannot return position - end of file reached");
            }
            return currentLine.getPos() + 1; // we want to display positions from 1
        }

        /**
         * Skip whitespace and navigate to start of next token
         *
         * @return true if next token was found and false if end of file was reached
         */
        private boolean skipWhiteSpace() {
            while (hasNextChar() && isWhiteSpace(peekChar())) {
                nextChar();
            }
            return hasNextChar();
        }

        /**
         * Check if current position is before specified text. Do not move current position.
         *
         * @return true if position is before specified text, false otherwise
         */
        private boolean isOnText(String text) {
            if (currentLine == null) {
                return false;
            }
            return currentLine.isOnText(text);
        }

        /**
         * Check if current position is before specified text. Skip given text if it was.
         *
         * @return true if position was before specified text, false otherwise
         */
        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        private boolean onText(String text) {
            if (currentLine == null) {
                return false;
            }
            return currentLine.onText(text);
        }

        /**
         * Check if current position is before specified text, case ignored. Do not move current position.
         *
         * @return true if position is before specified text, false otherwise
         */
        private boolean isOnTextIgnoreCase(String text) {
            if (currentLine == null) {
                return false;
            }
            return currentLine.isOnTextIgnoreCase(text);
        }

        /**
         * Check if current position is before specified text, case ignored. Skip given text if it was.
         *
         * @return true if position was before specified text, false otherwise
         */
        private boolean onTextIgnoreCase(String text) {
            if (currentLine == null) {
                return false;
            }
            return currentLine.onTextIgnoreCase(text);
        }

        /**
         * Read the rest of the line
         *
         * @return rest of current line, position wil be placed on end of line
         */
        @Nonnull
        private String readLine() {
            if (currentLine == null) {
                throw new NoSuchElementException("Cannot read line - end of file reached");
            }
            return currentLine.readString();
        }

        @Override
        public boolean hasNext() {
            return skipWhiteSpace();
        }

        /**
         * Invoked when current position is on --
         *
         * @return token representing single line comment read
         */
        @Nonnull
        private SqlParsedToken readSingleLineComment() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read single line comment at the end of file");
            }
            var pos = getPos();
            if (!onText("--")) {
                throw new IllegalStateException("Read single line comment should only be called on --");
            }
            return new ParsedSingleLineComment(line, pos, readLine());
        }

        /**
         * Invoked when current position is on start of multi-line comment
         *
         * @return token representing multi line comment
         */
        @Nonnull
        private SqlParsedToken readMultiLineComment() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read multiline comment at the end of file");
            }
            var pos = getPos();
            var startLine = line;
            if (!onText("/*")) {
                throw new IllegalStateException("Read multiline comment should only be called on /*");
            }
            var comment = new StringBuilder();
            boolean isStar = false;
            while (hasNextChar()) {
                char nextChar = nextChar();
                if (isStar) {
                    if (nextChar == '/') {
                        // end of comment reached
                        return new ParsedMultiLineComment(startLine, pos, comment.toString());
                    } else {
                        comment.append('*');
                        isStar = false;
                    }
                }
                if (nextChar == '*') {
                    isStar = true;
                } else {
                    comment.append(nextChar);
                }
            }
            throw new InternalException(LOG, "Unclosed multiline comment - end of file reached");
        }

        /**
         * Invoken when next character corresponds to one of symbols
         *
         * @return token representing symbol
         */
        @Nonnull
        private SqlParsedToken readSymbol() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read symbol at the end of file");
            }
            var pos = getPos();
            String firstChar = Character.toString(nextChar());
            if (hasNextChar()) {
                String twoChars = firstChar + peekChar();
                if (SqlSymbol.getBySymbol(twoChars).isPresent()) {
                    nextChar();
                    return new ParsedSymbol(line, pos, SqlSymbol.getBySymbol(twoChars).get());
                }
            }
            return new ParsedSymbol(line, pos, SqlSymbol.getBySymbol(firstChar)
                    .orElseThrow(() -> new InternalException(LOG, "Invalid character '" + firstChar +
                            "' found parsing SQL on line " + line + ", position " + pos)));
        }

        /**
         * Reads value of date literal. Note that unlike most other methods, this one is invoked AFTER reading DATE from
         * input
         *
         * @param pos is position of start of date literal (text DATE)
         * @return new date literal
         */
        private SqlParsedToken readDateLiteral(int pos) {
            skipWhiteSpace();
            if (nextChar() != '\'') {
                throw new RegularException(LOG, "SQLPARSER_MISSING_APOS_IN_DATE_LITERAL","' expected in date literal");
            }
            var startLine = line;
            var text = new StringBuilder();
            while (peekChar() != '\'') {
                text.append(nextChar());
            }
            nextChar();
            return new ParsedLiteral<>(startLine, pos, LiteralDate.of(DtDate.parseIso(text.toString())));
        }

        @Nonnull
        private SqlParsedToken readLetter() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read letter at the end of file");
            }
            var pos = getPos();
            var nameBuilder = new StringBuilder();
            while (hasNextChar() && (
                    ((peekChar() >= 'A') && (peekChar() <= 'Z')) ||
                            ((peekChar() >= 'a') && (peekChar() <= 'z')) ||
                            ((peekChar() >= '0') && (peekChar() <= '9')) ||
                            (peekChar() == '_') || (peekChar() == '$') ||
                            (peekChar() == '#'))) {
                 nameBuilder.append(nextChar());
            }
            var name = nameBuilder.toString();
            if (name.equalsIgnoreCase("DATE")) {
                return readDateLiteral(pos);
            }
            SqlKeyword keyword;
            try {
                keyword = SqlKeyword.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new ParsedIdentifier(line, pos, nameBuilder.toString());
            }
            return new ParsedKeyword(line, pos, keyword);
        }

        @Nonnull
        private SqlParsedToken readDelimitedIdentifier() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read name at the end of file");
            }
            var pos = getPos();
            if (nextChar() != '"') {
                throw new IllegalStateException("Delimited identifier must start with \"");
            }
            var name = new StringBuilder().append('"');
            while (hasNextChar()) {
                if (peekChar() == '\n') {
                    throw new RegularException(LOG, "SQLPARSER_UNFINISHED_DELIMITED_TOKEN",
                            "End of line encountered reading delimited token at line <<LINE>>, position <<POS>>",
                            Map.of("LINE", Integer.toString(line), "POS", Integer.toString(pos)));
                }
                if (peekChar() == '"') {
                    name.append(nextChar());
                    if (peekChar() != '"') {
                        return new ParsedIdentifier(line, pos, name.toString());
                    }
                    // two double quotation marks are evaluated as single one
                }
                name.append(nextChar());
            }
            throw new RegularException(LOG, "SQLPARSER_UNFINISHED_DELIMITED_TOKEN",
                    "End of file encountered reading delimited token at line <<LINE>>, position <<POS>>",
                    Map.of("LINE", Integer.toString(line), "POS", Integer.toString(pos)));
        }

        @Nonnull
        private SqlParsedToken readBind() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read name at the end of file");
            }
            var pos = getPos();
            if (nextChar() != ':') {
                throw new IllegalStateException("Bind variable must start with :");
            }
            var nameBuilder = new StringBuilder();
            while (hasNextChar() && (
                    ((peekChar() >= 'A') && (peekChar() <= 'Z')) ||
                            ((peekChar() >= 'a') && (peekChar() <= 'z')) ||
                            ((peekChar() >= '0') && (peekChar() <= '9')) ||
                            (peekChar() == '_'))) {
                nameBuilder.append(nextChar());
            }
            return new ParsedBind(line, pos, nameBuilder.toString());
        }

        @Nonnull
        private SqlParsedToken readStringLiteral() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read literal at the end of file");
            }
            var pos = getPos();
            if (nextChar() != '\'') {
                throw new IllegalStateException("String literal must start with '");
            }
            var value = new StringBuilder();
            while (hasNextChar()) {
                if (peekChar() == '\'') {
                    nextChar();
                    if (peekChar() != '\'') {
                        return new ParsedLiteral<>(line, pos, LiteralVarchar.of(value.toString()));
                    }
                    // two quotation marks are evaluated as single one
                }
                value.append(nextChar());
            }
            throw new RegularException(LOG, "SQLPARSER_UNFINISHED_STRING_LITERAL",
                    "End of file encountered reading string literal at line <<LINE>>, position <<POS>>",
                    Map.of("LINE", Integer.toString(line), "POS", Integer.toString(pos)));
        }

        @Nonnull
        private SqlParsedToken createNumericLiteral(int pos, String literal, boolean dotEncountered) {
            if (dotEncountered) {
                if (literal.length() <= 16) {
                    return new ParsedLiteral<>(line, pos, LiteralDouble.of(Double.parseDouble(literal)));
                } else {
                    return new ParsedLiteral<>(line, pos, LiteralBigDecimal.of(new BigDecimal(literal)));
                }
            } else {
                if (literal.length() <= 2) {
                    return new ParsedLiteral<>(line, pos, LiteralByte.of(Byte.parseByte(literal)));
                } else if (literal.length() <= 4) {
                    return new ParsedLiteral<>(line, pos, LiteralShort.of(Short.parseShort(literal)));
                } else if (literal.length() <= 9) {
                    return new ParsedLiteral<>(line, pos, LiteralInt.of(Integer.parseInt(literal)));
                } else {
                    return new ParsedLiteral<>(line, pos, LiteralBigInteger.of(new BigInteger(literal)));
                }
            }
        }

        @Nonnull
        private SqlParsedToken readNumericLiteral() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read literal at the end of file");
            }
            var pos = getPos();
            var literalBuilder = new StringBuilder();
            boolean dotEncountered = false;
            while (((peekChar() >= '0') && (peekChar() <= '9')) || (peekChar() == '.')) {
                if (peekChar() == '.') {
                    if (dotEncountered) {
                        throw new RegularException(LOG, "SQLPARSER_DOUBLE_DOT_IN_NUMERIC_LITERAL",
                                "Two dots encountered in numeric literal (line <<LINE>>, pos <<POS>>)",
                                Map.of("LINE", Integer.toString(line), "POS", Integer.toString(pos)));
                    } else {
                        dotEncountered = true;
                    }
                }
                literalBuilder.append(nextChar());
            }
            return createNumericLiteral(pos, literalBuilder.toString(), dotEncountered);
        }

        @Nonnull
        private SqlParsedToken readMinus() {
            if (isOnText("--")) {
                return readSingleLineComment();
            } else {
                return readSymbol();
            }
        }

        @Nonnull
        private SqlParsedToken readSlash() {
            if (isOnText("/*")) {
                return readMultiLineComment();
            } else {
                return readSymbol();
            }
        }

        @Nonnull
        private SqlParsedToken readDot() {
            if (isOnText(".0") || isOnText(".1") || isOnText(".2") || isOnText(".3") || isOnText(".4") ||
                    isOnText(".5") || isOnText(".6") || isOnText(".7") || isOnText(".8") || isOnText(".9")) {
                return readNumericLiteral();
            } else {
                return readSymbol();
            }

        }

        @Nonnull
        private SqlParsedToken readColon() {
            if (isOnText(":=")) {
                return readSymbol();
            } else {
                return readBind();
            }
        }

        @Override
        public SqlParsedToken next() {
            if (!skipWhiteSpace() || (currentLine == null)) { // condition on currentLine is not necessary as
                // skipWhiteSpace would return false, but static code analysis is unable to consider it and would
                // produce warning
                throw new NoSuchElementException("Cannot read Sql token - end of code reached");
            }
            if (((peekChar() >= 'a') && (peekChar() <= 'z')) || ((peekChar() >= 'A') && (peekChar() <= 'Z'))) {
                return readLetter();
            } else {
                switch (peekChar()) {
                    case '-':
                        return readMinus();
                    case '/':
                        return readSlash();
                    case '.':
                        return readDot();
                    case ':':
                        return readColon();
                    case '"':
                        return readDelimitedIdentifier();
                    case '\'':
                        return readStringLiteral();
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        return readNumericLiteral();
                    default:
                        // all other characters should be tried as symbols
                        return readSymbol();
                }
            }
        }
    }
}
