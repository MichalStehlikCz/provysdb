package com.provys.db.sqldb.query;

import com.provys.db.dbcontext.DbContext;
import com.provys.db.query.elements.Element;
import com.provys.db.query.elements.Select;

/**
 * Basic class, that allows creation of SelectStatement from Select. Holds references to database
 * context, function map and other settings, affecting creation of statements.
 */
public interface StatementFactory<F extends StatementFactory<F>> {

  /**
   * Database context this factory uses to prepare statements.
   *
   * @return database con text factory is used for
   */
  DbContext getDbContext();

  /**
   * Sql function map this statement factory uses for rendering sql built-in functions.
   *
   * @return function map used by this factory
   */
  SqlFunctionMap getSqlFunctionMap();

  /**
   * Append supplied element to provided sql builder.
   *
   * @param sqlBuilder is sql builder we want to append element to
   * @param element is element we want to append
   * @param <T>        is type of element
   */
  <T extends Element<?>> void append(SqlBuilder<? extends F, ?, ?> sqlBuilder, T element);

  /**
   * Build select statement from supplied select element.
   *
   * @param query is supplied query definition
   * @return select statement based on supplied query
   */
  SelectStatement getSelect(Select query);
}
