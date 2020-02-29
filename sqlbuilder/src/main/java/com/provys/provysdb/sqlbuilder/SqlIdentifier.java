package com.provys.provysdb.sqlbuilder;

/**
 * Valid name of sql object. Supports simple name (text starting with letter and containing letters,
 * numbers and characters _, # and $), that is translated to lowercase or quoted name (enclosed in
 * ", containing any printable character, case sensitive)
 */
public interface SqlIdentifier {

  /**
   * Textual representation of identifier.
   *
   * @return textual representation of name (e.g. name itself, including delimiter)
   */
  String getText();

  /**
   * Database catalogue version of identifier. Ordinary name upper-cased, without delimiters, ...
   *
   * @return name as represented in database catalogue
   */
  String getDbName();
}
