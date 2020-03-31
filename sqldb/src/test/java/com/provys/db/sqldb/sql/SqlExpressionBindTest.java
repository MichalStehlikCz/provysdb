package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.BindName;
import com.provys.db.query.BindVariable;
import com.provys.db.query.BindWithPos;
import com.provys.db.query.SegmentedName;
import com.provys.db.query.SimpleName;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlExpressionBindTest {

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new SqlExpressionBind(SqlContextImpl.getNoDbInstance(),
            new BindVariable("name1", Integer.class, 5)),
            "?", new BindWithPos[]{
            new BindWithPos(BindName.valueOf("name1"), Integer.class, List.of(1))},
            Map.of(BindName.valueOf("name1"), (Object) 5)}
        , new Object[]{new SqlExpressionBind(SqlContextImpl.getNoDbInstance(),
            new BindVariable("name2", String.class, null)),
            "?",
            new BindWithPos[]{new BindWithPos(BindName.valueOf("name2"), String.class, List.of(1))},
            Collections.emptyMap()}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(SqlElement value, String result, BindWithPos[] bindsWithPos,
      Map<BindName, @Nullable Object> bindValues) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    value.append(builder);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).containsExactlyInAnyOrder(bindsWithPos);
    assertThat(builder.getBindValues()).containsExactlyInAnyOrderEntriesOf(bindValues);
  }
}