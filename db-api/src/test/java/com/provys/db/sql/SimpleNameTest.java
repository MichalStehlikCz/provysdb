package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SimpleNameTest {

  static Stream<Object[]> ofValueTest() {
    return Stream.of(
        new Object[]{"v$session", "V$SESSION", "v$session"}
        , new Object[]{"A1", "A1", "a1"}
        , new Object[]{"\"Name\"", "Name", "\"Name\""}
        , new Object[]{"\"N$1ME\"", "N$1ME", "n$1me"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void ofValueTest(String name, String dbName, String text) {
    var simpleName = SimpleName.ofValue(name);
    assertThat(simpleName.getDbName()).isEqualTo(dbName);
    assertThat(simpleName.getText()).isEqualTo(text);
  }

  static Stream<@Nullable Object[]> ofValueFailTest() {
    return Stream.of(
        new Object[]{"", "Blank"}
        , new Object[]{"1A", "ordinary"}
        , new Object[]{"\"\n\"", "newline"}
        , new Object[]{"\"A\n\"", "delimited"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void ofValueFailTest(String name, String message) {
    assertThatThrownBy(() -> SimpleName.ofValue(name)).hasMessageContainingAll(name, message);
  }
}