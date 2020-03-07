package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used as basis for all "normal" SqlColumn types.
 *
 * @param <T> is type of variable represented by column
 */
abstract class SqlColumnBase<T> implements SqlColumn<T> {

  @Override
  public @Nullable SqlIdentifier getAlias() {
    return null;
  }

  @Override
  public Optional<SqlIdentifier> getOptAlias() {
    return Optional.empty();
  }

  @Override
  public SqlColumn<T> as(SqlIdentifier newAlias) {
    return SqlColumnAliasWrapper.of(this, newAlias);
  }

  @Override
  public String toString() {
    return "SqlColumnBase{}";
  }
}
