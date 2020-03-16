package com.provys.db.sqldb.sql.codebuilder;

import com.provys.db.sql.CodeIdent;

/**
 * Simple ident (all lines with the same prefix).
 */
final class CodeIdentSimple implements CodeIdent {

  static CodeIdentSimple of(String ident) {
    return new CodeIdentSimple(ident);
  }

  private final String ident;

  private CodeIdentSimple(String ident) {
    this.ident = ident;
  }

  @Override
  public String get() {
    return ident;
  }

  @Override
  public void use(StringBuilder builder) {
    builder.append(get());
  }

  @Override
  public CodeIdent copy() {
    /* non-mutable, thus can return itself */
    return this;
  }

  @Override
  public String toString() {
    return "CodeIdentSimple{"
        + "ident='" + ident + '\''
        + '}';
  }
}
