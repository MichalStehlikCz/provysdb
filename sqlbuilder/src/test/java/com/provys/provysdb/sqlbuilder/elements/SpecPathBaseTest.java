package com.provys.provysdb.sqlbuilder.elements;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.provysdb.sqlbuilder.SpecPath;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SpecPathBaseTest {

  private static class TestSpecPathBase extends SpecPathBase {
    private final String text;
    private final List<String> dbNames;

    TestSpecPathBase(String text, Collection<String> dbNames) {
      this.text = text;
      this.dbNames = List.copyOf(dbNames);
    }

    @Override
    public String getText() {
      return text;
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
    var object = new TestSpecPathBase("test", thisDbNames);
    var other = mock(SpecPath.class);
    when(other.getDbNames()).thenReturn(otherDbNames);
    assertThat(object.match(other)).isEqualTo(result);
  }
}