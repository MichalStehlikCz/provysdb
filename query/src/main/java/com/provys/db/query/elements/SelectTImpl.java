package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.provys.db.query.names.BindMap;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("AbstractClassExtendsConcreteClass")
abstract class SelectTImpl extends RegularFromContext {

  @JsonProperty("WHERE")
  private final @Nullable Condition whereClause;

  SelectTImpl(FromClause fromClause, @Nullable Condition whereClause,
      @Nullable FromContext parentContext, @Nullable BindMap bindMap) {
    super(fromClause, parentContext, bindMap);
    if (whereClause == null) {
      this.whereClause = null;
    } else {
      this.whereClause = (bindMap == null) ? whereClause : whereClause.mapBinds(bindMap);
    }
  }

  public @Nullable Condition getWhereClause() {
    return whereClause;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    SelectTImpl selectT = (SelectTImpl) o;
    return Objects.equals(whereClause, selectT.whereClause);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (whereClause != null ? whereClause.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SelectTImpl{"
        + "whereClause=" + whereClause
        + ", " + super.toString() + '}';
  }
}
