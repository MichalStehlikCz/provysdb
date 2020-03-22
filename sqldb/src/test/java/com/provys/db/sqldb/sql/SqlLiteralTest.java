package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtUid;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.sql.SegmentedName;
import com.provys.db.sql.SimpleName;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlLiteralTest {

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new SqlLiteral(SqlContextImpl.getNoDbInstance(), "testString"),
            "{\"VALUE\":{\"type\":\"STRING\",\"value\":\"testString\"}}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERAL><VALUE><type>STRING</type><value>testString</value></VALUE></LITERAL>"}
        , new Object[]{new SqlLiteral(SqlContextImpl.getNoDbInstance(), 5),
            "{\"VALUE\":{\"type\":\"INTEGER\",\"value\":5}}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERAL><VALUE><type>INTEGER</type><value>5</value></VALUE></LITERAL>"}
        , new Object[]{new SqlLiteral(SqlContextImpl.getNoDbInstance(), DtUid.valueOf("123456")),
            "{\"VALUE\":{\"type\":\"UID\",\"value\":123456}}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<LITERAL><VALUE><type>UID</type><value>123456</value></VALUE></LITERAL>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SqlLiteral value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SqlLiteral value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SqlLiteral.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SqlLiteral value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SqlLiteral value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SqlLiteral.class))
        .isEqualTo(value);
  }
}