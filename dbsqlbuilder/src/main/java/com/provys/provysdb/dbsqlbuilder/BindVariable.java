package com.provys.provysdb.dbsqlbuilder;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface BindVariable extends BindValue {

  /**
   * Return bind value with the same name and type, but with new value. Used as part of bind value
   * method. Specified object must be of the type compatible with bind variable
   *
   * @param newValue is value to be assigned to new bind
   * @return bind variable with the same name as old one, but with the new value
   */
  @Override
  BindVariable withValue(@Nullable Object newValue);

  /**
   * Method can be used when constructing statement and merging its parts. It combines two bind
   * values; they should have the same name and type. It verifies their values; if they have
   * different non-null values, exception is raised. Otherwise it uses one of variables to be
   * combined, preferring one with the value
   *
   * @param other is bind variable this variable should be combined with
   * @return this or other bind variable, depending which has more complete information
   */
  @Override
  BindVariable combine(BindName other);

  /**
   * Bind this variable to specified position in prepared statement.
   *
   * @param statement      is prepared statement value should be bound to
   * @param parameterIndex is index of parameter value should be bound to
   */
  void bind(DbPreparedStatement statement, int parameterIndex);
}
