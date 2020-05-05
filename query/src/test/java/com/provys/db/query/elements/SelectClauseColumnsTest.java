package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SelectClauseColumnsTest {

  @Test
  void getColumnByAliasNoTest() {
    var alias = SimpleName.valueOf("alias");
    // prepare column 1 - no match
    var column1 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column1.getAlias()).thenReturn(null);
    // prepare column 2 - no match
    var column2 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column2.getAlias()).thenReturn(SimpleName.valueOf("other"));
    // prepare column 3 - no match
    var column3 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column3.getAlias()).thenReturn(SimpleName.valueOf("third"));
    // prepare value itself
    var value = new SelectClauseColumns(List.of(column1, column2, column3));
    // assert
    assertThat(value.getColumnByAlias(alias)).isNull();
  }

  @Test
  void getColumnByAlias1Test() {
    var alias = SimpleName.valueOf("alias");
    // prepare column 1 - no match
    var column1 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column1.getAlias()).thenReturn(null);
    // prepare column 2 - match
    var column2 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column2.getAlias()).thenReturn(SimpleName.valueOf("alias"));
    // prepare column 3 - no match
    var column3 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column3.getAlias()).thenReturn(SimpleName.valueOf("third"));
    // prepare value itself
    var value = new SelectClauseColumns(List.of(column1, column2, column3));
    // assert
    assertThat(value.getColumnByAlias(alias)).isEqualTo(column2);
  }

  @Test
  void getColumnByAlias3Test() {
    var alias = SimpleName.valueOf("alias");
    // prepare column 1 - match
    var column1 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column1.getAlias()).thenReturn(alias);
    // prepare column 2 - no match
    var column2 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column2.getAlias()).thenReturn(SimpleName.valueOf("other"));
    // prepare column 3 - match
    var column3 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column3.getAlias()).thenReturn(SimpleName.valueOf("alias"));
    // prepare value itself
    var value = new SelectClauseColumns(List.of(column1, column2, column3));
    // assert
    assertThatThrownBy(() -> value.getColumnByAlias(alias));
  }

  @Test
  void getColumnsTest() {
    // prepare columns
    var column1 = (SelectColumn<?>) mock(SelectColumn.class);
    var column2 = (SelectColumn<?>) mock(SelectColumn.class);
    var column3 = (SelectColumn<?>) mock(SelectColumn.class);
    // prepare value itself
    var value = new SelectClauseColumns(List.of(column1, column2, column3), null);
    // assert
    assertThat(value.getColumns()).containsExactlyInAnyOrder(column1, column2, column3);
  }

  @Test
  void getBindsTest() {
    // prepare column 1 - no binds
    var column1 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column1.getBinds()).thenReturn(Collections.emptyList());
    // prepare column 2 - binds bind1, bind2
    var bind1 = new BindVariable("name1", String.class, null);
    var bind2 = new BindVariable("name2", Integer.class, null);
    var column2 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column2.getBinds()).thenReturn(List.of(bind1, bind2));
    // prepare from column 3 - binds bind3, bind1
    var bind3 = new BindVariable("name3", String.class, "test value");
    var column3 = (SelectColumn<?>) mock(SelectColumn.class);
    when(column3.getBinds()).thenReturn(List.of(bind3, bind1));
    // prepare value itself
    var value = new SelectClauseColumns(List.of(column1, column2, column3), null);
    // assert
    assertThat(value.getBinds()).containsExactlyInAnyOrder(bind1, bind2, bind3);
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new SelectClauseColumns(List.of(
            new ColumnExpression<>(new Literal<>(5), SimpleName.valueOf("alias")))),
            "[{\"EXPRESSION\":{\"EXPRESSION\":{\"LITERAL\":{\"INTEGER\":5}},\"ALIAS\":\"alias\"}}]",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMNS><COLUMN><EXPRESSION>"
                + "<EXPRESSION><LITERAL><INTEGER>5</INTEGER></LITERAL></EXPRESSION>"
                + "<ALIAS>alias</ALIAS></EXPRESSION></COLUMN></COLUMNS>"}
        , new Object[]{new SelectClauseColumns(List.of(
            new ColumnExpression<>(new Literal<>("value"), null),
            new ColumnExpression<>(
                ExpressionBind.ofBindVariable(new BindVariable("name", String.class, null)),
                SimpleName.valueOf("alias2")))),
            "[{\"EXPRESSION\":{\"EXPRESSION\":{\"LITERAL\":{\"STRING\":\"value\"}}}},"
                + "{\"EXPRESSION\":{\"EXPRESSION\":{\"BIND\":"
                + "{\"NAME\":\"NAME\",\"TYPE\":\"STRING\"}},\"ALIAS\":\"alias2\"}}]",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLUMNS><COLUMN><EXPRESSION>"
                + "<EXPRESSION><LITERAL><STRING>value</STRING></LITERAL></EXPRESSION><ALIAS/>"
                + "</EXPRESSION></COLUMN><COLUMN><EXPRESSION><EXPRESSION><BIND><NAME>NAME</NAME>"
                + "<TYPE>STRING</TYPE><VALUE/></BIND></EXPRESSION><ALIAS>alias2</ALIAS>"
                + "</EXPRESSION></COLUMN></COLUMNS>"
        }
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SelectClauseColumns value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SelectClauseColumns value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SelectClauseColumns.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SelectClauseColumns value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SelectClauseColumns value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SelectClauseColumns.class))
        .isEqualTo(value);
  }
}
