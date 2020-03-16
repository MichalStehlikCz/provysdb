package com.provys.db.sql;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class BindMapTest {

  @Test
  void getTest() {
    var bindVariable1 = new BindVariable(BindName.ofValue("name1"), Integer.class, null);
    var bindVariable2 = new BindVariable(BindName.ofValue("bind2"), Double.class, null);
    var map = new BindMap(List.of(bindVariable1, bindVariable2));
    assertThat(map.get(BindName.ofValue("name1"))).isEqualTo(bindVariable1);
    assertThat(map.get(BindName.ofValue("bind2"))).isEqualTo(bindVariable2);
    assertThatThrownBy(() -> map.get(BindName.ofValue("bind")))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void isSupersetOfTest() {
    var bindVariable1 = new BindVariable(BindName.ofValue("name1"), Integer.class, null);
    var bindVariable2 = new BindVariable(BindName.ofValue("bind2"), Double.class, null);
    var bindVariable2a = new BindVariable(BindName.ofValue("bind2"), Double.class, 5d);
    var bindVariable3 = new BindVariable(BindName.ofValue("bind3"), String.class, "a");
    var map1 = new BindMap(List.of(bindVariable1, bindVariable2, bindVariable3));
    var map2 =  new BindMap(List.of(bindVariable1, bindVariable2));
    var map3 = new BindMap(List.of(bindVariable1, bindVariable2a));
    var emptyMap = new BindMap(Collections.emptyMap());
    assertThat(map1.isSupersetOf(map1.getBindsByName())).isTrue();
    assertThat(map1.isSupersetOf(map2.getBindsByName())).isTrue();
    assertThat(map1.isSupersetOf(map3.getBindsByName())).isFalse();
    assertThat(map1.isSupersetOf(emptyMap.getBindsByName())).isTrue();
    assertThat(map2.isSupersetOf(map1.getBindsByName())).isFalse();
    assertThat(map2.isSupersetOf(map2.getBindsByName())).isTrue();
    assertThat(map2.isSupersetOf(map3.getBindsByName())).isFalse();
    assertThat(map2.isSupersetOf(emptyMap.getBindsByName())).isTrue();
    assertThat(map3.isSupersetOf(map1.getBindsByName())).isFalse();
    assertThat(map3.isSupersetOf(map2.getBindsByName())).isFalse();
    assertThat(map3.isSupersetOf(map3.getBindsByName())).isTrue();
    assertThat(map3.isSupersetOf(emptyMap.getBindsByName())).isTrue();
    assertThat(emptyMap.isSupersetOf(map1.getBindsByName())).isFalse();
    assertThat(emptyMap.isSupersetOf(map2.getBindsByName())).isFalse();
    assertThat(emptyMap.isSupersetOf(map3.getBindsByName())).isFalse();
    assertThat(emptyMap.isSupersetOf(Collections.emptyMap())).isTrue();
  }
}