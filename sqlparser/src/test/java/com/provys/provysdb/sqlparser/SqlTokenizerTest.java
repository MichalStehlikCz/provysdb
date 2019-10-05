package com.provys.provysdb.sqlparser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nonnull;
import java.security.InvalidParameterException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class SqlTokenizerTest {

    @Nonnull
    @SuppressWarnings("squid:S1192") // we do not care about duplicate strings in test data
    static Stream<Object[]> tokenizeTest() {
        return Stream.of(
                new Object[]{"  -- test\n --druhy", new SqlToken[]{
                        new SqlSingleLineComment(1, 3, " test")
                        , new SqlSingleLineComment(2, 2, "druhy")}}
                , new Object[]{"  -- test\n --druhy\n", new SqlToken[]{
                        new SqlSingleLineComment(1, 3, " test")
                        , new SqlSingleLineComment(2, 2, "druhy")}}
        );
    }

    @ParameterizedTest
    @MethodSource
    void tokenizeTest(String source, SqlToken[] result) {
        assertThat(new SqlTokenizer().tokenize(source)).containsExactly(result);
    }
}