package com.provys.db.sqldb.query;

import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.db.query.elements.Element;
import com.provys.db.sqldb.codebuilder.CodeBuilder;

public interface SqlBuilder extends CodeBuilder {

  /**
   * Type map, used to generate literals.
   *
   * @return type map, defines how to translate value to sql literal
   */
  SqlTypeMap getSqlTypeMap();

  /**
   * Sql function map this statement factory uses for rendering sql built-in functions.
   *
   * @return function map used by this factory
   */
  SqlFunctionMap getSqlFunctionMap();

  /**
   * Append supplied element to this builder.
   *
   * @param element is element we want to append
   */
  void append(Element<?> element);
}
