package com.provys.db.query.elements;

import static com.provys.db.query.functions.ConditionalOperator.COND_EQ_NONNULL;
import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.datatype.DtUid;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.functions.ConditionalOperator;
import com.provys.db.query.names.SegmentedName;
import com.provys.db.query.names.SimpleName;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class FromSelectTest {

  static Stream<Object[]> jacksonTest() {
    return Stream.of(
        new Object[]{
            new FromSelect(new SelectImpl(
                new SelectClauseColumns(List.of(
                    new ColumnExpression<>(
                        new ExpressionColumn<>(DtUid.class, SimpleName.valueOf("rec"),
                            SimpleName.valueOf("record_id")), SimpleName.valueOf("record_id")))),
                new DefaultFromClause(List.of(
                    new FromTable(SegmentedName.valueOf("brc.brc_record_tb"),
                        SimpleName.valueOf("rec")))),
                null, null, null),
                SimpleName.valueOf("alias")),
            "{\"SELECT\":{\"SELECT\":{\"SELECT\":{\"COLUMNS\":{\"COLUMN\":[{\"EXPRESSION\":"
                + "{\"EXPRESSION\":{\"COLUMN\":{\"TYPE\":\"UID\",\"TABLE\":\"rec\","
                + "\"COLUMN\":\"record_id\"}},\"ALIAS\":\"record_id\"}}]}},\"FROM\":"
                + "[{\"FROMTABLE\":{\"TABLENAME\":\"brc.brc_record_tb\",\"ALIAS\":\"rec\"}}]}},"
                + "\"ALIAS\":\"alias\"}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FROMSELECT><SELECT><SELECT><SELECT>"
                + "<COLUMNS><COLUMN><EXPRESSION><EXPRESSION><COLUMN><TYPE>UID</TYPE>"
                + "<TABLE>rec</TABLE><COLUMN>record_id</COLUMN></COLUMN></EXPRESSION>"
                + "<ALIAS>record_id</ALIAS></EXPRESSION></COLUMN></COLUMNS></SELECT><FROM><ELEM>"
                + "<FROMTABLE><TABLENAME>brc.brc_record_tb</TABLENAME><ALIAS>rec</ALIAS>"
                + "</FROMTABLE></ELEM></FROM><WHERE/><PARENTCONTEXT/></SELECT></SELECT>"
                + "<ALIAS>alias</ALIAS></FROMSELECT>"
        }
        , new Object[]{new FromSelect(new SelectImpl(
            new SelectClauseColumns(List.of(
                new ColumnExpression<>(
                    new ExpressionColumn<>(DtUid.class, null, SimpleName.valueOf("prog_id")),
                    null))),
            new DefaultFromClause(List.of(
                new FromTable(SegmentedName.valueOf("brc.brc_prog_tb"), SimpleName.valueOf("prog")),
                new FromTable(SimpleName.valueOf("brc_series_tb"), SimpleName.valueOf("series")))),
            new ConditionOperation(COND_EQ_NONNULL,
                List.of(new ExpressionColumn<>(DtUid.class, SimpleName.valueOf("prog"),
                        SimpleName.valueOf("series_id")),
                    new ExpressionColumn<>(DtUid.class, SimpleName.valueOf("series"),
                        SimpleName.valueOf("series_id")))), null, null),
            null),
            "{\"SELECT\":{\"SELECT\":{\"SELECT\":{\"COLUMNS\":{\"COLUMN\":[{\"EXPRESSION\":"
                + "{\"EXPRESSION\":{\"COLUMN\":{\"TYPE\":\"UID\",\"COLUMN\":\"prog_id\"}}}}]}},"
                + "\"FROM\":[{\"FROMTABLE\":{\"TABLENAME\":\"brc.brc_prog_tb\",\"ALIAS\":\"prog\"}}"
                + ",{\"FROMTABLE\":{\"TABLENAME\":\"brc_series_tb\",\"ALIAS\":\"series\"}}],"
                + "\"WHERE\":{\"CONDOP\":{\"OPERATOR\":\"COND_EQ_NONNULL\",\"ARGUMENTS\":["
                + "{\"COLUMN\":{\"TYPE\":\"UID\",\"TABLE\":\"prog\",\"COLUMN\":\"series_id\"}},"
                + "{\"COLUMN\":{\"TYPE\":\"UID\",\"TABLE\":\"series\",\"COLUMN\":\"series_id\"}}]}"
                + "}}}}",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><FROMSELECT><SELECT><SELECT><SELECT>"
                + "<COLUMNS><COLUMN><EXPRESSION><EXPRESSION><COLUMN><TYPE>UID</TYPE><TABLE/>"
                + "<COLUMN>prog_id</COLUMN></COLUMN></EXPRESSION><ALIAS/></EXPRESSION></COLUMN>"
                + "</COLUMNS></SELECT><FROM><ELEM><FROMTABLE><TABLENAME>brc.brc_prog_tb</TABLENAME>"
                + "<ALIAS>prog</ALIAS></FROMTABLE></ELEM><ELEM><FROMTABLE>"
                + "<TABLENAME>brc_series_tb</TABLENAME><ALIAS>series</ALIAS></FROMTABLE></ELEM>"
                + "</FROM><WHERE><CONDOP><OPERATOR>COND_EQ_NONNULL</OPERATOR><ARGUMENTS><ARGUMENT>"
                + "<COLUMN><TYPE>UID</TYPE><TABLE>prog</TABLE><COLUMN>series_id</COLUMN></COLUMN>"
                + "</ARGUMENT><ARGUMENT><COLUMN><TYPE>UID</TYPE><TABLE>series</TABLE>"
                + "<COLUMN>series_id</COLUMN></COLUMN></ARGUMENT></ARGUMENTS></CONDOP></WHERE>"
                + "<PARENTCONTEXT/></SELECT></SELECT><ALIAS/></FROMSELECT>"
        }
    );
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToJsonTest(FromSelect value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getJsonMapper().writeValueAsString(value))
        .isEqualTo(json);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromJsonTest(FromSelect value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getJsonMapper().readValue(json, FromSelect.class))
        .isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void serializeToXmlTest(FromSelect value, String json, String xml)
      throws JsonProcessingException {
    assertThat(JacksonMappers.getXmlMapper().writeValueAsString(value))
        .isEqualTo(xml);
  }

  @ParameterizedTest
  @MethodSource("jacksonTest")
  void deserializeFromXmlTest(FromSelect value, String json, String xml)
      throws IOException {
    assertThat(JacksonMappers.getXmlMapper().readValue(xml, FromSelect.class))
        .isEqualTo(value);
  }
}