package com.provys.provysdb.sqlbuilder.codebuilder;

import com.provys.provysdb.sqlbuilder.CodeIdent;

/**
 * This ident does exactly nothing when used - it can be used as default ident.
 */
final class CodeIdentVoid implements CodeIdent {

  private static final CodeIdentVoid INSTANCE = new CodeIdentVoid();

  /**
   * (The only) instance of {@code CodeIdentVoid}.
   *
   * @return instance of {@code CodeIdentVoid}
   */
  static CodeIdentVoid getInstance() {
    return INSTANCE;
  }

  /**
   * Static getInstance method should be used instead of constructor.
   */
  private CodeIdentVoid() {
  }

  @Override
  public String get() {
    return "";
  }

  @Override
  public void use(StringBuilder builder) {
    // does nothing and it is exactly what we want
  }

  @Override
  public CodeIdent copy() {
    // is non-mutable thus can return self
    return this;
  }

  @Override
  public String toString() {
    return "CodeIdentVoid{}";
  }
}
