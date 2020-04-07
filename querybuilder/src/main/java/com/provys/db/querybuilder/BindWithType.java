package com.provys.db.querybuilder;

import com.provys.provysdb.sql.BindName;

public interface BindWithType {

  /**
   * Bind name.
   *
   * @return bind name
   */
  BindName getBindName();

  /**
   * Bind name as string.
   *
   * @return bind name as string
   */
  String getName();

  /**
   * Java type, corresponding to this bind variable's type.
   *
   * @return Java type this bind can takes value of
   */
  Class<?> getType();
}
