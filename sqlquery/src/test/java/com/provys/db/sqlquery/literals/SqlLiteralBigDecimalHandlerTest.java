package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SqlLiteralBigDecimalHandlerTest {

  @Test
  void getLiteralTest() {
    var value = BigDecimal.valueOf(1234567891234567858L);
    assertThat(SqlLiteralBigDecimalHandler.getInstance().getLiteral(value))
        .isEqualTo("1234567891234567858");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralBigDecimalHandler.getInstance().getLiteral(null))
        .isEqualTo("TO_NUMBER(NULL)");
  }
}