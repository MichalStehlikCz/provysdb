package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.Function;
import com.provys.db.sql.SegmentedName;
import com.provys.db.sql.SimpleName;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SqlFromTableTest {

  @Test
  void getAliasTest() {
    var context = mock(SqlContext.class);
    var alias = SimpleName.valueOf("al");
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"), alias);
    assertThat(fromElem.getAlias()).isEqualTo(alias);
  }

  @Test
  void getAliasNoAliasTest() {
    var context = mock(SqlContext.class);
    var tableName = SimpleName.valueOf("tablename");
    var fromElem = new SqlFromTable(context, tableName, null);
    assertThat(fromElem.getAlias()).isEqualTo(tableName);
  }

  @Test
  void appendTest() {
    var context = mock(SqlContext.class);
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"),
        SimpleName.valueOf("al"));
    var builder = CodeBuilderFactory.getCodeBuilder();
    fromElem.append(builder);
    assertThat(builder.build()).isEqualTo("tablename al");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void appendNoAliasTest() {
    var context = mock(SqlContext.class);
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"), null);
    var builder = CodeBuilderFactory.getCodeBuilder();
    fromElem.append(builder);
    assertThat(builder.build()).isEqualTo("tablename");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{
            new SqlFromTable(SqlContextImpl.getNoDbInstance(), SimpleName.valueOf("brc_record_tb"),
                SimpleName.valueOf("rec")),
            "{\"TABLENAME\":\"brc_record_tb\",\"ALIAS\":\"rec\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FROMTABLE><TABLENAME>brc_record_tb</TABLENAME><ALIAS>rec</ALIAS></FROMTABLE>"}
        , new Object[]{new SqlFromTable(SqlContextImpl.getNoDbInstance(),
            SegmentedName.valueOf("brc.brc_record_tb"), null),
            "{\"TABLENAME\":\"brc.brc_record_tb\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FROMTABLE><TABLENAME>brc.brc_record_tb</TABLENAME><ALIAS/></FROMTABLE>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SqlFromTable value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SqlFromTable value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SqlFromTable.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SqlFromTable value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SqlFromTable value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SqlFromTable.class))
        .isEqualTo(value);
  }
}