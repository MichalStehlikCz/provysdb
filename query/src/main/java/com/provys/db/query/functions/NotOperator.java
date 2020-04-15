package com.provys.db.query.functions;

import com.provys.common.datatype.DbBoolean;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

class NotOperator extends BuiltInBase implements ConditionalOperatorInt {

  NotOperator() {
    super("COND_NOT");
  }

  @Override
  protected @Nullable String validateArgumentsInt(List<? extends Class<?>> callArguments) {
    if (callArguments.isEmpty()) {
      return "argument expected";
    }
    if (callArguments.size() > 1) {
      return "single argument expected, " + callArguments.size() + " supplied";
    }
    if (callArguments.get(0) != DbBoolean.class) {
      return "DbBoolean argument expected, " + callArguments.get(0) + " found";
    }
    return null;
  }
}
