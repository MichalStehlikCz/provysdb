package com.provys.db.sql;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Immutable data class, holding bind name and type together with positions it is used in statement.
 */
public final class BindWithPos {

  private final BindName name;
  private final Class<?> type;
  private final List<Integer> positions;

  /**
   * Create bind variable with positions.
   *
   * @param name is name of bind variable
   * @param type is java type, defining expected type of value variable can be assigned
   * @param positions is list of positions of this bind variable in statement
   */
  public BindWithPos(BindName name, Class<?> type, Collection<Integer> positions) {
    this.name = name;
    this.type = type;
    this.positions = List.copyOf(positions);
  }

  /**
   * Value of field name.
   *
   * @return value of field name
   */
  public BindName getName() {
    return name;
  }

  /**
   * Value of field type.
   *
   * @return value of field type
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Value of field positions.
   *
   * @return value of field positions
   */
  public List<Integer> getPositions() {
    return positions;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BindWithPos that = (BindWithPos) o;
    return Objects.equals(name, that.name)
        && type == that.type
        && Objects.equals(positions, that.positions);
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (positions != null ? positions.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "BindWithPos{"
        + "name=" + name
        + ", type=" + type
        + ", positions=" + positions
        + '}';
  }
}
