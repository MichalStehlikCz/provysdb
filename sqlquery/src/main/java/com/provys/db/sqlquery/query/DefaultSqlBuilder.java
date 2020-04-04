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
  public CodeBuilder append(String text) {
    return codeBuilder.append(text);
  }

  @Override
  public CodeBuilder append(char character) {
    return codeBuilder.append(character);
  }

  @Override
  public CodeBuilder append(int number) {
    return codeBuilder.append(number);
  }

  @Override
  public CodeBuilder append(BigInteger number) {
    return codeBuilder.append(number);
  }

  @Override
  public CodeBuilder append(NamePath name) {
    return codeBuilder.append(name);
  }

  @Override
  public CodeBuilder append(CodeBuilder appendBuilder) {
    return codeBuilder.append(appendBuilder);
  }

  @Override
  public CodeBuilder apply(
      Consumer<? super CodeBuilder> appendFunction) {
    return codeBuilder.apply(appendFunction);
  }

  @Override
  public CodeBuilder applyString(
      Consumer<? super StringBuilder> appendFunction) {
    return codeBuilder.applyString(appendFunction);
  }

  @Override
  public CodeBuilder appendWrapped(String text, int additionalIdent) {
    return codeBuilder.appendWrapped(text, additionalIdent);
  }

  @Override
  public CodeBuilder appendWrapped(String text) {
    return codeBuilder.appendWrapped(text);
  }

  @Override
  public CodeBuilder appendLine() {
    return codeBuilder.appendLine();
  }

  @Override
  public CodeBuilder appendLine(String line) {
    return codeBuilder.appendLine(line);
  }

  @Override
  public CodeIdentBuilder identBuilder() {
    return codeBuilder.identBuilder();
  }

  @Override
  public CodeBuilder setIdent(CodeIdent ident) {
    return codeBuilder.setIdent(ident);
  }

  @Override
  public CodeBuilder setIdent(String ident) {
    return codeBuilder.setIdent(ident);
  }

  @Override
  public CodeBuilder setIdent(String ident, int chars) {
    return codeBuilder.setIdent(ident, chars);
  }

  @Override
  public CodeBuilder setIdent(String firstIdent, String ident) {
    return codeBuilder.setIdent(firstIdent, ident);
  }

  @Override
  public CodeBuilder setIdent(String firstIdent, String ident, int chars) {
    return codeBuilder.setIdent(firstIdent, ident, chars);
  }

  @Override
  public CodeBuilder increasedIdent(int increaseBy) {
    return codeBuilder.increasedIdent(increaseBy);
  }

  @Override
  public CodeBuilder increasedIdent(String ident, int increaseBy) {
    return codeBuilder.increasedIdent(ident, increaseBy);
  }

  @Override
  public CodeBuilder increasedIdent(String firstIdent, String ident, int increaseBy) {
    return codeBuilder.increasedIdent(firstIdent, ident, increaseBy);
  }

  @Override
  public CodeBuilder popIdent() {
    return codeBuilder.popIdent();
  }

  @Override
  public CodeBuilder addBind(BindVariable bind) {
    return codeBuilder.addBind(bind);
  }

  @Override
  public CodeBuilder addBinds(
      Collection<? extends BindVariable> binds) {
    return codeBuilder.addBinds(binds);
  }

  @Override
  public CodeBuilder addBindsWithPos(
      Collection<BindWithPos> binds) {
    return codeBuilder.addBindsWithPos(binds);
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
  public void appendLiteral(Object value) {
    this.applyString(stringBuilder -> sqlLiteralHandler.appendLiteral(stringBuilder, value));
  }

  @Override
  public void append(Function function,
      List<? extends Consumer<? super DefaultSqlBuilder>> argumentAppend) {
    sqlFunctionMap.append(function, argumentAppend, this);
  }

  @Override
  public void append(Element<?> element) {
    elementSqlBuilder.append(this, element);
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
