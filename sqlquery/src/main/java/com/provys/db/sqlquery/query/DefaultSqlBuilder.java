package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.FromClause;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectClause;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.functions.BuiltIn;
import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.functions.ConditionalOperator;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import com.provys.db.sqlquery.codebuilder.CodeBuilderFactory;
import com.provys.db.sqlquery.literals.SqlLiteralHandler;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default implementation of Sql builder, delegating code builder functionality to internal code
 * builder instance and using supplied literal handler, function map end element builder.
 */
public class DefaultSqlBuilder implements SqlBuilder<DefaultSqlBuilder> {

  private final CodeBuilder codeBuilder = CodeBuilderFactory.getCodeBuilder();
  private final Deque<SqlBuilderPosition> positionStack = new ArrayDeque<>(5);
  private final SqlLiteralHandler sqlLiteralHandler;
  private final SqlBuiltInMap sqlBuiltInMap;

  /**
   * Constructor, creating sql builder that creates new code builder using factory and uses supplied
   * literal handler, function map end element builder.
   *
   * @param sqlLiteralHandler is literal handler that will manage export of literals
   * @param sqlBuiltInMap    will support export of functions
   */
  public DefaultSqlBuilder(SqlLiteralHandler sqlLiteralHandler, SqlBuiltInMap sqlBuiltInMap) {
    this.sqlLiteralHandler = sqlLiteralHandler;
    this.sqlBuiltInMap = sqlBuiltInMap;
  }

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @param type  is Java type of literal
   * @param <T>   represents Java type of literal for compile-time type safety
   * @return string representing literal for given value
   */
  protected <T> String getLiteral(@Nullable T value, Class<T> type) {
    return sqlLiteralHandler.getLiteral(value, type);
  }

  /**
   * Append literal corresponding to given value to internal builder.
   *
   * @param value is value literal should be created for
   * @param type  is Java type of literal
   * @param <T>   represents Java type of literal for compile-time type safety
   */
  protected <T> void appendLiteral(@Nullable T value, Class<T> type) {
    codeBuilder
        .applyString(stringBuilder -> sqlLiteralHandler.appendLiteral(stringBuilder, value, type));
  }

  /**
   * Append text, corresponding to invocation of supplied function with supplied arguments.
   *
   * @param builtIn       is function to be invoked
   * @param argumentAppend are appenders for individual arguments
   */
  protected void append(BuiltIn builtIn,
      List<? extends Consumer<? super DefaultSqlBuilder>> argumentAppend) {
    sqlBuiltInMap.append(builtIn, argumentAppend, this);
  }

  @Override
  public DefaultSqlBuilder append(String text) {
    codeBuilder.append(text);
    return this;
  }

  @Override
  public DefaultSqlBuilder appendLine(String text) {
    codeBuilder.appendLine(text);
    return this;
  }

  @Override
  public DefaultSqlBuilder appendLine() {
    codeBuilder.appendLine();
    return this;
  }

  @Override
  public DefaultSqlBuilder increasedIdent(int increaseBy) {
    codeBuilder.increasedIdent(increaseBy);
    return this;
  }

  @Override
  public DefaultSqlBuilder popIdent() {
    codeBuilder.popIdent();
    return this;
  }

  @Override
  public SqlBuilderPosition getPosition() {
    var result = positionStack.peek();
    return (result == null) ? SqlBuilderPosition.GENERAL : result;
  }

  @Override
  public DefaultSqlBuilder pushPosition(SqlBuilderPosition position) {
    positionStack.push(position);
    return this;
  }

  @Override
  public DefaultSqlBuilder popPosition() {
    positionStack.pop();
    return this;
  }

  @Override
  public void bind(Class<?> type, BindVariable bindVariable) {
    codeBuilder
        .append('?')
        .addBind(bindVariable);
  }

  @Override
  public void column(Class<?> type, @Nullable NamePath table, SimpleName column) {
    if (table != null) {
      codeBuilder.append(table).append('.');
    }
    codeBuilder.append(column);
  }

  @Override
  public void columnOuter(Class<?> type, @Nullable NamePath table, SimpleName column) {
    column(type, table, column);
    codeBuilder.append("(+)");
  }

  private class ArgumentAppender implements Consumer<SqlBuilder<?>> {

    private final Expression<?> argument;

    ArgumentAppender(Expression<?> argument) {
      this.argument = argument;
    }

    @Override
    public void accept(SqlBuilder<?> builder) {
      argument.apply(DefaultSqlBuilder.this);
    }
  }

  /**
   * Consume condition, based on supplied operator and arguments.
   *
   * @param operator  is condition operator / function
   * @param arguments are supplied arguments
   */
  @Override
  public void condition(ConditionalOperator operator,
      Collection<? extends Expression<?>> arguments) {
    if (getPosition() == SqlBuilderPosition.WHERE) {
      append("    ");
    }
    List<Consumer<? super SqlBuilder<?>>> argumentsAppend = arguments.stream()
        .map(ArgumentAppender::new)
        .collect(Collectors.toList());
    append(operator, argumentsAppend);
  }

