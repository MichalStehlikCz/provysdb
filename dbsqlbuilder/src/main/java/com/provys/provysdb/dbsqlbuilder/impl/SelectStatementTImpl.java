package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.DbRowMapper;
import com.provys.provysdb.dbsqlbuilder.BindVariable;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.SqlFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class SelectStatementTImpl<S extends SelectStatementTImpl<S>> {

  private final String sqlText;
  private final DbPreparedStatement statement;
  private final Map<String, BindWithPos> binds;

  /**
   * Owned connection. If selectStatement retrieved connection from context, it will be kept here
   * and returned to pool on close; when connection is passed to constructor, it is not held here
   * and thus not closed
   */
  private @Nullable DbConnection connection;
  private boolean closed = false;

  private static Map<String, BindWithPos> prepareBinds(Collection<? extends BindName> binds) {
    // first construct list of positions for each supplied bind name
    int pos = 1;
    Map<BindName, List<Integer>> bindPosMap = new HashMap<>(binds.size());
    for (var bind : binds) {
      var posList = bindPosMap.computeIfAbsent(bind, val -> new ArrayList<>(3));
      posList.add(pos++);
    }
    // and then convert bind + list of positions to BindWithPos
    Map<String, BindWithPos> result = new HashMap<>(bindPosMap.size());
    for (var bindWithPos : bindPosMap.entrySet()) {
      result.put(bindWithPos.getKey().getName(),
          new BindWithPos(bindWithPos.getKey(), bindWithPos.getValue()));
    }
    return result;
  }

  private SelectStatementTImpl(String sqlText, Collection<? extends BindName> binds,
      @SuppressWarnings("NullableProblems") DbConnection connection, boolean closeConnection) {
    this.sqlText = sqlText;
    if (closeConnection) {
      this.connection = connection;
    } else {
      this.connection = null;
    }
    this.statement = connection.prepareStatement(sqlText);
    this.binds = prepareBinds(binds);
  }

  SelectStatementTImpl(String sqlText, Collection<? extends BindName> binds,
      DbConnection connection) {
    this(sqlText, binds, connection, false);
  }

  SelectStatementTImpl(String sqlText, Collection<? extends BindName> binds, DbSql sqlContext) {
    this(sqlText, binds, sqlContext.getConnection(), true);
  }

  SelectStatementTImpl(Select select, DbSql sqlContext) {
    this(select.getSqlText(), select.getBinds(), sqlContext);
  }

  SelectStatementTImpl(Select select, DbConnection connection) {
    this(select.getSqlText(), select.getBinds(), connection);
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

  public S bindValue(String bind, @Nullable Object value) {
    if (closed) {
      throw new InternalException("Attempt to bind value in closed statement " + this);
    }
    var oldValue = binds.get(bind);
    if (oldValue == null) {
      throw new InternalException(
          "Bind variable with name " + bind + " not found in statement " + this);
    }
    oldValue.setValue(value);
    return self();
  }

  @SuppressWarnings("TypeMayBeWeakened")
  public <T> S bindValue(BindValue<T> bind, @Nullable T value) {
    return bindValue(bind.getName(), value);
  }

  public Collection<BindName> getBinds() {
    return binds.values().stream().map(BindWithPos::getBind)
        .collect(Collectors.toUnmodifiableList());
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

  private static final class BindWithPos {

    private BindName bind;
    private final List<Integer> positions;
    /**
     * indicates if bind value has been modified after last time it has been bound to prepared
     * statement.
     */
    private boolean modified = true;

    BindWithPos(BindName bind, Collection<Integer> positions) {
      if (bind instanceof BindVariable) {
        this.bind = bind;
      } else if (bind instanceof BindValue) {
        this.bind = new BindVariableImpl<>((BindValue<?>) bind);
      } else {
        // we do not have the value... we store bind-name and wait for value
        this.bind = bind;
      }
      this.positions = List.copyOf(positions);
    }

    /**
     * Value of field bind.
     *
     * @return value of field bind
     */
    BindName getBind() {
      return bind;
    }

    /**
     * Set value to given bind.
     *
     * @param value is new value to be set
     */
    public void setValue(@Nullable Object value) {
      BindName combinedBind;
      if (value == null) {
        if (bind instanceof BindValue) {
          combinedBind = ((BindValue<?>) bind).withValue(null);
        } else {
          throw new InternalException(
              "Cannot bind null value to bind variable with unknown type " + bind);
        }
      } else {
        combinedBind = SqlFactory.bind(bind, value);
      }
      if (combinedBind == bind) {
        return;
      }
      bind = combinedBind;
      modified = true;
    }

    void bindValue(DbPreparedStatement statement) {
      if (!modified) {
        // if value has not been modified, we do not have to rebind it
        return;
      }
      if (!(bind instanceof BindVariable)) {
        throw new InternalException("Value not assigned to bind variable " + bind.getName());
      }
      BindVariable bindVariable = (BindVariable) bind;
      for (var position : positions) {
        bindVariable.bind(statement, position);
      }
      modified = false;
    }

    @Override
    public boolean equals(@Nullable Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      BindWithPos that = (BindWithPos) o;
      return modified == that.modified
          && Objects.equals(bind, that.bind)
          && Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
      int result = bind != null ? bind.hashCode() : 0;
      result = 31 * result + (positions != null ? positions.hashCode() : 0);
      result = 31 * result + (modified ? 1 : 0);
      return result;
    }

    @Override
    public String toString() {
      return "BindWithPos{"
          + "bind=" + bind
          + ", positions=" + positions
          + ", modified=" + modified
          + '}';
    }
  }

  public <T> T fetchOneNoClose(DbRowMapper<T> rowMapper) {
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
      throw new InternalException("Exception thrown by sql statement " + this, e);
    }
  }

  @SuppressWarnings("BoundedWildcard")
  public <T> List<T> fetchNoClose(DbRowMapper<T> rowMapper) {
    try (var resultSet = execute()) {
      List<T> result = new ArrayList<>(10);
      long row = 0;
      while (resultSet.next()) {
        result.add(rowMapper.map(resultSet, row++));
      }
      return result;
    } catch (SQLException e) {
      throw new InternalException("Exception thrown by sql statement " + this, e);
    }
  }

  public <T> T fetchOne(DbRowMapper<T> rowMapper) {
    try {
      return fetchOneNoClose(rowMapper);
    } finally {
      close();
    }
  }

  public <T> List<T> fetch(DbRowMapper<T> rowMapper) {
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

  private <T> Stream<T> stream(DbRowMapper<T> rowMapper, boolean close) {
    var resultSet = execute();
    return StreamSupport
        .stream(Spliterators.spliteratorUnknownSize(new DbResultSetIterator<>(rowMapper, resultSet),
            Spliterator.ORDERED), false)
        .onClose(() -> onCloseStream(resultSet, close));
  }

  public <T> Stream<T> stream(DbRowMapper<T> rowMapper) {
    return stream(rowMapper, true);
  }

  public <T> Stream<T> streamNoClose(DbRowMapper<T> rowMapper) {
    return stream(rowMapper, false);
  }

  private static final class DbResultSetIterator<T> implements Iterator<T> {

    private final DbRowMapper<T> rowMapper;
    private final DbResultSet resultSet;
    private long rowNumber = 0;
    private boolean finished = false;
    private @Nullable T next = null;

    DbResultSetIterator(DbRowMapper<T> rowMapper, DbResultSet resultSet) {
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
