package com.provys.db.sqlquery.query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Select statement based on single column with defined type.
 *
 * @param <T1> is type of column 1
 */
public interface SelectStatementT1<T1> extends SelectStatement {

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched non-null value. Can only be used for query returning exactly one line. Close this
   * statement after fetch.
   *
   * @return value, retrieved by query
   */
  @NonNull T1 fetchNonNullOne();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line. Close this statement
   * after fetch.
   *
   * @return value, retrieved by query
   */
  @Nullable T1 fetchNullableOne();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line. Close this statement
   * after fetch.
   *
   * @return value, retrieved by query
   */
  Optional<T1> fetchOptionalOne();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values. Only usable to fetch from non-null column. Close this statement after fetch.
   *
   * @return list of fetched values
   */
  List<@NonNull T1> fetchNonNull();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values. Close this statement after fetch.
   *
   * @return list of fetched values
   */
  List<@Nullable T1> fetchNullable();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Only usable for non-null columns. Returned stream holds underlying
   * statement opened and should be closed explicitly. Close both resultset and this statement when
   * stream is closed
   *
   * @return stream with returned values
   */
  Stream<@NonNull T1> streamNonNull();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Returned stream holds underlying statement opened and should be
   * closed explicitly. Close both resultset and this statement when stream is closed
   *
   * @return stream with returned values
   */
  Stream<Optional<T1>> streamOptional();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched non-null value. Can only be used for query returning exactly one line
   *
   * @return value, retrieved by query
   */
  @NonNull T1 fetchNonNullOneNoClose();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line
   *
   * @return value, retrieved by query
   */
  @Nullable T1 fetchNullableOneNoClose();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched value. Can only be used for query returning exactly one line
   *
   * @return value, retrieved by query
   */
  Optional<T1> fetchOptionalOneNoClose();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values.
   *
   * @return list of fetched values
   */
  List<@NonNull T1> fetchNonNullNoClose();

  /**
   * Execute associated statement (using connection fetched from underlying DataSource) and return
   * fetched values.
   *
   * @return list of fetched values
   */
  List<@Nullable T1> fetchNullableNoClose();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Returned stream holds underlying resultset opened and should be
   * closed explicitly. Note that no further fetch / stream operations should be performed on this
   * statement until stream is closed
   *
   * @return stream with returned values
   */
  Stream<@NonNull T1> streamNonNullNoClose();

  /**
   * Execute associated statement (using connection, fetched from underlying DataSource) and return
   * stream of fetched values. Returned stream holds underlying resultset opened and should be
   * closed explicitly. Note that no further fetch / stream operations should be performed on this
   * statement until stream is closed
   *
   * @return stream with returned values
   */
  Stream<Optional<T1>> streamOptionalNoClose();
}
