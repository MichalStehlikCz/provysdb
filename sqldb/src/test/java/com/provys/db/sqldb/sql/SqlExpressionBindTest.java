package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.sql.BindName;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.BindWithPos;
import com.provys.db.sql.SegmentedName;
import com.provys.db.sql.SimpleName;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlExpressionBindTest {

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new SqlExpressionBind(SqlContextImpl.getNoDbInstance(),
            new BindVariable("name1", Integer.class, 5)),
            "?", new BindWithPos[]{
            new BindWithPos(BindName.valueOf("name1"), Integer.class, List.of(1))},
            Map.of(BindName.valueOf("name1"), (Object) 5)}
        , new Object[]{new SqlExpressionBind(SqlContextImpl.getNoDbInstance(),
            new BindVariable("name2", String.class, null)),
            "?",
            new BindWithPos[]{new BindWithPos(BindName.valueOf("name2"), String.class, List.of(1))},
            Collections.emptyMap()}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(SqlElement value, String result, BindWithPos[] bindsWithPos,
      Map<BindName, @Nullable Object> bindValues) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    value.append(builder);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).containsExactlyInAnyOrder(bindsWithPos);
    assertThat(builder.getBindValues()).containsExactlyInAnyOrderEntriesOf(bindValues);
  }

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{new SqlExpressionBind(SqlContextImpl.getNoDbInstance(),
            new BindVariable("name1", Integer.class, 5)),
            "{\"NAME\":\"NAME1\",\"TYPE\":\"INTEGER\",\"VALUE\":5}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BINDEXPRESSION><NAME>NAME1</NAME>"
                + "<TYPE>INTEGER</TYPE><VALUE>5</VALUE></BINDEXPRESSION>"}
        , new Object[]{new SqlExpressionBind(SqlContextImpl.getNoDbInstance(),
            new BindVariable("name2", String.class, null)),
            "{\"NAME\":\"NAME2\",\"TYPE\":\"STRING\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BINDEXPRESSION><NAME>NAME2</NAME>"
                + "<TYPE>STRING</TYPE><VALUE/></BINDEXPRESSION>"}
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(SqlExpressionBind value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(SqlExpressionBind value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, SqlExpressionBind.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(SqlExpressionBind value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(SqlExpressionBind value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, SqlExpressionBind.class))
        .isEqualTo(value);
  }
}