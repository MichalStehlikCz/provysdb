package com.provys.provysdb.sqlparser.impl;

import com.provys.common.datatype.DtDate;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.impl.BindNameImpl;
import com.provys.provysdb.sqlbuilder.impl.LiteralByte;
import com.provys.provysdb.sqlbuilder.impl.LiteralDate;
import com.provys.provysdb.sqlparser.SqlKeyword;
import com.provys.provysdb.sqlparser.SqlParsedToken;
import com.provys.provysdb.sqlparser.SqlSymbol;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;

class DefaultSqlTokenizerTest {

    @Nonnull
    @SuppressWarnings({"squid:S1192", // we do not care about duplicate strings in test data
            "squid:S3878"}) // we also cannot expand arrays as we need nested array
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
                        new ParsedKeyword(1, 1, SqlKeyword.SELECT)
                        , new ParsedSymbol(2, 5, SqlSymbol.STAR)
                        , new ParsedKeyword(3, 1, SqlKeyword.FROM)
                        , new ParsedIdentifier(4, 5, "ABC")
                        , new ParsedSymbol(4, 8, SqlSymbol.DOT)
                        , new ParsedIdentifier(4, 9, "\"Abc\"\"def\"")
                        , new ParsedSymbol(4, 19, SqlSymbol.PLUS)}}
                , new Object[]{"a:=5;", new SqlParsedToken[]{
                        new ParsedIdentifier(1, 1, "a")
                        , new ParsedSymbol(1, 2, SqlSymbol.ASSIGNMENT)
                        , new ParsedLiteral<>(1, 4, LiteralByte.of((byte) 5))
                        , new ParsedSymbol(1, 5, SqlSymbol.SEMICOLON)}}
                , new Object[]{"arc.call(p_A => :a, p_B => :b2);", new SqlParsedToken[]{
                        new ParsedIdentifier(1, 1, "arc")
                        , new ParsedSymbol(1, 4, SqlSymbol.DOT)
                        , new ParsedIdentifier(1, 5, "call")
                        , new ParsedSymbol(1, 9, SqlSymbol.OPENING_BRACKET)
                        , new ParsedIdentifier(1, 10, "p_A")
                        , new ParsedSymbol(1, 14, SqlSymbol.PARAM_VALUE)
                        , new ParsedBind(1, 17, "a")
                        , new ParsedSymbol(1, 19, SqlSymbol.COMMA)
                        , new ParsedIdentifier(1, 21, "p_B")
                        , new ParsedSymbol(1, 25, SqlSymbol.PARAM_VALUE)
                        , new ParsedBind(1, 28, "b2")
                        , new ParsedSymbol(1, 31, SqlSymbol.CLOSING_BRACKET)
                        , new ParsedSymbol(1, 32, SqlSymbol.SEMICOLON)}}
                , new Object[]{"SELECT\n    date '2018-01-12'\nFROM\n    dual", new SqlParsedToken[]{
                        new ParsedKeyword(1, 1, SqlKeyword.SELECT)
                        , new ParsedLiteral<>(2, 5, LiteralDate.of(DtDate.of(2018, 1, 12)))
                        , new ParsedKeyword(3, 1, SqlKeyword.FROM)
                        , new ParsedIdentifier(4, 5, "dual")}}
        );
    }

    @ParameterizedTest
    @MethodSource
    void tokenizeTest(String source, SqlParsedToken[] result) {
        assertThat(new DefaultSqlTokenizer().tokenize(source)).containsExactly(result);
    }

    @Nonnull
    @SuppressWarnings({"squid:S1192", // we do not care about duplicate strings in test data
            "squid:S3878"}) // we also cannot expand arrays as we need nested array
    static Stream<Object[]> getBindsTest() {
        return Stream.of(
                new Object[]{"  -- test\n --druhy", "-- test\n--druhy\n", new BindName[]{}}
                , new Object[]{"  -- test\n --druhy\n", "-- test\n--druhy\n", new BindName[]{}}
                , new Object[]{"  -- test\n/* viceradkovy\ncomment -- test*/\n--treti", "-- test\n" +
                        "/* viceradkovy\ncomment -- test*/ --treti\n", new BindName[]{}}
                , new Object[]{"SELECT\n    *\nFROM\n    abc.\"Abc\"\"def\"+", "SELECT * FROM abc.\"Abc\"\"def\"+",
                        new BindName[]{}}
                , new Object[]{"a:=5;", "a:=5;", new BindName[]{}}
                , new Object[]{"arc.call(p_A => :a, p_B => :b2);", "arc.call(p_a => ?, p_b => ?);",
                        new BindName[]{new BindNameImpl("a"),
                                new BindNameImpl("b2")}}
                , new Object[]{"SELECT\n    date '2018-01-12'\nFROM\n    dual", "SELECT DATE'2018-01-12' FROM dual",
                        new BindName[]{}}
        );
    }

    @ParameterizedTest
    @MethodSource
    void getBindsTest(String source, String parsed, BindName[] binds) {
        var builder = new DefaultSqlTokenizer().getBinds(source);
        assertThat(builder.build()).isEqualTo(parsed);
        assertThat(builder.getBinds()).containsExactly(binds);
    }
}