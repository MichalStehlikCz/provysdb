package com.provys.db.querybuilder;

/**
 * Simplest variant of select builder interface. This variant has no information about selected
 * columns and their types.
 */
public interface SelectBuilder extends SelectBuilderGen<SelectBuilder> {

  /**
   * Create new empty select query builder.
   *
   * @return new select query builder
   */
  SelectBuilderT0 select();
}
