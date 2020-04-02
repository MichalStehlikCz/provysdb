package com.provys.db.sqldb.codebuilder;

/**
 * Enables ident to use prefix from previous ident and mark its first line as used. Note that linked
 * ident itself is not stateful, but underlying idents might be stateful, meaning linked ident
 * itself gets stateful
 */
final class CodeIdentLinked implements CodeIdent {

  /**
   * Create new linked ident.
   *
   * @param ident       is main ident (appended)
   * @param linkedIdent is previous ident, used as prefix
   * @return linked ident with ident linked to linkedIdent
   */
  static CodeIdentLinked of(CodeIdent ident, CodeIdent linkedIdent) {
    return new CodeIdentLinked(ident, linkedIdent);
  }

  private final CodeIdent ident;
  private final CodeIdent parent;

  private CodeIdentLinked(CodeIdent ident, CodeIdent parent) {
    this.ident = ident;
    this.parent = parent;
  }


  @Override
  public String get() {
    return parent.get() + ident.get();
  }

  @Override
  public void use(StringBuilder builder) {
    parent.use(builder);
    ident.use(builder);
  }

  @Override
  public CodeIdent copy() {
    var newIdent = ident.copy();
    var newParent = parent.copy();
    //noinspection ObjectEquality
    if ((newIdent == ident) && (newParent == parent)) {
      return this;
    }
    return of(ident.copy(), parent.copy());
  }

  @Override
  public String toString() {
    return "CodeIdentLinked{"
        + "ident=" + ident
        + ", parent=" + parent
        + '}';
  }
}
