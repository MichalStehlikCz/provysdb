package com.provys.db.sqlquery.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.provys.db.query.elements.ExpressionColumn;
import com.provys.db.query.names.SegmentedName;
import com.provys.db.query.names.SimpleName;
import com.provys.db.sqlquery.codebuilder.CodeBuilderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ExpressionColumnSqlBuilderTest {

  @Test
  void getTypeTest() {
    assertThat(ExpressionColumnSqlBuilder.getInstance().getType()).isSameAs(ExpressionColumn.class);
  }

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new ExpressionColumn<>(null, SimpleName.valueOf("column"), Integer.class),
            "column"}
        , new Object[]{new ExpressionColumn<>(SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column"), String.class), "scheme.table.column"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(ExpressionColumn<?> value, String result) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    @SuppressWarnings("unchecked")
    SqlBuilder<StatementFactory, ?, ?> sqlBuilder = mock(SqlBuilder.class);
    when(sqlBuilder.getCodeBuilder()).thenReturn(builder);
    ExpressionColumnSqlBuilder.getInstance().append(sqlBuilder, value);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).isEmpty();
    assertThat(builder.getBindValues()).isEmpty();
  }
}