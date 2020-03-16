package com.provys.db.sql;

/**
 * Ident builder, supports creation of common idents. Can be retrieved from CodeBuilder
 */
public interface CodeIdentBuilder {

  /**
   * Set ident (used either on all lines or starting from second line, depending if firstIdent is
   * set).
   *
   * @param newIdent is ident to be used in new ident
   * @return self to support fluent build
   */
  CodeIdentBuilder setIdent(String newIdent);

  /**
   * Set specific ident for the first line.
   *
   * @param newFirstIdent is ident to be used for the first line in new ident
   * @return self to support fluent build
   */
  CodeIdentBuilder setFirstIdent(String newFirstIdent);

  /**
   * Link new ident to specified (previous) ident. Linking means that new ident will be appended to
   * previous ident and supplied ident will be used on each line together with new ident
   *
   * @param newPreviousIdent is ident that will be used as prefix
   * @return self to support fluent build
   */
  CodeIdentBuilder linkedIdent(CodeIdent newPreviousIdent);

  /**
   * Build ident with specified characteristics.
   *
   * @return ident built in this builder
   */
  CodeIdent build();
}
