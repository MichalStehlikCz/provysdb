package com.provys.db.crypt;

import static org.assertj.core.api.Assertions.*;

import com.provys.common.crypt.DtEncryptedString;
import com.provys.db.defaultdb.types.SqlTypeMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class CryptSqlTypeModuleTest {

  /**
   * Verify that type adapters are properly registered in default type map.
   */
  @Test
  void registerTest() {
    var sqlTypeMap = SqlTypeMap.getDefault();
    assertThat(sqlTypeMap.getAdapter(DtEncryptedString.class))
        .isEqualTo(SqlTypeAdapterEncryptedString.getInstance());
  }
}