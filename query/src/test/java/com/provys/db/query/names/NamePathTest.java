package com.provys.db.query.names;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class NamePathTest {

  @XmlRootElement(name = "NamePathElement")
  public static final class NamePathElement {

    private @MonotonicNonNull NamePath value;

    /**
     * Value of field value.
     *
     * @return value of field value
     */
    public @Nullable NamePath getValue() {
      return value;
    }

    /**
     * Set value of field value1.
     *
     * @param value is new value to be set
     */
    public NamePathTest.NamePathElement setValue(NamePath value) {
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
      NamePathTest.NamePathElement that = (NamePathTest.NamePathElement) o;
      return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
      return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
      return "NamePathElement{" +
          "value1=" + value +
          '}';
    }
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new NamePathTest.NamePathElement().setValue(SimpleName.valueOf("name")),
            "{\"value\":\"name\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><NamePathElement><value>name</value>"
                + "</NamePathElement>"}
        , new Object[]{
            new NamePathTest.NamePathElement().setValue(SegmentedName.valueOf("scheme.name")),
            "{\"value\":\"scheme.name\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><NamePathElement><value>scheme.name</value>"
                + "</NamePathElement>"}
        , new Object[]{new NamePathTest.NamePathElement(), "{}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><NamePathElement><value/>"
                + "</NamePathElement>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(NamePathTest.NamePathElement value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(NamePathTest.NamePathElement value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, NamePathTest.NamePathElement.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(NamePathTest.NamePathElement value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(NamePathTest.NamePathElement value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, NamePathTest.NamePathElement.class))
        .isEqualTo(value);
  }
}