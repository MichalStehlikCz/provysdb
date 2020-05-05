package com.provys.db.query.functions;

import com.provys.common.datatype.DbBoolean;

final class RepeatingConditionalOperator extends RepeatingBuiltInBase
    implements ConditionalOperatorInt {

  RepeatingConditionalOperator(String name) {
    super(name, DbBoolean.class, DbBoolean.class);
  }
}
