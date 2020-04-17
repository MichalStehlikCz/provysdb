package com.provys.db.query.elements;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.names.SegmentedName;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class FromTableTest {
  @Test
  void getAliasTest() {
    var alias = SimpleName.valueOf("al");
    var fromElem = new FromTable(SimpleName.valueOf("tablename"), alias);
    assertThat(fromElem.getAlias()).isEqualTo(alias);
  }

  @Test
  void getAliasNoAliasTest() {
    var tableName = SimpleName.valueOf("tablename");
    var fromElem = new FromTable(tableName, null);
    assertThat(fromElem.getAlias()).isEqualTo(tableName);
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{
            new FromTable(SimpleName.valueOf("brc_record_tb"), SimpleName.valueOf("rec")),
            "{\"TABLENAME\":\"brc_record_tb\",\"ALIAS\":\"rec\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FROMTABLE><TABLENAME>brc_record_tb</TABLENAME><ALIAS>rec</ALIAS></FROMTABLE>"}
        , new Object[]{new FromTable(SegmentedName.valueOf("brc.brc_record_tb"), null),
            "{\"TABLENAME\":\"brc.brc_record_tb\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<FROMTABLE><TABLENAME>brc.brc_record_tb</TABLENAME><ALIAS/></FROMTABLE>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(FromTable value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(FromTable value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, FromTable.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(FromTable value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(FromTable value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, FromTable.class))
        .isEqualTo(value);
  }
}