package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DbBoolean;
import com.provys.db.query.names.BindMap;

@Immutable
public interface Condition extends Expression<DbBoolean> {

  @Override
  Condition mapBinds(BindMap bindMap);
}
