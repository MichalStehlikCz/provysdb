package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Element;
import com.provys.db.query.elements.Function;
import com.provys.db.sqldb.codebuilder.CodeBuilder;
import java.util.List;
import java.util.function.Consumer;

public interface SqlBuilder<B extends SqlBuilder<B>> extends CodeBuilder {

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
   * @param builder is {@code StringBuilder} literal should be appended to
   * @param value is value literal should be created for
   */
  void appendLiteral(StringBuilder builder, Object value);

  /**
   * Append evaluated template to builder.
   *
   *  @param function is function to be applied
   * @param argumentAppend is procedure that appends given argument to builder
   */
  void append(Function function, List<? extends Consumer<? super B>> argumentAppend);

  /**
   * Append supplied element to this builder.
   *
   * @param element is element we want to append
   */
  void append(Element<?> element);

  /**
   * Get empty clone of this builder. Van be used to assemble part of statement (for example
   * evaluate function arguments)
   *
   * @return empty clone of this builder
   */
  B getClone();
}
