package com.provys.db.sqldb.sql;

import static org.assertj.core.api.Assertions.*;
import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;
import static org.mockito.Mockito.*;

import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindName;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.BindWithPos;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.SegmentedName;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SqlFromClauseImplTest {

  @Test
  void getElementByAliasNoTest() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - no match
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    when(fromElem2.match(alias)).thenReturn(false);
    // prepare from element 3 - no match
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getElementByAlias(alias)).isNull();
  }

  @Test
  void getElementByAlias1Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - match
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getElementByAlias(alias)).isEqualTo(fromElem2);
  }

  @Test
  void getElementByAlias2Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - match
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    when(fromElem1.match(alias)).thenReturn(true);
    // prepare from element 2 - match
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThatThrownBy(() -> value.getElementByAlias(alias));
  }

  @Test
  void isUniqueNoTest() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - no match
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    when(fromElem2.match(alias)).thenReturn(false);
    // prepare from element 3 - no match
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.isUnique(alias)).isTrue();
  }

  @Test
  void isUnique1Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - no match
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    when(fromElem1.match(alias)).thenReturn(false);
    // prepare from element 2 - match
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.isUnique(alias)).isTrue();
  }

  @Test
  void isUnique2Test() {
    var alias = SegmentedName.valueOf("schema1.table1");
    // prepare from element 1 - match
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    when(fromElem1.match(alias)).thenReturn(true);
    // prepare from element 2 - match
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    when(fromElem2.match(alias)).thenReturn(true);
    // prepare from element 3 - no match
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    when(fromElem3.match(alias)).thenReturn(false);
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.isUnique(alias)).isFalse();
  }

  @Test
  void getElementsTest() {
    // prepare from elements
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getElements()).containsExactlyInAnyOrder(fromElem1, fromElem2, fromElem3);
  }

  @Test
  void getBindsTest() {
    // prepare from element 1 - no binds
    var fromElem1 = mock(SqlFromElement.class);
    when(fromElem1.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem1);
    when(fromElem1.getBinds()).thenReturn(Collections.emptyList());
    // prepare from element 2 - binds bind1, bind2
    var bind1 = new BindVariable("name1", String.class, null);
    var bind2 = new BindVariable("name2", Integer.class, null);
    var fromElem2 = mock(SqlFromElement.class);
    when(fromElem2.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem2);
    when(fromElem2.getBinds()).thenReturn(List.of(bind1, bind2));
    // prepare from element 3 - binds bind3, bind1
    var bind3 = new BindVariable("name3", String.class, "test value");
    var fromElem3 = mock(SqlFromElement.class);
    when(fromElem3.transfer(any(), nullable(BindMap.class))).thenReturn(fromElem3);
    when(fromElem3.getBinds()).thenReturn(List.of(bind3, bind1));
    // prepare value itself
    var context = mock(SqlContext.class);
    var value = new SqlFromClauseImpl(context, List.of(fromElem1, fromElem2, fromElem3), null);
    // assert
    assertThat(value.getBinds()).containsExactlyInAnyOrder(bind1, bind2, bind3);
  }

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