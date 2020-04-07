package com.provys.db.sqlquery.query;

import java.util.List;
import java.util.stream.Stream;

public interface SelectStatementT2<T1, T2> extends SelectStatement {


  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line. Close this statement
   * after fetch.
   *
   * @return value, retrieved by query
   */
  TupleT2<T1, T2> fetchOne();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values. Close this statement after fetch.
   *
   * @return list of fetched values
   */
  List<TupleT2<T1, T2>> fetch();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Returned stream holds underlying statement opened and should be closed explicitly. Closes both ResultSet and this statement when stream is closed
   *
   * @return stream with returned values
   */
  Stream<TupleT2<T1, T2>> stream();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line
   *
   * @return value, retrieved by query
   */
  TupleT2<T1, T2> fetchOneNoClose();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values.
   *
   * @return list of fetched values
   */
  List<TupleT2<T1, T2>> fetchNoClose();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Returned stream holds underlying ResultSet opened and should be
   * closed explicitly. Note that no further fetch / stream operations should be performed on this
   * statement until stream is closed
   *
   * @return stream with returned values
   */
  Stream<TupleT2<T1, T2>> streamNoClose();
}
