package com.provys.db.sqlquerybuilder;

import com.google.errorprone.annotations.ImmutableTypeParameter;
import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.elements.SelectT1;
import com.provys.db.query.elements.SelectT2;
import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import com.provys.db.querybuilder.AndConditionBuilder;
import com.provys.db.querybuilder.ElementBuilderFactory;
import com.provys.db.querybuilder.ExpressionBuilder;
import com.provys.db.querybuilder.OrConditionBuilder;
import com.provys.db.querybuilder.StartConditionBuilder;
import com.provys.db.sqlquery.query.StatementFactory;
import java.io.Serializable;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Basic factory classes, provides access to QueryBuilderFactory methods, but extends them with
 * database-context aware versions of select builders that allow creation of select statements
 * instead of select queries on build.
 */
public final class DefaultSqlQueryBuilderFactory implements AdminQueryBuilderFactory,
    UserQueryBuilderFactory {

  private final StatementFactory statementFactory;
  private final ElementBuilderFactory elementBuilderFactory;

  public DefaultSqlQueryBuilderFactory(StatementFactory statementFactory,
      ElementBuilderFactory elementBuilderFactory) {
    this.statementFactory = statementFactory;
    this.elementBuilderFactory = elementBuilderFactory;
  }

  @Override
  public <T> ExpressionBuilder<T> expression(Expression<T> expression) {
    return elementBuilderFactory.expression(expression);
  }

  @Override
  public <T> ExpressionBuilder<T> bind(Class<T> type,
      BindVariable bindVariable) {
    return elementBuilderFactory.bind(type, bindVariable);
  }

  @Override
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      BindName name, @Nullable T value) {
    return elementBuilderFactory.bind(type, name, value);
  }

  @Override
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      BindName name) {
    return elementBuilderFactory.bind(type, name);
  }

  @Override
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      String name, @Nullable T value) {
    return elementBuilderFactory.bind(type, name, value);
  }

  @Override
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> bind(Class<T> type,
      String name) {
    return elementBuilderFactory.bind(type, name);
  }

  @Override
  public <T> ExpressionBuilder<T> column(Class<T> type,
      @Nullable NamePath table, SimpleName column) {
    return elementBuilderFactory.column(type, table, column);
  }

  @Override
  public <T> ExpressionBuilder<T> column(SelectColumn<T> column) {
    return elementBuilderFactory.column(column);
  }

  @Override
  public <T> ExpressionBuilder<T> function(Class<T> type,
      BuiltInFunction function, Collection<? extends Expression<?>> arguments) {
    return elementBuilderFactory.function(type, function, arguments);
  }

  @Override
  public <T> ExpressionBuilder<T> function(Class<T> type,
      BuiltInFunction function, ExpressionBuilder<?>... argumentBuilders) {
    return elementBuilderFactory.function(type, function, argumentBuilders);
  }

  @Override
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> literal(
      Class<T> type, @Nullable T value) {
    return elementBuilderFactory.literal(type, value);
  }

  @Override
  public <@ImmutableTypeParameter T extends Serializable> ExpressionBuilder<T> literal(
      @NonNull T value) {
    return elementBuilderFactory.literal(value);
  }

  @Override
  public StartConditionBuilder condition(Condition condition) {
    return elementBuilderFactory.condition(condition);
  }

  @Override
  public AndConditionBuilder andCondition() {
    return elementBuilderFactory.andCondition();
  }

  @Override
  public OrConditionBuilder orCondition() {
    return elementBuilderFactory.orCondition();
  }

  @Override
  public DbSelectBuilderT0 select() {
    return new DefaultDbSelectBuilderT0(elementBuilderFactory.select(), statementFactory);
  }

  @Override
  public DbSelectBuilder select(SelectT<?> select) {
    return new DefaultDbSelectBuilder(elementBuilderFactory.select(select), statementFactory);
  }

  @Override
  public <T1> DbSelectBuilderT1<T1> select(SelectT1<T1> select) {
    return new DefaultDbSelectBuilderT1<>(elementBuilderFactory.select(select), statementFactory);
  }

  @Override
  public <T1, T2> DbSelectBuilderT2<T1, T2> select(SelectT2<T1, T2> select) {
    return new DefaultDbSelectBuilderT2<>(elementBuilderFactory.select(select), statementFactory);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultSqlQueryBuilderFactory that = (DefaultSqlQueryBuilderFactory) o;
    return statementFactory.equals(that.statementFactory)
        && elementBuilderFactory.equals(that.elementBuilderFactory);
  }

  @Override
  public int hashCode() {
    int result = statementFactory.hashCode();
    result = 31 * result + elementBuilderFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSqlQueryBuilderFactory{"
        + "statementFactory=" + statementFactory
        + ", elementBuilderFactory=" + elementBuilderFactory
        + '}';
  }
}
