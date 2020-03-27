package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtDate;
import com.provys.common.jackson.JacksonMappers;
import java.io.IOException;
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
  void constructorFailTest() {
    assertThatThrownBy(() -> new BindVariable("nazev", Integer.class, "value"))
        .hasMessageContaining("does not match type");
  }
}