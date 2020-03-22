package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.xml.bind.annotation.XmlRootElement;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SegmentedNameTest {

  static Stream<@Nullable Object[]> valueOfTest() {
    return Stream.of(
        new Object[]{"sys.v$session", new String[]{"SYS", "V$SESSION"}}
        , new Object[]{"v$session", new String[]{"V$SESSION"}}
        , new Object[]{"schema.\"Test\"", new String[]{"SCHEMA", "Test"}}
    );
  }

  @ParameterizedTest
  @MethodSource
  void valueOfTest(String text, String[] result) {
    assertThat(SegmentedName.valueOf(text).getDbNames()).containsExactly(result);
  }

  static Stream<@Nullable Object[]> valueOfFailTest() {
    return Stream.of(
        new Object[]{""}
        , new Object[]{"A..B"}
        , new Object[]{".A"}
        , new Object[]{"B."}
    );
  }

  @ParameterizedTest
  @MethodSource
  void valueOfFailTest(String text) {
    assertThatThrownBy(() -> SegmentedName.valueOf(text));
  }

  @Test
  void isSimpleTrueTest() {
    assertThat(SegmentedName.ofSegments(List.of(SimpleName.valueOf("test"))).isSimple()).isTrue();
  }

  @Test
  void isSimpleFalseTest() {
    assertThat(
        SegmentedName.ofSegments(List.of(SimpleName.valueOf("test"), SimpleName.valueOf("test")))
            .isSimple()).isFalse();
  }

  @Test
  void getSegmentsTest() {
    var value = SimpleName.valueOf("testName");
    assertThat(SegmentedName.ofSegments(List.of(value, value)).getSegments())
        .containsExactly(value, value);
  }

  @XmlRootElement(name = "SegmentedNameElement")
  public static final class SegmentedNameElement {

    private @MonotonicNonNull SegmentedName value;

    /**
     * Value of field value.
     *
     * @return value of field value
     */
    public @Nullable SegmentedName getValue() {
      return value;
    }

    /**
     * Set value of field value1.
     *
     * @param value is new value to be set
     */
    public SegmentedNameTest.SegmentedNameElement setValue(SegmentedName value) {
      this.value = value;
      return this;
    }

    @Override
    public boolean equals(@Nullable Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      SegmentedNameTest.SegmentedNameElement that = (SegmentedNameTest.SegmentedNameElement) o;
      return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
      return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
      return "SegmentedNameElement{" +
          "value1=" + value +
          '}';
    }
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{
            new SegmentedNameTest.SegmentedNameElement().setValue(SegmentedName.valueOf("name")),
            "{\"value\":\"name\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SegmentedNameElement><value>name</value>"
                + "</SegmentedNameElement>"}
        , new Object[]{
            new SegmentedNameTest.SegmentedNameElement().setValue(
                SegmentedName.valueOf("scheme.name")),
            "{\"value\":\"scheme.name\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SegmentedNameElement>"
                + "<value>scheme.name</value></SegmentedNameElement>"}
        , new Object[]{new SegmentedNameTest.SegmentedNameElement(), "{}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SegmentedNameElement><value/>"
                + "</SegmentedNameElement>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SegmentedNameTest.SegmentedNameElement value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SegmentedNameTest.SegmentedNameElement value, String json,
      String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper()
        .readValue(json, SegmentedNameTest.SegmentedNameElement.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SegmentedNameTest.SegmentedNameElement value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SegmentedNameTest.SegmentedNameElement value, String json, String xml)
      throws IOException {
    assertThat(
        JacksonMappers.getXmlMapper().readValue(xml, SegmentedNameTest.SegmentedNameElement.class))
        .isEqualTo(value);
  }
}