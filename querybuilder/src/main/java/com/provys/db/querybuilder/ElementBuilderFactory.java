package com.provys.db.querybuilder;

import com.google.errorprone.annotations.ImmutableTypeParameter;
import com.provys.common.exception.InternalException;
import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.ExpressionConsumer;
import com.provys.db.query.elements.FromClause;
import com.provys.db.query.elements.SelectClause;
import com.provys.db.query.elements.SelectClauseConsumer;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectColumnConsumer;
import com.provys.db.query.elements.SelectConsumer;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.elements.SelectT1;
import com.provys.db.query.elements.SelectT2;
import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.functions.ConditionalOperator;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Factory capable of producing all element builders.
 */
public final class ElementBuilderFactory {

  private static final ElementBuilderFactory INSTANCE = new ElementBuilderFactory();

  /**
   * Get singleton instance of this factory.
   *
   * @return singleton instance of this factory
   */
  public static ElementBuilderFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Query factory, used to produce query elements.
   */
  private final ElementFactory elementFactory = ElementFactory.getInstance();

  /**
   * No point producing multiple instances - at least not until there are multiple implementations
   * of ElementFactory and we want to inject them on creation...
   */
  private ElementBuilderFactory() {
  }

  /**
   * Get expression builder, wrapping given expression.
   *
   * @param expression is expression to be used as basis for expression builder
   * @param <T>        is type of expression
   * @return expression builder, based on given expression
   */
  public <T> ExpressionBuilder<T> expression(Expression<T> expression) {
    return new DecoratingExpressionBuilder<>(expression, elementFactory);
  }

