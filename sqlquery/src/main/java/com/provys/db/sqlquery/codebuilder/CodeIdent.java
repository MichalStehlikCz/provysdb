package com.provys.db.sqlquery.codebuilder;

/**
 * Represents ident that is to be used for text when appending new lines to {@link CodeBuilder}.
 * Implementations of this interface can be mutable.
 */
public interface CodeIdent {

  /**
   * Current ident text.
   *
   * @return current ident text
   */
  String get();

  /**
   * Append current ident to supplied builder and mark it as used (e.g. deactivate first ident)
   *
   * @param builder is builder in which this ident is to be used
   */
  void use(StringBuilder builder);

  /**
   * Clone ident or return self in case of immutable ident.
   *
   * @return clone of this ident; can return self if given ident is not mutable
   */
  CodeIdent copy();
}
