package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SqlLiteralStringHandlerTest {

  @Test
  void getLiteralTest() {
    var value = "kelwafrqietrjwe'jsehf\"rufghewa\ndaokjfo";
    assertThat(SqlLiteralStringHandler.getInstance().getLiteral(value))
        .isEqualTo("'kelwafrqietrjwe''jsehf\"rufghewa'||CHR(10)||'daokjfo'");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralStringHandler.getInstance().getLiteral(null)).isEqualTo("NULL");
  }
}