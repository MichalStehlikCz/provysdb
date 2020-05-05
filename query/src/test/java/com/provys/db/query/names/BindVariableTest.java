package com.provys.db.query.names;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtDate;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
import java.io.Serializable;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class BindVariableTest {

  @Test
  void constructorTest() {
    var name = BindName.valueOf("name");
    var bind = new BindVariable(name, Integer.class, 5);
    assertThat(bind.getName()).isEqualTo(name);
    assertThat(bind.getType()).isSameAs(Integer.class);
    assertThat(bind.getValue()).isEqualTo(5);
  }

  @Test
  void constructor2Test() {
    var bind = new BindVariable("MujNazev", Double.class, null);
    assertThat(bind.getName()).isEqualTo(BindName.valueOf("mujnazev"));
    assertThat(bind.getType()).isSameAs(Double.class);
    assertThat(bind.getValue()).isNull();
  }

  @Test
  @SuppressWarnings("Immutable") // intentionally ignore immutable warning even though it IS broken
  void constructorFailTest() {
    assertThatThrownBy(() -> new BindVariable("nazev",
        Integer.class.asSubclass(Serializable.class), "value"))
        .hasMessageContaining("does not match type");
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new BindVariable("name1", String.class, null),
            "{\"NAME\":\"NAME1\",\"TYPE\":\"STRING\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BINDVARIABLE><NAME>NAME1</NAME>"
                + "<TYPE>STRING</TYPE><VALUE/></BINDVARIABLE>"}
        , new Object[]{new BindVariable("name2", String.class, "test value"),
            "{\"NAME\":\"NAME2\",\"TYPE\":\"STRING\",\"VALUE\":\"test value\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BINDVARIABLE><NAME>NAME2</NAME>"
                + "<TYPE>STRING</TYPE><VALUE>test value</VALUE></BINDVARIABLE>"}
        , new Object[]{new BindVariable("name3", Integer.class, 5),
            "{\"NAME\":\"NAME3\",\"TYPE\":\"INTEGER\",\"VALUE\":5}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BINDVARIABLE><NAME>NAME3</NAME>"
                + "<TYPE>INTEGER</TYPE><VALUE>5</VALUE></BINDVARIABLE>"}
        , new Object[]{new BindVariable("name4", DtDate.class, DtDate.of(2020, 2, 15)),
            "{\"NAME\":\"NAME4\",\"TYPE\":\"DATE\",\"VALUE\":\"2020-02-15\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BINDVARIABLE><NAME>NAME4</NAME>"
                + "<TYPE>DATE</TYPE><VALUE>2020-02-15</VALUE></BINDVARIABLE>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(BindVariable value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(BindVariable value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, BindVariable.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(BindVariable value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(BindVariable value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, BindVariable.class))
        .isEqualTo(value);
  }
}