package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.BindVariable;
import com.provys.db.query.Function;
import com.provys.db.query.SegmentedName;
import com.provys.db.query.SimpleName;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SqlFromTableTest {
  @Test
  void appendTest() {
    var context = mock(SqlContext.class);
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"),
        SimpleName.valueOf("al"));
    var builder = CodeBuilderFactory.getCodeBuilder();
    fromElem.append(builder);
    assertThat(builder.build()).isEqualTo("tablename al");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }

  @Test
  void appendNoAliasTest() {
    var context = mock(SqlContext.class);
    var fromElem = new SqlFromTable(context, SimpleName.valueOf("tablename"), null);
    var builder = CodeBuilderFactory.getCodeBuilder();
    fromElem.append(builder);
    assertThat(builder.build()).isEqualTo("tablename");
    assertThat(builder.getBindsWithPos()).isEmpty();
  }


}