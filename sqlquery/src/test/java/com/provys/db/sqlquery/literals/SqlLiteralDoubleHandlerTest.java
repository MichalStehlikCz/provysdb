package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SqlLiteralDoubleHandlerTest {

  @Test
  void getLiteralTest() {
    var value = 15486521.754;
    assertThat(SqlLiteralDoubleHandler.getInstance().getLiteral(value)).isEqualTo("15486521.754");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralDoubleHandler.getInstance().getLiteral(null)).isEqualTo("TO_NUMBER(NULL)");
  }
}