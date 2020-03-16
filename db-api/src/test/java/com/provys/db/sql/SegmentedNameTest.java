package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SegmentedNameTest {

  @Test
  void ofValueTest() {
    assertThat(SegmentedName.ofValue("sys.v$session").getDbNames()).containsExactly("SYS", "V$SESSION");
  }

  static Stream<@Nullable Object[]> ofValueFailTest() {
    return Stream.of(
        new Object[]{""}
        , new Object[]{"A..B"}
        , new Object[]{".A"}
        , new Object[]{"B."}
    );
  }

  @ParameterizedTest
  @MethodSource
  void ofValueFailTest(String text) {
    assertThatThrownBy(() -> SegmentedName.ofValue(text));
  }
}