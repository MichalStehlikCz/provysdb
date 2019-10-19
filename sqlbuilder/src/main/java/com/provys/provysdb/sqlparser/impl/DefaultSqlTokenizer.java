package com.provys.provysdb.sqlparser.impl;

import com.provys.common.datatype.StringParser;
import com.provys.common.exception.InternalException;
import com.provys.common.exception.RegularException;
import com.provys.provysdb.sqlparser.SqlParsedToken;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Tokenizer is used to parse supplied text into tokens
 */
public class DefaultSqlTokenizer implements SqlTokenizer {

    private static final Logger LOG = LogManager.getLogger(DefaultSqlTokenizer.class);

    private final int maxTokens;

    DefaultSqlTokenizer() {
        maxTokens = 1000000;
    }

    DefaultSqlTokenizer(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    @Override
    public Collection<SqlParsedToken> tokenize(String source) {
        try (Scanner scanner = new Scanner(source)) {
            return tokenize(scanner);
        }
    }

    @Override
    public Collection<SqlParsedToken> tokenize(Scanner scanner) {
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
                if (ParsedSymbol.SYMBOLS.contains(twoChars)) {
                    return new ParsedSymbol(line, pos, twoChars);
                }
            }
            if (ParsedSymbol.SYMBOLS.contains(firstChar)) {
                return new ParsedSymbol(line, pos, firstChar);
            }
            throw new InternalException(LOG, "Invalid character '" + firstChar + "' found parsing SQL on line " + line
                    + ", position " + pos);
        }

        @Nonnull
        private SqlParsedToken readOrdinaryIdentifier() {
            if (!hasNextChar()) { // caller ensures this method is not used on end of file
                throw new IllegalStateException("Cannot read name at the end of file");
            }
            var pos = getPos();
            var name = new StringBuilder();
            while (hasNextChar() && (
                    ((peekChar() >= 'A') && (peekChar() <= 'Z')) ||
                            ((peekChar() >= 'a') && (peekChar() <= 'z')) ||
                            ((peekChar() >= '0') && (peekChar() <= '9')) ||
                            (peekChar() == '_') || (peekChar() == '$') ||
                            (peekChar() == '#'))) {
                 name.append(nextChar());
            }
            return new ParsedIdentifier(line, pos, name.toString());
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
                        return new ParsedVarchar(line, pos, value.toString());
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

        @Override
        public SqlParsedToken next() {
            if (!skipWhiteSpace() || (currentLine == null)) { // condition on currentLine is not necessary as
                // skipWhiteSpace would return false, but static code analysis is unable to consider it and would
                // produce warning
                throw new NoSuchElementException("Cannot read Sql token - end of code reached");
            }
            if (((peekChar() >= 'a') && (peekChar() <= 'z')) || ((peekChar() >= 'A') && (peekChar() <= 'Z'))) {
                return readOrdinaryIdentifier();
            } else {
                switch (peekChar()) {
                    case '-':
                        return readMinus();
                    case '/':
                        return readSlash();
                    case '"':
                        return readDelimitedIdentifier();
                    case '\'':
                        return readStringLiteral();
                    default:
                        // all other characters should be tried as symbols
                        return readSymbol();
                }
            }
        }
    }


