package com.provys.db.sqlquery.literals;

import static org.assertj.core.api.Assertions.*;

import com.provys.common.datatype.DtUid;
import org.junit.jupiter.api.Test;

class SqlLiteralDtUidHandlerTest {

  @Test
  void getLiteralTest() {
    var uid = "123456789123456789123";
    var value = DtUid.valueOf(uid);
    assertThat(SqlLiteralDtUidHandler.getInstance().getLiteral(value)).isEqualTo(uid);
  }

  @Test
  void getLiteralNullTest() {
    assertThat(SqlLiteralDtUidHandler.getInstance().getLiteral(null)).isEqualTo("TO_NUMBER(NULL)");
  }
}