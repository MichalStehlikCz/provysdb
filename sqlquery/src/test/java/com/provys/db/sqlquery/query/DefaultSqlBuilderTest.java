package com.provys.db.sqlquery.query;

import static com.provys.db.query.functions.BuiltInFunction.STRING_CHR;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CONCAT;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.provys.db.query.elements.Element;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.query.names.SegmentedName;
import com.provys.db.query.names.SimpleName;
import com.provys.db.sqlquery.literals.SqlLiteralHandler;
import com.provys.db.sqlquery.literals.SqlLiteralTypeHandlerMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@SuppressWarnings({"java:S4144", "java:S1192"})
    // same methods... still we want separate methods to make see where is problem when test fails
class DefaultSqlBuilderTest {

  private static final ElementFactory FACTORY = ElementFactory.getInstance();
  public static final BindWithPos[] BIND_WITH_POS = new BindWithPos[]{};

  private static DefaultSqlBuilder getBuilder() {
    return new DefaultSqlBuilder(SqlLiteralTypeHandlerMap.getDefaultMap(),
        SqlFunctionMapImpl.getDefault());
  }

  static Stream<Object[]> bindTest() {
    return Stream.of(
        new Object[]{FACTORY.bind(Integer.class, new BindVariable("name1", Integer.class, 5)),
            "?", new BindWithPos[]{
            new BindWithPos(BindName.valueOf("name1"), Integer.class, List.of(1))},
            Map.of(BindName.valueOf("name1"), (Object) 5)}
        , new Object[]{FACTORY.bind(String.class, new BindVariable("name2", String.class, null)),
            "?",
            new BindWithPos[]{new BindWithPos(BindName.valueOf("name2"), String.class, List.of(1))},
            Collections.emptyMap()}
    );
  }

  @ParameterizedTest
  @MethodSource
  void bindTest(Element<?> expression, String sql, BindWithPos[] bindWithPos,
      Map<BindName, Object> bindValue) {
    var builder = getBuilder();
    expression.apply(builder);
    assertThat(builder.getSql()).isEqualTo(sql);
    assertThat(builder.getBindsWithPos()).containsExactlyInAnyOrder(bindWithPos);
    assertThat(builder.getBindValues()).containsExactlyInAnyOrderEntriesOf(bindValue);
  }

  static Stream<Object[]> columnTest() {
    return Stream.of(
        new Object[]{FACTORY.column(Integer.class, null, SimpleName.valueOf("column")),
            "column"}
        , new Object[]{FACTORY.column(String.class, SegmentedName.valueOf("scheme.table"),
            SimpleName.valueOf("column")), "scheme.table.column"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void columnTest(Element<?> expression, String sql) {
    var builder = getBuilder();
    expression.apply(builder);
    assertThat(builder.getSql()).isEqualTo(sql);
    assertThat(builder.getBindsWithPos()).isEmpty();
    assertThat(builder.getBindValues()).isEmpty();
  }

  static Stream<Object[]> functionTest() {
    return Stream.of(
        new Object[]{FACTORY.function(String.class, STRING_CHR,
            List.of(FACTORY.literal(5))),
            "CHR(5)", BIND_WITH_POS, Collections.emptyMap()}
        , new Object[]{FACTORY.function(String.class, STRING_CONCAT,
            List.of(FACTORY.literal("first"), FACTORY.literal("second"),
                FACTORY.bind(String.class, new BindVariable("bind1", String.class, null)))),
            "'first'||'second'||?",
            new BindWithPos[]{new BindWithPos(BindName.valueOf("bind1"), String.class,
                List.of(1))}, Collections.emptyMap()}
    );
  }

  @ParameterizedTest
  @MethodSource
  void functionTest(Element<?> expression, String sql, BindWithPos[] bindWithPos,
      Map<BindName, Object> bindValue) {
    var builder = getBuilder();
    expression.apply(builder);
    assertThat(builder.getSql()).isEqualTo(sql);
    assertThat(builder.getBindsWithPos()).containsExactlyInAnyOrder(bindWithPos);
    assertThat(builder.getBindValues()).containsExactlyInAnyOrderEntriesOf(bindValue);
  }

  static Stream<Object[]> literalTest() {
    return Stream.of(
        new Object[]{FACTORY.literal(5), "5"}
        , new Object[]{FACTORY.literal("Ahoj"), "'Ahoj'"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void literalTest(Element<?> element, String sql) {
    var builder = getBuilder();
    element.apply(builder);
    assertThat(builder.getSql()).isEqualTo(sql);
    assertThat(builder.getBindsWithPos()).isEmpty();
    assertThat(builder.getBindValues()).isEmpty();
  }

  @ParameterizedTest
  @MethodSource
  void selectTest() {
  }

  @ParameterizedTest
  @MethodSource
  void testSelectTest() {
  }

  @ParameterizedTest
  @MethodSource
  void selectColumnsTest() {
  }

  @ParameterizedTest
  @MethodSource
  void selectColumnTest() {
  }

  @Test
  void fromTest() {
    // prepare from element 1 - table1, no binds
    var fromElem1 = FACTORY.fromTable(SimpleName.valueOf("table1"), null);
    // prepare from element 2 - table2, bind bind1
    var bind1 = new BindVariable("name1", String.class, null);
    var fromElem2 = FACTORY.fromSelect(FACTORY
        .select(FACTORY.selectColumn(FACTORY.bind(String.class, bind1), null),
            FACTORY.from(List.of(FACTORY.fromDual(null))), null), SimpleName.valueOf("table2"));
    // prepare value itself
    var element = FACTORY.from(List.of(fromElem1, fromElem2));
    // run
    var builder = getBuilder();
    element.apply(builder);
    // assert
    assertThat(builder.getSql()).isEqualTo(
        "FROM\n    table1\n  , (\n      SELECT\n          ?\n      FROM\n          dual\n    ) table2\n");
    assertThat(builder.getBindsWithPos())
        .containsExactly(new BindWithPos(BindName.valueOf("name1"), String.class, List.of(1)));
    assertThat(builder.getBindValues()).isEmpty();
  }

  static Stream<Object[]> fromTableTest() {
    return Stream.of(
        new Object[]{FACTORY.fromTable(SimpleName.valueOf("tablename"), SimpleName.valueOf("al")),
            "tablename al"}
        , new Object[]{FACTORY.fromTable(SegmentedName.valueOf("brc.brc_techinfo_tb"), null),
            "brc.brc_techinfo_tb"}
    );
  }

  @ParameterizedTest
  @MethodSource
  void fromTableTest(Element<?> element, String sql) {
    var builder = getBuilder();
    element.apply(builder);
    assertThat(builder.getSql()).isEqualTo(sql);
    assertThat(builder.getBindsWithPos()).isEmpty();
    assertThat(builder.getBindValues()).isEmpty();
  }

  @ParameterizedTest
  @MethodSource
  void fromSelectTest() {
  }

  @ParameterizedTest
  @MethodSource
  void eqTest() {
  }

  @Test
  void getCloneTest() {
    var sqlLiteralHandler = mock(SqlLiteralHandler.class);
    var sqlFunctionMap = mock(SqlFunctionMap.class);
    var sourceBuilder = new DefaultSqlBuilder(sqlLiteralHandler, sqlFunctionMap);
    sourceBuilder.append("text");
    assertThat(sourceBuilder.getClone().getSql()).isEmpty();
  }
}