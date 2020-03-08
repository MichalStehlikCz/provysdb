package com.provys.provysdb.sqlbuilder;

/**
 * Valid name of sql object. Supports simple name (text starting with letter and containing letters,
 * numbers and characters _, # and $), that is translated to lowercase or quoted name (enclosed in
 * ", containing any printable character, case sensitive).
 */
public interface Identifier extends SpecPath {

  /**
   * Database catalogue version of identifier. Ordinary name upper-cased, without delimiters. Can be
   * used for uniqueness checks.
   *
   * @return name as represented in database catalogue
   */
  String getDbName();
}
