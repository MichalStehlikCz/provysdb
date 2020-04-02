package com.provys.db.sqldb.queryold;

import static org.assertj.core.api.Assertions.*;

import com.provys.db.query.CodeBuilder;
import com.provys.db.query.SegmentedName;
import com.provys.db.query.SimpleName;
import com.provys.db.sqldb.codebuilder.CodeBuilder;
import com.provys.db.sqldb.codebuilder.CodeBuilderFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlExpressionColumnTest {

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new SqlExpressionColumn(SqlContextImpl.getNoDbInstance(), null,
            SimpleName.valueOf("column"), Integer.class, null),
            "column"}
        , new Object[]{new SqlExpressionColumn(SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column"), String.class),
            "scheme.table.column"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(SqlElement value, String result) {
    CodeBuilder builder = CodeBuilderFactory.getCodeBuilder();
    value.append(builder);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).isEmpty();
  }


}