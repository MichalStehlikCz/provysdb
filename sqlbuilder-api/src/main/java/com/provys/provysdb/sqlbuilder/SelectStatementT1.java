package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Select statement based n single column with defined type
 *
 * @param <T1> is type of column 1
 */
public interface SelectStatementT1<T1> extends SelectStatement {

    /**
     * Execute associated statement (using connection fetched from underlying DataSource) and return fetched value. Can
     * only be used on non-null value and for query returning exactly one line
     *
     * @return value, retrieved by query
     */
    @Nonnull
    T1 fetchOne();

    /**
     * Execute associated statement (using connection fetched from underlying DataSource) and return fetched value. Can
     * only be used even for null values and for query returning exactly one line
     *
     * @return value, retrieved by query
     */
    @Nonnull
    Optional<T1> fetchOneOptional();

    /**
     * Execute associated statement (using connection fetched from underlying DataSource) and return fetched values. Can
     * only be used on non-null value columns
     *
     * @return list of fetched values
     */
    @Nonnull
    List<T1> fetch();

    /**
     * Execute associated statement (using connection fetched from underlying DataSource) and return fetched values. Can
     * be used even on null value columns
     *
     * @return list of fetched values
     */
    @Nonnull
    List<Optional<T1>> fetchOptional();

    /**
     * Execute associated statement (using connection, fetched from underlying DataSource) and return stream of fetched
     * values. Can only be used on non-null value columns. Returned stream holds underlying statement opened and should
     * be closed explicitly
     *
     * @return stream with returned values
     */
    @Nonnull
    Stream<T1> stream();

    /**
     * Execute associated statement (using connection, fetched from underlying DataSource) and return stream of fetched
     * values. Can be used even on null value columns. Returned stream holds underlying statement opened and should
     * be closed explicitly
     *
     * @return stream with returned values
     */
    @Nonnull
    Stream<Optional<T1>> streamOptional();
}
