package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.SegmentedName;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class DefaultFromClauseTest {

  @Test
  void getElementByAliasNoTest() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(FromElement.class);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - no match
    var fromElem2 = mock(FromElement.class);
    when(fromElem2.match(alias)).thenReturn(false);
    // prepare from element 3 - no match
    var fromElem3 = mock(FromElement.class);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getElementByAlias(alias)).isNull();
  }

  @Test
  void getElementByAlias1Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(FromElement.class);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - match
    var fromElem2 = mock(FromElement.class);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(FromElement.class);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getElementByAlias(alias)).isEqualTo(fromElem2);
  }

  @Test
  void getElementByAlias2Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - match
    var fromElem1 = mock(FromElement.class);
    when(fromElem1.match(alias)).thenReturn(true);
    // prepare from element 2 - match
    var fromElem2 = mock(FromElement.class);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(FromElement.class);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThatThrownBy(() -> value.getElementByAlias(alias));
  }

  @Test
  void isUniqueNoTest() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(FromElement.class);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - no match
    var fromElem2 = mock(FromElement.class);
    when(fromElem2.match(alias)).thenReturn(false);
    // prepare from element 3 - no match
    var fromElem3 = mock(FromElement.class);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.isUnique(alias)).isTrue();
  }

  @Test
  void isUnique1Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(FromElement.class);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - match
    var fromElem2 = mock(FromElement.class);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(FromElement.class);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.isUnique(alias)).isTrue();
  }

  @Test
  void isUnique2Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - match
    var fromElem1 = mock(FromElement.class);
    when(fromElem1.match(alias)).thenReturn(true);
    // prepare from element 2 - match
    var fromElem2 = mock(FromElement.class);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(FromElement.class);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.isUnique(alias)).isFalse();
  }

  @Test
  void getElementsTest() {
    // prepare from elements
    var fromElem1 = mock(FromElement.class);
    var fromElem2 = mock(FromElement.class);
    var fromElem3 = mock(FromElement.class);
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getElements()).containsExactlyInAnyOrder(fromElem1, fromElem2, fromElem3);
  }

  @Test
  void getBindsTest() {
    // prepare from element 1 - no binds
    var fromElem1 = mock(FromElement.class);
    when(fromElem1.getBinds()).thenReturn(Collections.emptyList());
    // prepare from element 2 - binds bind1, bind2
    var bind1 = new BindVariable("name1", String.class, null);
    var bind2 = new BindVariable("name2", Integer.class, null);
    var fromElem2 = mock(FromElement.class);
    when(fromElem2.getBinds()).thenReturn(List.of(bind1, bind2));
    // prepare from element 3 - binds bind3, bind1
    var bind3 = new BindVariable("name3", String.class, "test value");
    var fromElem3 = mock(FromElement.class);
    when(fromElem3.getBinds()).thenReturn(List.of(bind3, bind1));
    // prepare value itself
    var value = new DefaultFromClause(List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getBinds()).containsExactlyInAnyOrder(bind1, bind2, bind3);
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{
            new DefaultFromClause(List.of(
                new FromTable(SimpleName.valueOf("brc_record_tb"), SimpleName.valueOf("rec"))),
                null),
            "[{\"FROMTABLE\":{\"TABLENAME\":\"brc_record_tb\",\"ALIAS\":\"rec\"}}]",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FROM><ELEM>"
                + "<FROMTABLE><TABLENAME>brc_record_tb</TABLENAME><ALIAS>rec</ALIAS></FROMTABLE>"
                + "</ELEM></FROM>"}
        , new Object[]{new DefaultFromClause(List.of(
            new FromTable(SimpleName.valueOf("brc_record_tb"), SimpleName.valueOf("rec")),
            new FromTable(SegmentedName.valueOf("brc.brc_record_tb"), null)), null),
            "[{\"FROMTABLE\":{\"TABLENAME\":\"brc_record_tb\",\"ALIAS\":\"rec\"}},"
                + "{\"FROMTABLE\":{\"TABLENAME\":\"brc.brc_record_tb\"}}]",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FROM><ELEM>"
                + "<FROMTABLE><TABLENAME>brc_record_tb</TABLENAME><ALIAS>rec</ALIAS></FROMTABLE>"
                + "</ELEM><ELEM>"
                + "<FROMTABLE><TABLENAME>brc.brc_record_tb</TABLENAME><ALIAS/></FROMTABLE>"
                + "</ELEM></FROM>"
        }
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(DefaultFromClause value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(DefaultFromClause value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, DefaultFromClause.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(DefaultFromClause value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(DefaultFromClause value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, DefaultFromClause.class))
        .isEqualTo(value);
  }
}