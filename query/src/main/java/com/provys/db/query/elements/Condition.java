package com.provys.db.query.elements;

import com.provys.common.datatype.DbBoolean;
import com.provys.db.query.names.BindMap;

public interface Condition extends Expression<DbBoolean> {

  @Override
  Condition mapBinds(BindMap bindMap);
}
