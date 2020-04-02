package com.provys.db.sqldb.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.provys.db.query.elements.ExpressionBind;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.sqldb.codebuilder.CodeBuilderFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class ExpressionBindBuilderTest {

  @Test
  void getTypeTest() {
    assertThat(ExpressionBindBuilder.getInstance().getType()).isSameAs(ExpressionBind.class);
  }

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{ExpressionBind.ofBindVariable(new BindVariable("name1", Integer.class, 5)),
            "?", new BindWithPos[]{
            new BindWithPos(BindName.valueOf("name1"), Integer.class, List.of(1))},
            Map.of(BindName.valueOf("name1"), (Object) 5)}
        , new Object[]{ExpressionBind.ofBindVariable(new BindVariable("name2", String.class, null)),
            "?",
            new BindWithPos[]{new BindWithPos(BindName.valueOf("name2"), String.class, List.of(1))},
            Collections.emptyMap()}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(ExpressionBind<?> value, String result, BindWithPos[] bindsWithPos,
      Map<BindName, @Nullable Object> bindValues) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    @SuppressWarnings("unchecked")
    SqlBuilder<StatementFactory, ?, ?> sqlBuilder = mock(SqlBuilder.class);
    when(sqlBuilder.getCodeBuilder()).thenReturn(builder);
    ExpressionBindBuilder.getInstance().append(sqlBuilder, value);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).containsExactlyInAnyOrder(bindsWithPos);
    assertThat(builder.getBindValues()).containsExactlyInAnyOrderEntriesOf(bindValues);
  }
}