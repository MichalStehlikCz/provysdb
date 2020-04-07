package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.FromClause;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.Function;
import com.provys.db.query.elements.SelectClause;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import com.provys.db.sqlquery.codebuilder.CodeBuilderFactory;
import com.provys.db.sqlquery.literals.SqlLiteralHandler;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;

/**
 * Default implementation of Sql builder, delegating code builder functionality to internal code
 * builder instance and using supplied literal handler, function map end element builder.
 */
public class DefaultSqlBuilder implements SqlBuilder<DefaultSqlBuilder> {

  private final CodeBuilder codeBuilder = CodeBuilderFactory.getCodeBuilder();
  private final SqlLiteralHandler sqlLiteralHandler;
  private final SqlFunctionMap sqlFunctionMap;

  /**
   * Constructor, creating sql builder that creates new code builder using factory and uses supplied
   * literal handler, function map end element builder.
   *
   * @param sqlLiteralHandler is literal handler that will manage export of literals
   * @param sqlFunctionMap    will support export of functions
   */
  public DefaultSqlBuilder(SqlLiteralHandler sqlLiteralHandler, SqlFunctionMap sqlFunctionMap) {
    this.sqlLiteralHandler = sqlLiteralHandler;
    this.sqlFunctionMap = sqlFunctionMap;
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
   * @param function       is function to be invoked
   * @param argumentAppend are appenders for individual arguments
   */
  protected void append(Function function,
      List<? extends Consumer<? super DefaultSqlBuilder>> argumentAppend) {
    sqlFunctionMap.append(function, argumentAppend, this);
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

  @Override
  public void function(Class<?> type, Function function,
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

  @Override
  public void select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause) {
    selectClause.apply(this);
    fromClause.apply(this);
    if (whereClause != null) {
      codeBuilder.appendLine("WHERE").increasedIdent(2);
      whereClause.apply(this);
      codeBuilder.popIdent();
    }
  }

  @Override
  public void select(Collection<? extends SelectColumn<?>> columns, FromClause fromClause,
      @Nullable Condition whereClause) {
    selectColumns(columns);
    fromClause.apply(this);
    if (whereClause != null) {
      codeBuilder.appendLine("WHERE").increasedIdent(2);
      whereClause.apply(this);
      codeBuilder.popIdent();
    }
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
        .appendLine()
        .append(')');
    appendAlias(alias);
  }

  @Override
  public void fromDual(@Nullable SimpleName alias) {
    codeBuilder.append("dual");
    appendAlias(alias);
  }

  @Override
  public <T> void eq(Expression<T> expression1, Expression<T> expression2) {
    codeBuilder.append('(');
    expression1.apply(this);
    codeBuilder.append('=');
    expression2.apply(this);
    codeBuilder.append(')');
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
  public void append(String text) {
    codeBuilder.append(text);
  }

  @Override
  public DefaultSqlBuilder getClone() {
    return new DefaultSqlBuilder(sqlLiteralHandler, sqlFunctionMap);
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

  @SuppressWarnings("EqualsGetClass")
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
        && sqlFunctionMap.equals(that.sqlFunctionMap);
  }

  @Override
  public int hashCode() {
    int result = codeBuilder.hashCode();
    result = 31 * result + sqlLiteralHandler.hashCode();
    result = 31 * result + sqlFunctionMap.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSqlBuilder{"
        + "codeBuilder=" + codeBuilder
        + ", sqlLiteralHandler=" + sqlLiteralHandler
        + ", sqlFunctionMap=" + sqlFunctionMap
        + '}';
  }
}
