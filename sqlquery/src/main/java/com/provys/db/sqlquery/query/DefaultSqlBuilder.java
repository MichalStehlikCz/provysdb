package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Element;
import com.provys.db.query.elements.Function;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.query.names.NamePath;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import com.provys.db.sqlquery.codebuilder.CodeBuilderFactory;
import com.provys.db.sqlquery.codebuilder.CodeIdent;
import com.provys.db.sqlquery.codebuilder.CodeIdentBuilder;
import com.provys.db.sqlquery.literals.SqlLiteralHandler;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default implementation of Sql builder, delegating code builder functionality to internal code
 * builder instance and using supplied literal handler, function map end element builder.
 */
public class DefaultSqlBuilder implements SqlBuilder<DefaultSqlBuilder> {

  private final CodeBuilder codeBuilder = CodeBuilderFactory.getCodeBuilder();
  private final SqlLiteralHandler sqlLiteralHandler;
  private final SqlFunctionMap sqlFunctionMap;
  private final ElementSqlBuilder<? super DefaultSqlBuilder> elementSqlBuilder;

  /**
   * Constructor, creating sql builder that creates new code builder using factory and uses supplied
   * literal handler, function map end element builder.
   *
   * @param sqlLiteralHandler is literal handler that will manage export of literals
   * @param sqlFunctionMap    will support export of functions
   * @param elementSqlBuilder will provide builders for individual types of elements
   */
  public DefaultSqlBuilder(SqlLiteralHandler sqlLiteralHandler,
      SqlFunctionMap sqlFunctionMap,
      ElementSqlBuilder<? super DefaultSqlBuilder> elementSqlBuilder) {
    this.sqlLiteralHandler = sqlLiteralHandler;
    this.sqlFunctionMap = sqlFunctionMap;
    this.elementSqlBuilder = elementSqlBuilder;
  }

  @Override
  public DefaultSqlBuilder append(String text) {
    codeBuilder.append(text);
    return this;
  }

  @Override
  public DefaultSqlBuilder append(char character) {
    codeBuilder.append(character);
    return this;
  }

  @Override
  public DefaultSqlBuilder append(int number) {
    codeBuilder.append(number);
    return this;
  }

  @Override
  public DefaultSqlBuilder append(BigInteger number) {
    codeBuilder.append(number);
    return this;
  }

  @Override
  public DefaultSqlBuilder append(NamePath name) {
    codeBuilder.append(name);
    return this;
  }

  @Override
  public DefaultSqlBuilder append(CodeBuilder appendBuilder) {
    codeBuilder.append(appendBuilder);
    return this;
  }

  @Override
  public DefaultSqlBuilder apply(
      Consumer<? super CodeBuilder> appendFunction) {
    codeBuilder.apply(appendFunction);
    return this;
  }

  @Override
  public DefaultSqlBuilder applyString(
      Consumer<? super StringBuilder> appendFunction) {
    codeBuilder.applyString(appendFunction);
    return this;
  }

  @Override
  public DefaultSqlBuilder appendWrapped(String text, int additionalIdent) {
    codeBuilder.appendWrapped(text, additionalIdent);
    return this;
  }

  @Override
  public DefaultSqlBuilder appendWrapped(String text) {
    codeBuilder.appendWrapped(text);
    return this;
  }

  @Override
  public DefaultSqlBuilder appendLine() {
    codeBuilder.appendLine();
    return this;
  }

  @Override
  public DefaultSqlBuilder appendLine(String line) {
    codeBuilder.appendLine(line);
    return this;
  }

  @Override
  public CodeIdentBuilder identBuilder() {
    return codeBuilder.identBuilder();
  }

  @Override
  public DefaultSqlBuilder setIdent(CodeIdent ident) {
    codeBuilder.setIdent(ident);
    return this;
  }

  @Override
  public DefaultSqlBuilder setIdent(String ident) {
    codeBuilder.setIdent(ident);
    return this;
  }

  @Override
  public DefaultSqlBuilder setIdent(String ident, int chars) {
    codeBuilder.setIdent(ident, chars);
    return this;
  }

  @Override
  public DefaultSqlBuilder setIdent(String firstIdent, String ident) {
    codeBuilder.setIdent(firstIdent, ident);
    return this;
  }

  @Override
  public DefaultSqlBuilder setIdent(String firstIdent, String ident, int chars) {
    codeBuilder.setIdent(firstIdent, ident, chars);
    return this;
  }

  @Override
  public DefaultSqlBuilder increasedIdent(int increaseBy) {
    codeBuilder.increasedIdent(increaseBy);
    return this;
  }

  @Override
  public DefaultSqlBuilder increasedIdent(String ident, int increaseBy) {
    codeBuilder.increasedIdent(ident, increaseBy);
    return this;
  }

  @Override
  public DefaultSqlBuilder increasedIdent(String firstIdent, String ident, int increaseBy) {
    codeBuilder.increasedIdent(firstIdent, ident, increaseBy);
    return this;
  }

  @Override
  public DefaultSqlBuilder popIdent() {
    codeBuilder.popIdent();
    return this;
  }

  @Override
  public DefaultSqlBuilder addBind(BindVariable bind) {
    codeBuilder.addBind(bind);
    return this;
  }

  @Override
  public DefaultSqlBuilder addBinds(
      Collection<? extends BindVariable> binds) {
    codeBuilder.addBinds(binds);
    return this;
  }

  @Override
  public DefaultSqlBuilder addBindsWithPos(
      Collection<BindWithPos> binds) {
    codeBuilder.addBindsWithPos(binds);
    return this;
  }

  @Override
  public String build() {
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
  public String getLiteral(Object value) {
    return sqlLiteralHandler.getLiteral(value);
  }

  @Override
  public DefaultSqlBuilder appendLiteral(Object value) {
    this.applyString(stringBuilder -> sqlLiteralHandler.appendLiteral(stringBuilder, value));
    return this;
  }

  @Override
  public DefaultSqlBuilder append(Function function,
      List<? extends Consumer<? super DefaultSqlBuilder>> argumentAppend) {
    sqlFunctionMap.append(function, argumentAppend, this);
    return this;
  }

  @Override
  public DefaultSqlBuilder append(Element<?> element) {
    elementSqlBuilder.append(this, element);
    return this;
  }

  @Override
  public DefaultSqlBuilder getClone() {
    return new DefaultSqlBuilder(sqlLiteralHandler, sqlFunctionMap, elementSqlBuilder);
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
        && sqlFunctionMap.equals(that.sqlFunctionMap)
        && elementSqlBuilder.equals(that.elementSqlBuilder);
  }

  @Override
  public int hashCode() {
    int result = codeBuilder.hashCode();
    result = 31 * result + sqlLiteralHandler.hashCode();
    result = 31 * result + sqlFunctionMap.hashCode();
    result = 31 * result + elementSqlBuilder.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSqlBuilder{"
        + "codeBuilder=" + codeBuilder
        + ", sqlLiteralHandler=" + sqlLiteralHandler
        + ", sqlFunctionMap=" + sqlFunctionMap
        + ", elementSqlBuilder=" + elementSqlBuilder
        + ", " + super.toString() + '}';
  }
}
