package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.BindName;
import com.provys.db.query.BindVariable;
import com.provys.db.query.BindWithPos;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Function;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class SqlFunctionTest {

  public static final BindWithPos[] BIND_WITH_POS = new BindWithPos[]{};

  static Stream<Object[]> appendTest() {
    return Stream.of(
        new Object[]{new SqlFunction(SqlContextImpl.getNoDbInstance(), Function.STRING_CHR,
            new SqlExpression[]{new SqlLiteral(5)}, null, null),
            "CHR(5)", BIND_WITH_POS}
        , new Object[]{new SqlFunction(SqlContextImpl.getNoDbInstance(), Function.STRING_CONCAT,
            new SqlExpression[]{new SqlLiteral("first"), new SqlLiteral("second"),
                new SqlExpressionBind(new BindVariable("bind1", String.class, null))},
            null, null), "'first'||'second'||?",
            new BindWithPos[]{new BindWithPos(BindName.valueOf("bind1"), String.class,
                List.of(1))}}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendTest(SqlElement value, String result, BindWithPos[] bindsWithPos) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    value.append(builder);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).containsExactlyInAnyOrder(bindsWithPos);
  }
}