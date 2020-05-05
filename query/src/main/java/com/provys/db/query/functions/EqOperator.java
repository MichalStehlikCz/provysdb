package com.provys.db.query.functions;

import com.google.errorprone.annotations.Immutable;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
class EqOperator extends BuiltInBase implements ConditionalOperatorInt {

  EqOperator(String name) {
    super(name);
  }

  @Override
  protected @Nullable String validateArgumentsInt(List<? extends Class<?>> callArguments) {
    if (callArguments.size() != 2) {
      return "exactly 2 arguments expected";
    }
    if (!callArguments.get(0).equals(callArguments.get(1))) {
      return "arguments must have same type";
    }
    return null;
  }

  @Override
  public String toString() {
    return "EqOperator{" + super.toString() + '}';
  }
}