    /*

    PROCEDURE mpi_ReadDateLiteral
    IS
    l_Pos PLS_INTEGER;
    BEGIN
    l_Pos:=INSTR(l_Line, '''', 6);
    IF (l_Pos=0) THEN
      KER_Exception_EP.mp_Raise('Unfinished date literal on line '||l_Line_i);
    END IF;
    l_Token:=KER_GenToken_TO.mf_Create('LITERAL', 'DATE', SUBSTR(l_Line, 1, l_Pos));
    l_Line:=SUBSTR(l_Line, l_Pos+1);
    END;

    PROCEDURE mpi_ReadNumericLiteral
    IS
    l_Pos PLS_INTEGER :=1;
    l_Dot BOOLEAN :=FALSE; /* indicates we already found . /
    BEGIN
    WHILE (SUBSTR(l_Line, l_Pos, 1) BETWEEN '0' AND '9') OR (SUBSTR(l_Line, l_Pos, 1)='.') LOOP
    IF (SUBSTR(l_Line, l_Pos, 1)='.') THEN
    IF l_Dot THEN
          KER_Exception_EP.mp_Raise('Cannot have . in number literal twice');
    END IF;
    l_Dot:=TRUE;
    END IF;
    l_Pos:=l_Pos+1;
    END LOOP;
    l_Token:=KER_GenToken_TO.mf_Create('LITERAL', 'NUMBER', SUBSTR(l_Line, 1, l_Pos-1));
    l_Line:=SUBSTR(l_Line, l_Pos);
    END;

    PROCEDURE mpi_ReadName
    IS
    l_Pos PLS_INTEGER :=1;
    BEGIN
    WHILE (SUBSTR(l_Line, l_Pos, 1) BETWEEN 'A' AND 'Z')
    OR  (SUBSTR(l_Line, l_Pos, 1) BETWEEN 'a' AND 'z')
    OR  (SUBSTR(l_Line, l_Pos, 1) BETWEEN '0' AND '9')
    OR  (SUBSTR(l_Line, l_Pos, 1) IN ('_', '$', '.', '%', '#'))
    LOOP
    l_Pos:=l_Pos+1;
    END LOOP;
    l_Token:=KER_GenToken_TO.mf_Create('NAME', SUBSTR(l_Line, 1, l_Pos-1));
    l_Line:=SUBSTR(l_Line, l_Pos);
    END;

    PROCEDURE mpi_ReadAssignment
    IS
            BEGIN
    l_Token:=KER_GenToken_TO.mf_Create('SYMBOL', ':=', ':=');
    l_Line:=SUBSTR(l_Line, 3);
    END;

    PROCEDURE mpi_ReadParamRef
    IS
            BEGIN
    l_Token:=KER_GenToken_TO.mf_Create('SYMBOL', '=>', '=>');
    l_Line:=SUBSTR(l_Line, 3);
    END;

    PROCEDURE mpi_ReadSymbol
    IS
            BEGIN
    l_Token:=KER_GenToken_TO.mf_Create('SYMBOL', SUBSTR(l_Line, 1, 1), SUBSTR(l_Line, 1, 1));
    l_Line:=SUBSTR(l_Line, 2);
    END;
    BEGIN
    ot_Token:=KER_GenToken_TT();
    WHILE (l_Line_i<=pt_SrcLine.COUNT) LOOP
    /* read line, right trim it (white space on start / end of line is ignored) /
    l_Line:=RTRIM(pt_SrcLine(l_Line_i), ' '||CHR(9)||CHR(10)||CHR(13));
    LOOP
    /* left trim after each step /
    l_Line:=LTRIM(l_Line, ' '||CHR(9));
    EXIT WHEN (l_Line IS NULL);
    IF (l_Line LIKE '--%') THEN
    /* single line comment /
    mpi_ReadSingleLineComment;
    ELSIF (l_Line LIKE '/*%') THEN
    /* Multi-line comment /
    mpi_ReadMultiLineComment;
    ELSIF (l_Line LIKE '''%') THEN
    /* String literal (without special escaping) /
    mpi_ReadStringLiteral;
    ELSIF (UPPER(l_Line) LIKE 'Q''%') THEN
    /* String literal with escaping /
        KER_Exception_EP.mp_Raise('Escaped strings not supported at the moment');
    ELSIF (UPPER(l_Line) LIKE 'DATE''%') THEN
            /* Date literal /
            mpi_ReadDateLiteral;
    ELSIF (SUBSTR(l_Line, 1, 1) BETWEEN '0' AND '9') OR (SUBSTR(l_Line, 1, 1)='.') THEN
            /* numeric literal; at the moment, we only support simple literals without exponent /
            mpi_ReadNumericLiteral;
    ELSIF (SUBSTR(l_Line, 1, 1) BETWEEN 'A' AND 'Z') OR (SUBSTR(l_Line, 1, 1) BETWEEN 'a' AND 'z') THEN
            /* name /
            mpi_ReadName;
    ELSIF (l_Line LIKE ':=%') THEN
    /* default value assignment /
    mpi_ReadAssignment;
    ELSIF (l_Line LIKE '=>%') THEN
    /* named parameter /
    mpi_ReadParamRef;
    ELSIF (SUBSTR(l_Line, 1, 1) IN ('(', ')', ',', '+', '-', '/', '*', ';', '.', '%', '=', '>', '<')) THEN
            /* characters acting as token on their own /
                        mpi_ReadSymbol;
    ELSE
        KER_Exception_EP.mp_Raise('Unrecognised token on line '||l_Line_i||': "'||l_Line||'"');
    END IF;
    ot_Token.EXTEND;
    ot_Token(ot_Token.COUNT):=l_Token;
    IF (ot_Token.COUNT>SELF.MaxTokens) THEN
        KER_Exception_EP.mp_Raise('Cannot parse package this big');
    END IF;
    END LOOP;
    l_Line_i:=l_Line_i+1;
    END LOOP;
    END;

    END;
*/
}
