package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.common.exception.InternalException;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@SuppressWarnings("java:S1192") // duplicate string
class NamePathBaseTest {

  private static class TestNamePathBase extends NamePathBase {

    private final List<String> dbNames;

    TestNamePathBase(Collection<String> dbNames) {
      this.dbNames = List.copyOf(dbNames);
    }

    @Override
    public String getText() {
      throw new InternalException("Not implemented in this test class");
    }

    @Override
    public List<String> getDbNames() {
      return dbNames;
    }
  }

  static Stream<Object[]> matchTest() {
    return Stream.of(
        new Object[]{List.of("BRC", "BRC_RECORD_TB"), List.of("BRC", "BRC_RECORD_TB"), true}
        , new Object[]{List.of("BRC", "BRC_RECORD_TB"), List.of("BRC_RECORD_TB"), true}
        , new Object[]{List.of("BRC_RECORD_TB"), List.of("BRC", "BRC_RECORD_TB"), false}
        , new Object[]{List.of("BRC_RECORD_TB"), List.of("BRC_RECORD_TB"), true}
    );
  }

  @ParameterizedTest
  @MethodSource
  void matchTest(List<String> thisDbNames, List<String> otherDbNames, boolean result) {
    var object = new TestNamePathBase(thisDbNames);
    var other = mock(NamePath.class);
    when(other.getDbNames()).thenReturn(otherDbNames);
    assertThat(object.match(other)).isEqualTo(result);
  }
}