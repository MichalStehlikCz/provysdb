package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.sql.BindVariable;
import com.provys.db.sql.Element;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BindVariableCollectorTest {

  @Test
  void addTest() {
    var collector = new BindVariableCollector();
    assertThat(collector.getBinds()).isEmpty();
    assertThat(collector.getBindsByName()).isEmpty();
    var bind1 = new BindVariable("name1", Integer.class, 5);
    var bind2 = new BindVariable("name2", Double.class, null);
    collector.add(List.of(bind1, bind2, bind1));
    assertThat(collector.getBinds()).containsExactlyInAnyOrder(bind1, bind2);
    assertThat(collector.getBindsByName())
        .containsExactlyInAnyOrderEntriesOf(Map.of(bind1.getName(), bind1, bind2.getName(), bind2));
    var bind3 = new BindVariable("name3", String.class, "test");
    var element = mock(Element.class);
    when(element.getBinds()).thenReturn(List.of(bind3, bind2));
    collector.add(element);
    assertThat(collector.getBinds()).containsExactlyInAnyOrder(bind1, bind2, bind3);
    assertThat(collector.getBindsByName())
        .containsExactlyInAnyOrderEntriesOf(
            Map.of(bind1.getName(), bind1, bind2.getName(), bind2, bind3.getName(), bind3));
    var bind4 = new BindVariable("name4", String.class, "test");
    collector.add(bind4);
    assertThat(collector.getBinds()).containsExactlyInAnyOrder(bind1, bind2, bind3, bind4);
    assertThat(collector.getBindsByName())
        .containsExactlyInAnyOrderEntriesOf(
            Map.of(bind1.getName(), bind1, bind2.getName(), bind2, bind3.getName(), bind3,
                bind4.getName(), bind4));
    collector.add(Collections.emptyList());
    assertThat(collector.getBinds()).containsExactlyInAnyOrder(bind1, bind2, bind3, bind4);
    assertThat(collector.getBindsByName())
        .containsExactlyInAnyOrderEntriesOf(
            Map.of(bind1.getName(), bind1, bind2.getName(), bind2, bind3.getName(), bind3,
                bind4.getName(), bind4));
  }

  @Test
  void addFailTest() {
    var name1 = "name1";
    var bind1 = new BindVariable(name1, Integer.class, 5);
    var name2 = "name2";
    var bind2 = new BindVariable(name2, Double.class, null);
    var collector = new BindVariableCollector(List.of(bind1, bind2, bind1));
    var bind1Conflict = new BindVariable(name1, Integer.class, 6);
    assertThatThrownBy(() -> collector.add(bind1Conflict));
    assertThat(collector.getBinds()).containsExactlyInAnyOrder(bind1, bind2);
    assertThat(collector.getBindsByName())
        .containsExactlyInAnyOrderEntriesOf(Map.of(bind1.getName(), bind1, bind2.getName(), bind2));
  }
}