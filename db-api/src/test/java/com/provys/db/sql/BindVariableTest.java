package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BindVariableTest {

  @Test
  void constructorTest() {
    var name = BindName.ofValue("name");
    var bind = new BindVariable(name, Integer.class, 5);
    assertThat(bind.getName()).isEqualTo(name);
    assertThat(bind.getType()).isSameAs(Integer.class);
    assertThat(bind.getValue()).isEqualTo(5);
  }

  @Test
  void constructor2Test() {
    var bind = new BindVariable("MujNazev", Double.class, null);
    assertThat(bind.getName()).isEqualTo(BindName.ofValue("mujnazev"));
    assertThat(bind.getType()).isSameAs(Double.class);
    assertThat(bind.getValue()).isNull();
  }

  @Test
  void constructorFailTest() {
    assertThatThrownBy(() -> new BindVariable("nazev", Integer.class, "value"))
        .hasMessageContaining("does not match type");
  }
}