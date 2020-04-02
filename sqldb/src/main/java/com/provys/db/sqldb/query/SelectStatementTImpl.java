package com.provys.db.sqldb.query;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.DbRowMapper;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindWithPos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class SelectStatementTImpl<S extends SelectStatementTImpl<S>> {

  private final String sqlText;
  private final DbPreparedStatement statement;
  private final Map<BindName, BindValue<?>> binds;

  /**
   * Owned connection. If selectStatement retrieved connection from context, it will be kept here
   * and returned to pool on close; when connection is passed to constructor, it is not held here
   * and thus not closed
   */
  private @Nullable DbConnection connection;
  private boolean closed = false;

  private SelectStatementTImpl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, ? extends @Nullable Object> bindValues,
      @SuppressWarnings("NullableProblems") DbConnection connection,
      boolean closeConnection) {
    this.sqlText = sqlText;
    if (closeConnection) {
      this.connection = connection;
    } else {
      this.connection = null;
    }
    this.statement = connection.prepareStatement(sqlText);
    this.binds = binds.stream()
        .collect(Collectors.toUnmodifiableMap(BindWithPos::getName,
            bindWithPos -> new BindValue<>(bindWithPos, bindWithPos.getType(),
                bindValues.get(bindWithPos.getName()))));
  }

  SelectStatementTImpl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, ? extends @Nullable Object> bindValues, DbConnection connection) {
    this(sqlText, binds, bindValues, connection, false);
  }

  SelectStatementTImpl(String sqlText, DbConnection connection) {
    this(sqlText, Collections.emptyList(), Collections.emptyMap(), connection, false);
  }

  SelectStatementTImpl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, ? extends @Nullable Object> bindValues, DbContext dbContext) {
    this(sqlText, binds, bindValues, dbContext.getConnection(), true);
  }

  SelectStatementTImpl(String sqlText, DbContext dbContext) {
    this(sqlText, Collections.emptyList(), Collections.emptyMap(), dbContext.getConnection(), true);
  }

  /**
   * Statement this select is based on.
   *
   * @return value of field statement
   */
  DbPreparedStatement getStatement() {
    return statement;
  }

  abstract S self();

  public S bindValue(BindName name, @Nullable Object value) {
    if (closed) {
      throw new InternalException("Attempt to bind value in closed statement " + this);
    }
    var oldValue = binds.get(name);
    if (oldValue == null) {
      throw new InternalException(
          "Bind variable with name " + name + " not found in statement " + this);
    }
    oldValue.setValue(value);
    return self();
  }

  public S bindValue(String name, @Nullable Object value) {
    return bindValue(BindName.valueOf(name), value);
  }

  public Collection<BindName> getBinds() {
    return Collections.unmodifiableCollection(binds.keySet());
  }

  private void bindValues() {
    for (var bind : binds.values()) {
      bind.bindValue(statement);
    }
  }

  public DbResultSet execute() {
    if (closed) {
      throw new InternalException("Attempt to execute closed statement " + this);
    }
    bindValues();
    try {
      return statement.executeQuery();
    } catch (SQLException e) {
      throw new InternalException("Error executing statement " + this, e);
    }
  }

  @SuppressWarnings("java:S2583") // Sonar does not evaluate assignment in exception handler
  public void close() {
    if (closed) {
      // repeated attempt to close statement should do nothing
      return;
    }
    closed = true;
    SQLException exception = null;
    try {
      statement.close();
    } catch (SQLException e) {
      // even if closing prepared statement failed, we will still try to close connection
      exception = e;
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        if (exception == null) {
          throw new InternalException("Error closing connection", e);
        }
      }
      connection = null;
    }
    if (exception != null) {
      throw new InternalException("Error closing prepared statement", exception);
    }
  }

  private static final class BindValue<T> {

    private final BindWithPos bindWithPos;
    private final Class<T> type;
    private @Nullable T value;
    /**
     * indicates if bind value has been modified after last time it has been bound to prepared
     * statement.
     */
    private boolean modified = true;

    private @Nullable T checkValue(@Nullable Object newValue) {
      if (newValue == null) {
        return null;
      }
      if (!type.isInstance(newValue)) {
        throw new InternalException(
            "Bind " + bindWithPos.getName() + " type " + type + " and value " + newValue
                + "mismatch");
      }
      return type.cast(newValue);
    }

    BindValue(BindWithPos bindWithPos, Class<T> type, @Nullable Object value) {
      if (bindWithPos.getType() != type) {
        throw new InternalException("Type mismatch between bind and declared type of bind value");
      }
      this.bindWithPos = bindWithPos;
      this.type = type;
      this.value = checkValue(value);
    }

    /**
     * Value of field bindWithPos.
     *
     * @return value of field bindWithPos
     */
    BindWithPos getBindWithPos() {
      return bindWithPos;
    }

    /**
     * Value of field value.
     *
     * @return value of field value
     */
    @Nullable Object getValue() {
      return value;
    }

    /**
     * Set value of field value.
     *
     * @param newValue is new value to be set
     */
    void setValue(@Nullable Object newValue) {
      if (!Objects.equals(value, newValue)) {
        value = checkValue(newValue);
        modified = true;
      }
    }

    /**
     * Value of field modified.
     *
     * @return value of field modified
     */
    boolean isModified() {
      return modified;
    }

    void bindValue(DbPreparedStatement statement) {
      if (modified) {
        for (var position : bindWithPos.getPositions()) {
          statement.setNullableValue(position, value, type);
        }
      }
    }

    @Override
    public String toString() {
      return "BindValue{"
          + "bindWithPos=" + bindWithPos
          + ", value=" + value
          + ", modified=" + modified
          + '}';
    }
  }

  public <T> @NonNull T fetchOneNoClose(DbRowMapper<? extends T> rowMapper) {
    try (var resultSet = execute()) {
      if (!resultSet.next()) {
        throw new InternalException("Exact fetch returned no rows" + this);
      }
      T result = rowMapper.map(resultSet, 0);
      if (resultSet.next()) {
        throw new InternalException("Exact fetch returned more than one row" + this);
      }
      return result;
    } catch (SQLException e) {
      throw new InternalException("Exception thrown by com.provys.db.sql statement " + this, e);
    }
  }

  public <T> List<@NonNull T> fetchNoClose(DbRowMapper<? extends T> rowMapper) {
    try (var resultSet = execute()) {
      List<@NonNull T> result = new ArrayList<>(10);
      long row = 0;
      while (resultSet.next()) {
        result.add(rowMapper.map(resultSet, row++));
      }
      return result;
    } catch (SQLException e) {
      throw new InternalException("Exception thrown by com.provys.db.sql statement " + this, e);
    }
  }

  public <T> @NonNull T fetchOne(DbRowMapper<? extends T> rowMapper) {
    try {
      return fetchOneNoClose(rowMapper);
    } finally {
      close();
    }
  }

  public <T> List<@NonNull T> fetch(DbRowMapper<? extends T> rowMapper) {
    try {
      return fetchNoClose(rowMapper);
    } finally {
      close();
    }
  }

  private void onCloseStream(ResultSet resultSet, boolean close) {
    // close both result and this statement
    Exception exception = null;
    try {
      resultSet.close();
    } catch (SQLException e) {
      exception = e;
    }
    if (close) {
      this.close();
    }
    if (exception != null) {
      throw new InternalException("Error fetching data from statement " + this, exception);
    }
  }

  private <T> Stream<@NonNull T> stream(DbRowMapper<? extends T> rowMapper, boolean close) {
    var resultSet = execute();
    return StreamSupport
        .stream(Spliterators.spliteratorUnknownSize(
            new DbResultSetIterator<T>(rowMapper, resultSet), Spliterator.ORDERED), false)
        .onClose(() -> onCloseStream(resultSet, close));
  }

  public <T> Stream<@NonNull T> stream(DbRowMapper<? extends T> rowMapper) {
    return stream(rowMapper, true);
  }

  public <T> Stream<@NonNull T> streamNoClose(DbRowMapper<? extends T> rowMapper) {
    return stream(rowMapper, false);
  }

  private static final class DbResultSetIterator<T> implements Iterator<T> {

    private final DbRowMapper<? extends T> rowMapper;
    private final DbResultSet resultSet;
    private long rowNumber = 0;
    private boolean finished = false;
    private @Nullable T next = null;

    DbResultSetIterator(DbRowMapper<? extends T> rowMapper, DbResultSet resultSet) {
      this.rowMapper = rowMapper;
      this.resultSet = resultSet;
    }

    private void fetch() {
      if (finished || (next != null)) {
        return;
      }
      try {
        if (resultSet.next()) {
          next = rowMapper.map(resultSet, rowNumber++);
        } else {
          finished = true;
        }
      } catch (SQLException e) {
        throw new InternalException("Error fetching data in stream", e);
      }
    }

    @Override
    public boolean hasNext() {
      fetch();
      return !finished;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      var result = Objects.requireNonNull(next); // safe after hasNext
      next = null;
      return result;
    }

    @Override
    public String toString() {
      return "DbResultSetIterator{"
          + "rowMapper=" + rowMapper
          + ", rowNumber=" + rowNumber
          + ", finished=" + finished
          + ", next=" + next
          + '}';
    }
  }

  @Override
  public String toString() {
    return "SelectStatementTImpl{"
        + "sqlText='" + sqlText + '\''
        + ", statement=" + statement
        + ", binds=" + binds
        + ", connection=" + connection
        + ", closed=" + closed
        + '}';
  }
}
