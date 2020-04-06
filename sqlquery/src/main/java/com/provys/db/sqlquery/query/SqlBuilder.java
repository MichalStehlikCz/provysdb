package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Element;
import com.provys.db.query.elements.Function;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import com.provys.db.query.names.NamePath;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import com.provys.db.sqlquery.codebuilder.CodeIdent;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface SqlBuilder<B extends SqlBuilder<B>> extends CodeBuilder {

  @Override
  B append(String text);

  @Override
  B append(char character);

  @Override
  B append(int number);

  @Override
  B append(BigInteger number);

  @Override
  B append(NamePath name);

  @Override
  B append(CodeBuilder appendBuilder);

  @Override
  B apply(Consumer<? super CodeBuilder> appendFunction);

  @Override
  B applyString(Consumer<? super StringBuilder> appendFunction);

  @Override
  B appendWrapped(String text, int additionalIdent);

  @Override
  B appendWrapped(String text);

  @Override
  B appendLine();

  @Override
  B appendLine(String line);

  @Override
  B setIdent(CodeIdent ident);

  @Override
  B setIdent(String ident);

  @Override
  B setIdent(String ident, int chars);

  @Override
  B setIdent(String firstIdent, String ident);

  @Override
  B setIdent(String firstIdent, String ident, int chars);

  @Override
  B increasedIdent(int increaseBy);

  @Override
  B increasedIdent(String ident, int increaseBy);

  @Override
  B increasedIdent(String firstIdent, String ident, int increaseBy);

  @Override
  B popIdent();

  @Override
  B addBind(BindVariable bind);

  @Override
  B addBinds(Collection<? extends BindVariable> binds);

  @Override
  B addBindsWithPos(Collection<BindWithPos> binds);

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @return string representing literal for given value
   */
  String getLiteral(Object value);

  /**
   * Append literal corresponding to given value.
   *
   * @param value is value literal should be created for
   */
  B appendLiteral(Object value);

  /**
   * Append evaluated template to builder.
   *
   *  @param function is function to be applied
   * @param argumentAppend is procedure that appends given argument to builder
   */
  B append(Function function, List<? extends Consumer<? super B>> argumentAppend);

  /**
   * Append supplied element to this builder.
   *
   * @param element is element we want to append
   */
  B append(Element<?> element);

  /**
   * Get empty clone of this builder. Can be used to assemble part of statement (for example
   * evaluate function arguments)
   *
   * @return empty clone of this builder
   */
  B getClone();
}