  /**
   * Create expression builder, based on supplied bind variable.
   *
   * @param type         is type of expression
   * @param bindVariable is bind variable that will be used as basis of expression
   * @param <T>          is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <T> ExpressionBuilder<T> bind(Class<T> type, BindVariable bindVariable) {
    return expression(elementFactory.bind(type, bindVariable));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      BindName name, @Nullable T value) {
    return expression(elementFactory.bind(type, name, value));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      BindName name) {
    return expression(elementFactory.bind(type, name));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type  is type of expression (and bind variable)
   * @param name  is name of bind variable
   * @param value is initial value of bind variable
   * @param <T>   is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      String name, @Nullable T value) {
    return expression(elementFactory.bind(type, name, value));
  }

  /**
   * Create bind expression builder based on supplied parameters.
   *
   * @param type is type of expression (and bind variable)
   * @param name is name of bind variable
   * @param <T>  is type parameter denoting type of expression
   * @return expression builder based on bind variable expression
   */
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      String name) {
    return expression(elementFactory.bind(type, name));
  }

  /**
   * Create column expression builder, based on specified source (identified by alias), column name
   * and type. Expression is validated against supplied context.
   *
   * @param type   is type that given expression should yield
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param <T>    is type parameter denoting type of expression
   * @return expression builder, representing single column / property from source
   */
  public <T> ExpressionBuilder<T> column(Class<T> type, @Nullable NamePath table,
      SimpleName column) {
    return expression(elementFactory.column(type, table, column));
  }

  private static final class SelectColumnAnalyzer implements SelectColumnConsumer {

    private @Nullable Expression<?> expressionFound;
    private @Nullable SimpleName aliasFound;

    @Override
    public void selectColumn(Expression<?> expression, @Nullable SimpleName alias) {
      expressionFound = expression;
      aliasFound = alias;
    }
  }

  /**
   * Create expression builder, based on supplied column. Column must be of type based on expression
   * - if column is other type (e.g. table.*), action fails.
   *
   * @param column is column expression should be based on
   * @param <T>    is type parameter denoting type of expression
   * @return expression builder, representing single column / property from source
   */
  public <T> ExpressionBuilder<T> column(SelectColumn<T> column) {
    var selectColumnAnalyzer = new SelectColumnAnalyzer();
    column.apply(selectColumnAnalyzer);
    if (selectColumnAnalyzer.expressionFound == null) {
      throw new InternalException(
          "Cannot create expression builder from column not based on expression " + column);
    }
    // should be safe, given that we extract expression from column of type T
    @SuppressWarnings("unchecked")
    var expression = (Expression<T>) selectColumnAnalyzer.expressionFound;
    return new DecoratingColumnExpressionBuilder<>(expression, selectColumnAnalyzer.aliasFound,
        elementFactory);
  }

  /**
   * Create outer column (e.g. column with (+) ) expression builder, based on specified source
   * (identified by alias), column name and type. Expression is validated against supplied context.
   *
   * @param type   is type that given expression should yield
   * @param table  is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param <T>    is type parameter denoting type of expression
   * @return expression builder, representing single column / property from source
   */
  public <T> ExpressionBuilder<T> columnOuter(Class<T> type, @Nullable NamePath table,
      SimpleName column) {
    return expression(elementFactory.columnOuter(type, table, column));
  }

  private static final class ExpressionColumnAnalyzer implements ExpressionConsumer {

    private @MonotonicNonNull Class<?> typeFound;
    private @Nullable NamePath tableFound;
    private @MonotonicNonNull SimpleName columnFound;

    /**
     * Value of field typeFound.
     *
     * @return value of field typeFound
     */
    public Class<?> getTypeFound() {
      if (typeFound == null) {
        throw new IllegalStateException("Cannot read type - type not initialized");
      }
      return typeFound;
    }

    /**
     * Value of field tableFound.
     *
     * @return value of field tableFound
     */
    public @Nullable NamePath getTableFound() {
      return tableFound;
    }

    /**
     * Value of field columnFound.
     *
     * @return value of field columnFound
     */
    public SimpleName getColumnFound() {
      if (columnFound == null) {
        throw new IllegalStateException("Cannot read column - column not initialized");
      }
      return columnFound;
    }

    @Override
    public void bind(Class<?> type, BindVariable bindVariable) {
      throw new InternalException("Cannot mark outer join on bind expression");
    }

    @Override
    public void column(Class<?> type, @Nullable NamePath table, SimpleName column) {
      typeFound = type;
      tableFound = table;
      columnFound = column;
    }

    @Override
    public void columnOuter(Class<?> type, @Nullable NamePath table, SimpleName column) {
      throw new InternalException("Cannot mark outer join - already marked on column");
    }

    @Override
    public void function(Class<?> type, BuiltInFunction function,
        Collection<? extends Expression<?>> arguments) {
      throw new InternalException("Cannot mark outer join on built-in function expression");
    }

    @Override
    public <T> void literal(Class<T> type, @Nullable T value) {
      throw new InternalException("Cannot mark outer join on literal");
    }

    @Override
    public void condition(ConditionalOperator operator,
        Collection<? extends Expression<?>> arguments) {
      throw new InternalException("Cannot mark outer join on condition");
    }
  }

  /**
   * Create outer column (e.g. column with (+) ) expression builder, based on specified select
   * column. Used when using column definitions read from database.
   *
   * @param column is select column that is to be used as source for outer column expression
   * @param <T>    is type parameter denoting type of expression
   * @return expression builder, representing single column / property from source
   */
  public <T> ExpressionBuilder<T> columnOuter(SelectColumn<T> column) {
    var selectColumnAnalyzer = new SelectColumnAnalyzer();
    column.apply(selectColumnAnalyzer);
    var expression = selectColumnAnalyzer.expressionFound;
    if (expression == null) {
      throw new InternalException(
          "Cannot create expression builder from column not based on expression " + column);
    }
    var expressionAnalyzer = new ExpressionColumnAnalyzer();
    expression.apply(expressionAnalyzer);
    @SuppressWarnings("unchecked") // ok because we started from column of correct type
    var result = (ExpressionBuilder<T>) expression(elementFactory.columnOuter(
        expressionAnalyzer.getTypeFound(), expressionAnalyzer.getTableFound(),
        expressionAnalyzer.getColumnFound()));
    return result;
  }

  /**
   * Create function builder that will evaluate to supplied type, based on supplied function and
   * using arguments. Type of function and arguments are validated against function definition.
   *
   * @param type      is type of object expression returns
   * @param function  is built-in function, used for evaluation
   * @param arguments are arguments to be passed to function
   * @param <T>       is type parameter denoting type of expression
   * @return function, representing built-in function applied on supplied arguments
   */
  public <T> ExpressionBuilder<T> function(Class<T> type, BuiltInFunction function,
      Collection<? extends Expression<?>> arguments) {
    return expression(elementFactory.function(type, function, arguments));
  }

  /**
   * Create function builder that will evaluate to supplied type, based on supplied function and
   * using arguments. Type of function and arguments are validated against function definition.
   *
   * @param type             is type of object expression returns
   * @param function         is built-in function, used for evaluation
   * @param argumentBuilders are argument builders to be passed to function
   * @param <T>              is type parameter denoting type of expression
   * @return function, representing built-in function applied on supplied arguments
   */
  public <T> ExpressionBuilder<T> function(Class<T> type, BuiltInFunction function,
      ExpressionBuilder<?>... argumentBuilders) {
    var arguments = new ArrayList<Expression<?>>(argumentBuilders.length);
    for (var argumentBuilder : argumentBuilders) {
      arguments.add(argumentBuilder.build());
    }
    return function(type, function, arguments);
  }

  /**
   * Create new literal expression builder of given type and value.
   *
   * @param type  is type of expression
   * @param value is expression value
   * @param <T>   is parameter denoting type of expression
   * @return new expression builder based on literal, representing supplied value
   */
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> literal(
      Class<T> type, @Nullable T value) {
    return expression(elementFactory.literal(type, value));
  }

  /**
   * Create new literal expression builder of given value. Not that it does not work correctly if
   * value is of generic type, as only class information is preserved, but generic parameters are
   * lost.
   *
   * @param value is expression value
   * @param <T>   is parameter denoting type of expression
   * @return expression builder based on literal, representing supplied value
   */
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> literal(
      @NonNull T value) {
    return expression(elementFactory.literal(value));
  }

  /**
   * Create new condition builder, decorating supplied condition.
   *
   * @param condition is condition to be decorated
   * @return new condition builder
   */
  public StartConditionBuilder condition(Condition condition) {
    return new DecoratingConditionBuilder(condition, elementFactory);
  }

  /**
   * Create builder, allowing construction of AND connected chain of conditions. Note that it is
   * also possible to use {@code condition()} and follow with appending further conditions.
   *
   * @return AND condition chain builder
   */
  public AndConditionBuilder andCondition() {
    return new CombiningConditionBuilderAnd(elementFactory);
  }

  /**
   * Create builder, allowing construction of OR connected chain of conditions. Note that it is
   * also possible to use {@code condition()} and follow with appending further conditions.
   *
   * @return OR condition chain builder
   */
  public OrConditionBuilder orCondition() {
    return new CombiningConditionBuilderOr(elementFactory);
  }

  /**
   * Create select builder, allowing to add columns, from conditions, where clause etc.
   *
   * @return new empty select builder
   */
  public SelectBuilderT0 select() {
    return new DefaultSelectBuilderT0(elementFactory);
  }

  private final class SelectBuilderFactory implements SelectConsumer {

    private @Nullable SelectBuilder builder;

    private final class SelectClauseBuilder implements SelectClauseConsumer {

      private @Nullable Collection<? extends SelectColumn<?>> foundColumns;

      @Override
      public void selectColumns(Collection<? extends SelectColumn<?>> columns) {
        foundColumns = columns;
      }
    }

    @Override
    public void select(SelectClause selectClause, FromClause fromClause,
        @Nullable Condition whereClause) {
      var selectClauseBuilder = new SelectClauseBuilder();
      selectClause.apply(selectClauseBuilder);
      if (selectClauseBuilder.foundColumns == null) {
        throw new InternalException("Columns not found in select clause");
      }
      select(selectClauseBuilder.foundColumns, fromClause, whereClause);
    }

    @Override
    public void select(Collection<? extends SelectColumn<?>> columns, FromClause fromClause,
        @Nullable Condition whereClause) {
      builder = new DefaultSelectBuilder(columns, fromClause.getElements(), whereClause,
          elementFactory);
    }
  }

  /**
   * Create select builder based on supplied select statement - general version.
   *
   * @param select is select query builder should be based on
   * @return new select builder
   */
  public SelectBuilder select(SelectT<?> select) {
    var selectBuilderFactory = new SelectBuilderFactory();
    select.apply(selectBuilderFactory);
    if (selectBuilderFactory.builder == null) {
      throw new InternalException("Cannot build select builder around supplied select statement");
    }
    return selectBuilderFactory.builder;
  }

  /**
   * Create select builder based on supplied select statement with single column.
   *
   * @param select is single column select query this builder should be based on
   * @param <T1> is type of the first and only column
   * @return new select builder, initialized based on supplied statement
   */
  public <T1> SelectBuilderT1<T1> select(SelectT1<T1> select) {
    return new DefaultSelectBuilderT1<>(select.getColumn1(), select.getFromClause().getElements(),
        select.getWhereClause(), elementFactory);
  }

  /**
   * Create select builder based on supplied select statement with two columns.
   *
   * @param select is two column select query this builder is based on
   * @param <T1> is type of the first column
   * @param <T2> is type of the second column
   * @return new select builder, initialized based on supplied statement
   */
  public <T1, T2> SelectBuilderT2<T1, T2> select(SelectT2<T1, T2> select) {
    return new DefaultSelectBuilderT2<>(select.getColumn1(), select.getColumn2(),
        select.getFromClause().getElements(), select.getWhereClause(), elementFactory);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ElementBuilderFactory that = (ElementBuilderFactory) o;
    return elementFactory.equals(that.elementFactory);
  }

  @Override
  public int hashCode() {
    return elementFactory.hashCode();
  }

  @Override
  public String toString() {
    return "ElementBuilderFactory{"
        + "elementFactory=" + elementFactory
        + '}';
  }
}
