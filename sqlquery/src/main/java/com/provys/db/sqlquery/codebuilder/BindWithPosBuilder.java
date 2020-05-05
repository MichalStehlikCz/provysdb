package com.provys.db.sqlquery.codebuilder;

import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindWithPos;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Class used internally by CodeBuilder to build lists of BindWithPos and bind values for statement.
 */
final class BindWithPosBuilder {

  private final BindVariableBuilder bindVariableBuilder;
  private final List<Integer> positions;

  BindWithPosBuilder(BindName name, Class<?> type) {
    this.bindVariableBuilder = new BindVariableBuilder(name, type);
    positions = new ArrayList<>(3);
  }

  BindWithPosBuilder(BindVariable bindVariable) {
    this.bindVariableBuilder = new BindVariableBuilder(bindVariable);
    positions = new ArrayList<>(3);
  }

  /**
   * Value of field positions.
   *
   * @return value of field positions
   */
  List<Integer> getPositions() {
    return positions;
  }

  /**
   * Name of bind variable.
   *
   * @return name of bind variable
   */
  BindName getName() {
    return bindVariableBuilder.getName();
  }

  /**
   * Type of bind variable.
   *
   * @return type of bind variable
   */
  Class<?> getType() {
    return bindVariableBuilder.getType();
  }

  /**
   * Value of bind variable.
   *
   * @return value of bind variable
   */
  @Nullable Object getValue() {
    return bindVariableBuilder.getValue();
  }

  /**
   * Add supplied position to list of positions.
   *
   * @param position is one-based position to be added
   */
  BindWithPosBuilder addPosition(Integer position) {
    positions.add(position);
    return this;
  }

  void combineType(Class<?> newType) {
    bindVariableBuilder.combineType(newType);
  }

  void combineValue(@Nullable Object newValue) {
    bindVariableBuilder.combineValue(newValue);
  }

  void combineVariable(BindVariable bindVariable) {
    bindVariableBuilder.combineVariable(bindVariable);
  }

  BindWithPos build() {
    return new BindWithPos(bindVariableBuilder.getName(), bindVariableBuilder.getType(), positions);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BindWithPosBuilder that = (BindWithPosBuilder) o;
    return bindVariableBuilder.equals(that.bindVariableBuilder)
        && positions.equals(that.positions);
  }

  @Override
  public int hashCode() {
    int result = bindVariableBuilder.hashCode();
    result = 31 * result + positions.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "BindWithPosBuilder{"
        + "bindVariableBuilder=" + bindVariableBuilder
        + ", positions=" + positions
        + '}';
  }
}
