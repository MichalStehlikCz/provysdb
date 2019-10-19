package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlparser.SqlParsedToken;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;

class DefaultSqlTokenizerTest {

    @Nonnull
    @SuppressWarnings("squid:S1192") // we do not care about duplicate strings in test data
    static Stream<Object[]> tokenizeTest() {
        return Stream.of(
                new Object[]{"  -- test\n --druhy", new SqlParsedToken[]{
                        new ParsedSingleLineComment(1, 3, " test")
                        , new ParsedSingleLineComment(2, 2, "druhy")}}
                , new Object[]{"  -- test\n --druhy\n", new SqlParsedToken[]{
                        new ParsedSingleLineComment(1, 3, " test")
                        , new ParsedSingleLineComment(2, 2, "druhy")}}
                , new Object[]{"  -- test\n/* viceradkovy\ncomment -- test*/\n--treti", new SqlParsedToken[]{
                        new ParsedSingleLineComment(1, 3, " test")
                        , new ParsedMultiLineComment(2, 1, " viceradkovy\ncomment -- test")
                        , new ParsedSingleLineComment(4, 1, "treti")}}
                , new Object[]{"SELECT\n    *\nFROM\n    abc.\"Abc\"\"def\"+", new SqlParsedToken[]{
                        new ParsedIdentifier(1, 1, "SELECT")
                        , new ParsedSymbol(2, 5, "*")
                        , new ParsedIdentifier(3, 1, "FROM")
                        , new ParsedIdentifier(4, 5, "ABC")
                        , new ParsedSymbol(4, 8, ".")
                        , new ParsedIdentifier(4, 9, "\"Abc\"\"def\"")
                        , new ParsedSymbol(4, 19, "+")}}
        );
    }

    @ParameterizedTest
    @MethodSource
    void tokenizeTest(String source, SqlParsedToken[] result) {
        assertThat(new DefaultSqlTokenizer().tokenize(source)).containsExactly(result);
    }
}