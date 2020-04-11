package com.provys.db.querybuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.provys.db.query.elements.Expression;
import org.junit.jupiter.api.Test;

public class DecoratingExpressionBuilderTest {
  @Test
  void getTypeTest() {
    @SuppressWarnings("unchecked")
    var expression = (Expression<Integer>) mock(Expression.class);
    when(expression.getType()).thenReturn(Integer.class);
    var value = new DecoratingExpressionBuilder<>(expression);
    assertThat(value.getType()).isEqualTo(Integer.class);
  }

  @Test
  void buildTest() {
    var expression = (Expression<?>) mock(Expression.class);
    var value = new DecoratingExpressionBuilder<>(expression);
    assertThat(value.build()).isEqualTo(expression);
  }
}
