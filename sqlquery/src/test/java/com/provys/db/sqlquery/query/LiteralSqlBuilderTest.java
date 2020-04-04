package com.provys.db.sqlquery.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.provys.db.query.elements.ExpressionColumn;
import com.provys.db.query.elements.Literal;
import com.provys.db.query.elements.Select;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LiteralSqlBuilderTest {

  @Test
  void getTypeTest() {
    assertThat(ExpressionColumnSqlBuilder.getInstance().getType()).isSameAs(Literal.class);
  }

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new Literal<>(5), "5"}
        , new Object[]{new Literal<>("Ahoj"), "'Ahoj'"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(ExpressionColumn<?> value, String result) {
    var select = mock(Select.class);
    var sqlBuilder = DefaultStatementFactory.getNoDbFactory().getSqlBuilder(select);
    var builder = sqlBuilder.getCodeBuilder();
    ExpressionColumnSqlBuilder.getInstance().append(sqlBuilder, value);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).isEmpty();
    assertThat(builder.getBindValues()).isEmpty();
  }
}
