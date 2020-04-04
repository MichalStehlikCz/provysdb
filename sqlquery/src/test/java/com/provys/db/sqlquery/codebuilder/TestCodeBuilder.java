package com.provys.db.sqlquery.codebuilder;

import com.provys.common.exception.NotImplementedException;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.CodeIdent;
import com.provys.db.query.CodeIdentBuilder;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

class TestCodeBuilder implements CodeBuilder {

  private final StringBuilder builder = new StringBuilder();

  @Override
  public CodeBuilder append(String text) {
    builder.append(text);
    return this;
  }

  @Override
  public CodeBuilder append(char character) {
    builder.append(character);
    return this;
  }

  @Override
  public CodeBuilder append(int number) {
    builder.append(number);
    return this;
  }

  @Override
  public CodeBuilder append(BigInteger number) {
    builder.append(number);
    return this;
  }

  @Override
  public CodeBuilder append(CodeBuilder appendBuilder) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder apply(Consumer<? super CodeBuilder> appendFunction) {
    appendFunction.accept(this);
    return this;
  }

  @Override
  public CodeBuilder applyString(Consumer<? super StringBuilder> appendFunction) {
    appendFunction.accept(builder);
    return this;
  }

  @Override
  public CodeBuilder appendWrapped(String text, int additionalIdent) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder appendWrapped(String text) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder appendLine() {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder appendLine(String line) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeIdentBuilder identBuilder() {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder setIdent(CodeIdent ident) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder setIdent(String ident) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder setIdent(String ident, int chars) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder setIdent(String firstIdent, String ident) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder setIdent(String firstIdent, String ident, int chars) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder increasedIdent(int increaseBy) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder increasedIdent(String ident, int increaseBy) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder increasedIdent(String firstIdent, String ident, int increaseBy) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder popIdent() {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder addBind(BindVariable bind) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder addBinds(Collection<? extends BindVariable> binds) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public CodeBuilder addBindsWithPos(Collection<BindWithPos> binds) {
    throw new NotImplementedException(TestCodeBuilder.class);
  }

  @Override
  public String build() {
    return builder.toString();
  }

  @Override
  public Collection<BindWithPos> getBindsWithPos() {
    return Collections.emptyList();
  }

  @Override
  public Map<BindName, Object> getBindValues() {
    return Collections.emptyMap();
  }
}
