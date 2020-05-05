package com.provys.db.sqlquery.query;

import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.DbRowMapper;
import com.provys.db.query.names.BindName;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Class corresponds to PreparedStatement, built from Statement. It allows to bind values to
 * variables, execute statement and access resulting data
 */
public interface SelectStatement extends AutoCloseable {

  /**
   * Collection of bind variables, associated with this statement.
   *
   * @return collection of bind variables, associated with this statement (and available for
   *     binding)
   */
  Collection<BindName> getBinds();

  /**
   * Bind value to variable with specified name.
   *
   * @param bind  is name of bind
   * @param value is supplied value
   * @return self to allow chaining
   */
  SelectStatement bindValue(String bind, @Nullable Object value);

  /**
   * Bind value to specified bind variable.
   *
   * @param bind  is bind variable
   * @param value is supplied value
   * @return self to allow chaining
   */
  SelectStatement bindValue(BindName bind, @Nullable Object value);

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * resulting ResultSet.
   *
   * @return ResultSet containing data, retrieved by query
   */
  DbResultSet execute();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource), fetch
   * single row and return it. Throw exception if statement returns no or more than one row. Close
   * statement after fetching the row
   *
   * @param rowMapper is mapper used to translate fetched values to target type
   * @param <T>       is type of value to be returned
   * @return fetched value
   */
  <T> T fetchOne(DbRowMapper<? extends T> rowMapper);

  /**
   * Execute associated statement (using connection fetched from underlying DataSource), fetch all
   * rows and return them. Close statement after fetching the rows
   *
   * @param rowMapper is mapper used to translate fetched values to target type
   * @param <T>       is type of value to be returned
   * @return fetched value
   */
  <T> List<T> fetch(DbRowMapper<? extends T> rowMapper);

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and stream
   * fetched rows. Resulting stream holds prepared statement and ResultSet resources and should be
   * properly closed once reading is finished; closing the stream closes underlying prepared
   * statement.
   *
   * @param rowMapper is mapper used to translate fetched values to target type
   * @param <T>       is type of value to be returned
   * @return fetched value
   */
  <T> Stream<T> stream(DbRowMapper<? extends T> rowMapper);

  /**
   * Execute associated statement (using connection fetched from underlying DataSource), fetch
   * single row and return it. Throw exception if statement returns no or more than one row.
   *
   * @param rowMapper is mapper used to translate fetched values to target type
   * @param <T>       is type of value to be returned
   * @return fetched value
   */
  <T> T fetchOneNoClose(DbRowMapper<? extends T> rowMapper);

  /**
   * Execute associated statement (using connection fetched from underlying DataSource), fetch all
   * rows and return them.
   *
   * @param rowMapper is mapper used to translate fetched values to target type
   * @param <T>       is type of value to be returned
   * @return fetched value
   */
  <T> List<T> fetchNoClose(DbRowMapper<? extends T> rowMapper);

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and stream
   * fetched rows. Resulting stream holds prepared statement and resultset resources and should be
   * properly closed once reading is finished; closing the stream closes resultset but does not
   * close this statement. Note that previous stream should be closed before next fetch command is
   * executed on statement
   *
   * @param rowMapper is mapper used to translate fetched values to target type
   * @param <T>       is type of value to be returned
   * @return fetched value
   */
  <T> Stream<T> streamNoClose(DbRowMapper<? extends T> rowMapper);

  /**
   * Close statement. Will close underlying connection if it was initialized internally and not
   * supplied to constructor.
   */
  @Override
  void close();
}
