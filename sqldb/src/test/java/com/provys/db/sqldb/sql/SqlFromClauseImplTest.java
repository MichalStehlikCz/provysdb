package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.provys.common.jackson.JacksonMappers;
import com.provys.db.query.BindMap;
import com.provys.db.query.BindName;
import com.provys.db.query.BindVariable;
import com.provys.db.query.BindWithPos;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.SegmentedName;
import com.provys.db.query.SimpleName;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SqlFromClauseImplTest {
  @Test
  void appendTest() {
    // prepare from element 1 - table1, no binds
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    doAnswer(args -> {
      CodeBuilder builder = args.getArgument(0);
      assert builder != null : "@AssumeAssertion(nullness): builder is non-null argument...";
      builder.append("table1");
      return null;
    }).when(fromElem1).append(any());
    when(fromElem1.getBinds()).thenReturn(Collections.emptyList());
    // prepare from element 2 - table2, bind bind1
    var bind1 = new BindVariable("name1", String.class, null);
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    doAnswer(args -> {
      CodeBuilder builder = args.getArgument(0);
      assert builder != null : "@AssumeAssertion(nullness): builder is non-null argument...";
      builder.append("table2");
      builder.addBind(bind1);
      return null;
    }).when(fromElem2).append(any());
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2), null);
    // run
    var builder = CodeBuilderFactory.getCodeBuilder();
    value.append(builder);
    // assert
    assertThat(builder.build()).isEqualTo("FROM\n    table1\n  , table2\n");
    assertThat(builder.getBindsWithPos())
        .containsExactly(new BindWithPos(BindName.valueOf("name1"), String.class, List.of(1)));
  }

}