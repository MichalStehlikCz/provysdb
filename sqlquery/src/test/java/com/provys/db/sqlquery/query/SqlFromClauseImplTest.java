package com.provys.db.sqlquery.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.provys.db.query.BindMap;
import com.provys.db.query.BindName;
import com.provys.db.query.BindVariable;
import com.provys.db.query.BindWithPos;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.SegmentedName;
import com.provys.db.query.SimpleName;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import com.provys.db.sqlquery.codebuilder.CodeBuilderFactory;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

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