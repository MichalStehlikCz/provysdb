package com.provys.provysdb.sqlparser.impl;

import com.provys.common.datatype.DtDate;
import com.provys.provysdb.sqlbuilder.impl.LiteralByte;
import com.provys.provysdb.sqlbuilder.impl.LiteralDate;
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
                , new Object[]{"a:=5;", new SqlParsedToken[]{
                        new ParsedIdentifier(1, 1, "a")
                        , new ParsedSymbol(1, 2, ":=")
                        , new ParsedLiteral<>(1, 4, LiteralByte.of((byte) 5))
                        , new ParsedSymbol(1, 5, ";")}}
                , new Object[]{"arc.call(p_A => :a, p_B => :b2);", new SqlParsedToken[]{
                        new ParsedIdentifier(1, 1, "arc")
                        , new ParsedSymbol(1, 4, ".")
                        , new ParsedIdentifier(1, 5, "call")
                        , new ParsedSymbol(1, 9, "(")
                        , new ParsedIdentifier(1, 10, "p_A")
                        , new ParsedSymbol(1, 14, "=>")
                        , new ParsedBind(1, 17, "a")
                        , new ParsedSymbol(1, 19, ",")
                        , new ParsedIdentifier(1, 21, "p_B")
                        , new ParsedSymbol(1, 25, "=>")
                        , new ParsedBind(1, 28, "b2")
                        , new ParsedSymbol(1, 31, ")")
                        , new ParsedSymbol(1, 32, ";")}}
                , new Object[]{"SELECT\n    date '2018-01-12'\nFROM\n    dual", new SqlParsedToken[]{
                        new ParsedIdentifier(1, 1, "SELECT")
                        , new ParsedLiteral<>(2, 5, LiteralDate.of(DtDate.of(2018, 1, 12)))
                        , new ParsedIdentifier(3, 1, "FROM")
                        , new ParsedIdentifier(4, 5, "dual")}}
        );
    }

    @ParameterizedTest
    @MethodSource
    void tokenizeTest(String source, SqlParsedToken[] result) {
        assertThat(new DefaultSqlTokenizer().tokenize(source)).containsExactly(result);
    }
}