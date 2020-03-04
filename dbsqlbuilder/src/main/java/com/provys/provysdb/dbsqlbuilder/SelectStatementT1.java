package com.provys.provysdb.dbsqlbuilder;

import java.util.List;
import java.util.stream.Stream;

/**
 * Select statement based on single column with defined type.
 *
 * @param <T1> is type of column 1
 */
public interface SelectStatementT1<T1> extends SelectStatement {

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line. Close this statement
   * after fetch.
   *
   * @return value, retrieved by query
   */
  T1 fetchOne();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values. Close this statement after fetch.
   *
   * @return list of fetched values
   */
  List<T1> fetch();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Returned stream holds underlying statement opened and should be
   * closed explicitly. Close both resultset and this statement when stream is closed
   *
   * @return stream with returned values
   */
  Stream<T1> stream();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line
   *
   * @return value, retrieved by query
   */
  T1 fetchOneNoClose();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values.
   *
   * @return list of fetched values
   */
  List<T1> fetchNoClose();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Returned stream holds underlying resultset opened and should be
   * closed explicitly. Note that no further fetch / stream operations should be performed on this
   * statement until stream is closed
   *
   * @return stream with returned values
   */
  Stream<T1> streamNoClose();
}