  @Override
  public void function(Class<?> type, BuiltInFunction function,
      Collection<? extends Expression<?>> arguments) {
    List<Consumer<? super SqlBuilder<?>>> argumentsAppend = arguments.stream()
        .map(ArgumentAppender::new)
        .collect(Collectors.toList());
    append(function, argumentsAppend);
  }

  @Override
  public <T> void literal(Class<T> type, @Nullable T value) {
    appendLiteral(value, type);
  }

  /**
   * By default appends alias without AS keyword, but can be overwritten to include this keyword.
   * Used for both column and table aliases
   *
   * @param alias is alias to be appended, potentially null
   */
  protected void appendAlias(@Nullable NamePath alias) {
    if (alias != null) {
      codeBuilder
          .append(' ')
          .append(alias);
    }
  }

  @SuppressWarnings("TypeMayBeWeakened")
  private void where(@Nullable Condition whereClause) {
    if (whereClause != null) {
      appendLine("WHERE");
      increasedIdent(2);
      pushPosition(SqlBuilderPosition.WHERE);
      whereClause.apply(this);
      appendLine();
      popPosition();
      popIdent();
    }
  }

  @Override
  public void select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause) {
    selectClause.apply(this);
    fromClause.apply(this);
    where(whereClause);
  }

  @Override
  public void select(Collection<? extends SelectColumn<?>> columns, FromClause fromClause,
      @Nullable Condition whereClause) {
    selectColumns(columns);
    fromClause.apply(this);
    where(whereClause);
  }

  @Override
  public void selectColumns(Collection<? extends SelectColumn<?>> columns) {
    codeBuilder
        .appendLine("SELECT")
        .increasedIdent("  ", ", ", 4);
    for (var column : columns) {
      column.apply(this);
      codeBuilder.appendLine();
    }
    codeBuilder.popIdent();
  }

  @Override
  public void selectColumn(Expression<?> expression, @Nullable SimpleName alias) {
    expression.apply(this);
    appendAlias(alias);
  }

  @Override
  public void from(Collection<? extends FromElement> fromElements) {
    codeBuilder
        .appendLine("FROM")
        .increasedIdent("  ", ", ", 4);
    for (var fromElement : fromElements) {
      fromElement.apply(this);
      codeBuilder.appendLine();
    }
    codeBuilder.popIdent();
  }

  @Override
  public void fromTable(NamePath tableName, @Nullable SimpleName alias) {
    codeBuilder.append(tableName);
    appendAlias(alias);
  }

  @Override
  public void fromSelect(SelectT<?> select, @Nullable SimpleName alias) {
    codeBuilder
        .appendLine("(")
        .increasedIdent(2);
    select.apply(this);
    codeBuilder
        .popIdent()
        .increasedIdent(0) // we want clear line with original ident level
        .append(')')
        .popIdent();
    appendAlias(alias);
  }

  @Override
  public void fromDual(@Nullable SimpleName alias) {
    codeBuilder.append("dual");
    appendAlias(alias);
  }

  @Override
  public void keyword(String keyword) {
    codeBuilder.appendName(keyword.toUpperCase(Locale.ENGLISH));
  }

  @Override
  public void name(NamePath namePath) {
    codeBuilder.appendName(namePath.getText());
  }

  @Override
  public void symbol(String symbol) {
    if (symbol.equals("=>")) {
      // per provys conventions, param spec is surrounded by spaces
      codeBuilder.append(" => ");
    } else {
      // other symbols are without spaces
      codeBuilder.append(symbol);
    }
  }

  @Override
  public void simpleComment(String comment) {
    codeBuilder
        .append("--")
        .appendLine(comment);
  }

  @Override
  public void longComment(String comment) {
    codeBuilder
        .append("/*")
        .append(comment)
        .append("*/");
  }

  @Override
  public DefaultSqlBuilder getClone() {
    return new DefaultSqlBuilder(sqlLiteralHandler, sqlBuiltInMap);
  }

  @Override
  public String getSql() {
    return codeBuilder.build();
  }

  @Override
  public Collection<BindWithPos> getBindsWithPos() {
    return codeBuilder.getBindsWithPos();
  }

  @Override
  public Map<BindName, Object> getBindValues() {
    return codeBuilder.getBindValues();
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultSqlBuilder that = (DefaultSqlBuilder) o;
    return codeBuilder.equals(that.codeBuilder)
        && sqlLiteralHandler.equals(that.sqlLiteralHandler)
        && sqlBuiltInMap.equals(that.sqlBuiltInMap);
  }

  @Override
  public int hashCode() {
    int result = codeBuilder.hashCode();
    result = 31 * result + sqlLiteralHandler.hashCode();
    result = 31 * result + sqlBuiltInMap.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSqlBuilder{"
        + "codeBuilder=" + codeBuilder
        + ", sqlLiteralHandler=" + sqlLiteralHandler
        + ", sqlFunctionMap=" + sqlBuiltInMap
        + '}';
  }
}
