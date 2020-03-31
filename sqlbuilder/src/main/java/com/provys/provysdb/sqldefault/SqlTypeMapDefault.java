package com.provys.provysdb.sqldefault;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeAdapter;
import com.provys.db.dbcontext.SqlTypeMap;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlTypeMapDefault implements SqlTypeMap {

  @Override
  public <T> SqlTypeAdapter<T> getAdapter(Class<T> type) {
    throw new InternalException("No com.provys.db.sql type adapter found for class " + type);
  }

  @Override
  public int getSqlType(Class<?> type) {
    return 0;
  }

  /**
   * JDBC / database type associated with supplied Java type.
   *
   * @param type is Java type we ask about
   * @return name of type associated with this adapter (on JDBC level), empty optional for
   *     non-object types
   */
  @Override
  public Optional<String> getTypeName(Class<?> type) {
    return Optional.empty();
  }

  /**
   * Read value from result set. Throw exception when value in given column is null.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @param type        is Java type of column
   * @return read column value
   */
  @Override
  public <T> @NonNull T readNonnullValue(DbResultSet resultSet, int columnIndex, Class<T> type) {
    return null;
  }

  /**
   * Read value from result set. Throw exception when value in given column is null.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @param type        is Java type of column
   * @return read column value
   */
  @Override
  public <T> @NonNull T readNonnullValue(DbResultSet resultSet, String columnLabel, Class<T> type) {
    return null;
  }

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @param type        is Java type of column
   * @return read column value
   */
  @Override
  public <T> @Nullable T readNullableValue(DbResultSet resultSet, int columnIndex, Class<T> type) {
    return null;
  }

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @param type        is Java type of column
   * @return read column value
   */
  @Override
  public <T> @Nullable T readNullableValue(DbResultSet resultSet, String columnLabel,
      Class<T> type) {
    return null;
  }

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @param type        is Java type of column
   * @return read column value
   */
  @Override
  public <T> Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, int columnIndex,
      Class<T> type) {
    return Optional.empty();
  }

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @param type        is Java type of column
   * @return read column value
   */
  @Override
  public <T> Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, String columnLabel,
      Class<T> type) {
    return Optional.empty();
  }

  /**
   * Bind value to variable.
   *
   * @param statement      is prepared statement where value should be bind
   * @param parameterIndex is index of parameter whose value should be set
   * @param value          is value to be set
   * @param type           is Java type of bind variable
   */
  @Override
  public <T> void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable T value,
      Class<T> type) {

  }

  /**
   * Bind value to variable.
   *
   * @param statement      is prepared statement where value should be bind
   * @param parameterIndex is index of parameter whose value should be set
   * @param value          is value to be set
   */
  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex, Object value) {

  }

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @param type  is Java type of literal
   * @return string representing literal for given value
   */
  @Override
  public <T> String getLiteral(@Nullable T value, Class<T> type) {
    return null;
  }

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @return string representing literal for given value
   */
  @Override
  public String getLiteral(Object value) {
    return null;
  }
}
