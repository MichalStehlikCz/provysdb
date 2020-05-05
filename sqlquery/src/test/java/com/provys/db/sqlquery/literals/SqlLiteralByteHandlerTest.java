package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SqlLiteralByteHandlerTest {

  @Test
  void getLiteralTest() {
    var value = (byte) 45;
    assertThat(SqlLiteralByteHandler.getInstance().getLiteral(value)).isEqualTo("45");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralByteHandler.getInstance().getLiteral(null)).isEqualTo("TO_NUMBER(NULL)");
  }
}