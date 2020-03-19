package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtUid;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
import java.util.Objects;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.assertj.core.api.Fail;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SimpleNameTest {

  static Stream<Object[]> valueOfTest() {
    return Stream.of(
        new Object[]{"v$session", "V$SESSION", "v$session"}
        , new Object[]{"A1", "A1", "a1"}
        , new Object[]{"\"Name\"", "Name", "\"Name\""}
        , new Object[]{"\"N$1ME\"", "N$1ME", "n$1me"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void valueOfTest(String name, String dbName, String text) {
    var simpleName = SimpleName.valueOf(name);
    assertThat(simpleName.getDbName()).isEqualTo(dbName);
    assertThat(simpleName.getText()).isEqualTo(text);
  }

  static Stream<@Nullable Object[]> valueOfFailTest() {
    return Stream.of(
        new Object[]{"", "Blank"}
        , new Object[]{"1A", "ordinary"}
        , new Object[]{"\"\n\"", "newline"}
        , new Object[]{"\"A\n\"", "delimited"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void valueOfFailTest(String name, String message) {
    assertThatThrownBy(() -> SimpleName.valueOf(name)).hasMessageContainingAll(name, message);
  }

  @XmlRootElement(name = "SimpleNameElement")
  public static final class SimpleNameElement {

    private @MonotonicNonNull SimpleName value;

    /**
     * Value of field value.
     *
     * @return value of field value
     */
    public @Nullable SimpleName getValue() {
      return value;
    }

    /**
     * Set value of field value1.
     *
     * @param value is new value to be set
     */
    public SimpleNameElement setValue(SimpleName value) {
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
      SimpleNameElement that = (SimpleNameElement) o;
      return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
      return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
      return "SimpleNameElement{" +
          "value1=" + value +
          '}';
    }
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new SimpleNameElement().setValue(SimpleName.valueOf("name")),
            "{\"value\":\"name\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SimpleNameElement><value>name</value>"
                + "</SimpleNameElement>"}
        , new Object[]{new SimpleNameElement(), "{}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SimpleNameElement><value/>"
                + "</SimpleNameElement>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SimpleNameElement value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SimpleNameElement value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SimpleNameElement.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SimpleNameElement value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SimpleNameElement value, String json, String xml) throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SimpleNameElement.class))
        .isEqualTo(value);
  }
}