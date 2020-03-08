package com.provys.provysdb.sqlbuilder;

import java.util.List;

/**
 * Path to database object, consisting of multiple identifiers chained together. Identifier itself
 * is special case of identifier path.
 */
public interface SpecPath {

  /**
   * Textual representation of identifier(s) delimited by .
   *
   * @return textual representation of name (e.g. name itself, including delimiter)
   */
  String getText();

  /**
   * List of identifiers forming this path in their base (database) form.
   *
   * @return list of identifiers forming this path in their base (database) form
   */
  List<String> getDbNames();

  /**
   * Reports if other identifier path can match this path. True if other path matches item by item
   * this path's items from the end until we run out of other identifiers.
   *
   * @param other is path we verify match against ours
   * @return true if other can be used as reference to this, false otherwise
   */
  boolean match(SpecPath other);

  /**
   * Append path to code builder.
   * Should be equivalent of {@code builder.append(this.getText());}.
   *
   * @param builder is code builder path should be appended to
   */
  void append(CodeBuilder builder);
}
