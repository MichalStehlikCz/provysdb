package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class SqlLiteralBigIntegerHandlerTest {

  @Test
  void getLiteralTest() {
    var value = BigInteger.valueOf(1234567891234567858L);
    assertThat(SqlLiteralBigIntegerHandler.getInstance().getLiteral(value))
        .isEqualTo("1234567891234567858");
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralBigIntegerHandler.getInstance().getLiteral(null))
        .isEqualTo("TO_NUMBER(NULL)");
  }
}