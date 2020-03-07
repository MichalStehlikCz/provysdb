package com.provys.provysdb.sqlbuilder.codebuilder;

import com.provys.provysdb.sqlbuilder.CodeIdent;

/**
 * Ident that has different prefix on the first line and subsequent lines. Note that unlike other
 * ident classes, this one is mutable (state indicates if the first line has been used already or
 * not)
 */
final class CodeIdentFirst implements CodeIdent {

  /**
   * Create ident manager with specified first and regular line prefixes. Typically used when
   * managing parameter lists, where starting on second line, , character is added
   *
   * @param firstIdent is ident (prefix) to be used on the first line
   * @param ident      is ident (prefix) to be used on all lines starting with second
   * @return new ident manager with required characteristics
   */
  static CodeIdentFirst of(String firstIdent, String ident) {
    return new CodeIdentFirst(firstIdent, ident);
  }

  private final String firstIdent;
  private final String ident;
  private boolean firstActive;

  private CodeIdentFirst(String firstIdent, String ident) {
    this.firstIdent = firstIdent;
    this.ident = ident;
    this.firstActive = true;
  }

  @Override
  public String get() {
    return firstActive ? firstIdent : ident;
  }

  @Override
  public void use(StringBuilder builder) {
    builder.append(get());
    firstActive = false;
  }

  @Override
  public CodeIdent copy() {
    var result = of(firstIdent, ident);
    if (!firstActive) {
      result.firstActive = false;
    }
    return result;
  }

  @Override
  public String toString() {
    return "CodeIdentFirst{"
        + "firstIdent='" + firstIdent + '\''
        + ", ident='" + ident + '\''
        + ", firstActive=" + firstActive
        + '}';
  }
}
