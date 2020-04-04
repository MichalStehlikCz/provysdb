package com.provys.db.sqlquery.query;

import com.provys.db.dbcontext.DbContext;
import com.provys.db.query.elements.Select;

/**
 * Builder capable of building Select statements. It can be used either to append select statement
 *
 * @param <B> is type of Sql builder, supported by this select statement builder
 * @param <S> is type of select, supported by this select statement builder
 * @param <T> is type of select statement that will be produced by this select statement builder
 */
public interface SelectTypeSqlBuilder<B extends SqlBuilder<?>, S extends Select,
    T extends SelectStatement> extends ElementTypeSqlBuilder<B, S> {

  /**
   * Build select statement and produce {@link SelectStatement} object usable to execute this
   * statement.
   *
   * @param sqlBuilder will be used to build text and binds for the statement. It is strongly
   *                  recommended to provide empty builder and dispose it after usage
   * @param select is select statement that is to be built
   * @param dbContext is database context, providing database connection for statement. Note that
   *                 type map associated with dbContext is NOT used - building will use type map
   *                 associated with builder. Caller is responsible for ensuring that database
   *                 context and builder are compatible
   * @return created select statement
   */
  T build(B sqlBuilder, S select, DbContext dbContext);
}
