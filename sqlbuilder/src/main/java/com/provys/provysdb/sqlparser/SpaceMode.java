package com.provys.provysdb.sqlparser;

/**
 * Defines modes that are used to place space between tokens when rewriting token sequence to text.
 */
public enum SpaceMode {
  /**
   * Space is required on this position.
   */
  FORCE,
  /**
   * Space is used unless next to NONE mode token.
   */
  NORMAL,
  /**
   * Space is not required unless forced.
   */
  NONE,
  /**
   * Space is never used (used after newline)
   */
  FORCE_NONE
}
