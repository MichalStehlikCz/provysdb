package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SqlLiteralIntegerHandlerTest {

  @Test
  void getLiteralTest() {
    var value = -4987645;
    assertThat(SqlLiteralIntegerHandler.getInstance().getLiteral(value)).isEqualTo("-4987645");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralIntegerHandler.getInstance().getLiteral(null))
        .isEqualTo("TO_NUMBER(NULL)");
  }
}