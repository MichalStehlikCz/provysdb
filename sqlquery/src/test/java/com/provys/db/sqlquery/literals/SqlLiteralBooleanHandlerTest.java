package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SqlLiteralBooleanHandlerTest {

  @Test
  void getLiteralTrueTest() {
    assertThat(SqlLiteralBooleanHandler.getInstance().getLiteral(true)).isEqualTo("'Y'");
  }

  @Test
  void getLiteralFalseTest() {
    assertThat(SqlLiteralBooleanHandler.getInstance().getLiteral(false)).isEqualTo("'N'");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralBooleanHandler.getInstance().getLiteral(null)).isEqualTo("NULL");
  }
}