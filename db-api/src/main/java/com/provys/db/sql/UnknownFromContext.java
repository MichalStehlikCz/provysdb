package com.provys.db.sql;

import com.provys.common.exception.InternalException;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used when select statement is used in expression or condition, but target context for given
 * expression / condition is not known.
 */
final class UnknownFromContext implements FromContext {

  private static final UnknownFromContext INSTANCE = new UnknownFromContext();

  static UnknownFromContext getInstance() {
    return INSTANCE;
  }

  private UnknownFromContext() {
  }

  @Override
  public @Nullable FromElement getFromElement(NamePath alias) {
    return null;
  }

  @Override
  public NamePath getDefaultAlias(FromElement fromElement) {
    throw new InternalException("Specified from element is not valid for this context");
  }
}
