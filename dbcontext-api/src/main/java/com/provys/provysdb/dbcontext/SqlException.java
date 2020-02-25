package com.provys.provysdb.dbcontext;

import com.provys.common.exception.ProvysException;
import java.sql.SQLException;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents internal exception during work with JDBC. Usually masks {@link SQLException}. This
 * exception can be used in internal checks, for example when data are being bound to sql variables,
 * but should not be used outside database access layer.
 */
public final class SqlException extends ProvysException {

  private static final String NAME_NM = "JAVA_SQL_EXCEPTION";

  /**
   * Constructs a new PROVYS Sql exception with the specified detail message, parameters and cause.
   * Note that the detail message associated with {@code cause} is not automatically incorporated in
   * this runtime exception's detail message.
   *
   * @param message the detail message; displayed to user if translations via database are not
   *                available. Message is prefixed with internal name
   * @param params  is list of parameter and their values that can be embedded in error message
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()}
   *                method). (A @{code null} value is permitted, and indicates that the cause is
   *                nonexistent or unknown.)
   */
  public SqlException(String message, @Nullable Map<String, String> params,
      @Nullable Throwable cause) {
    super(message, params, cause);
  }

  /**
   * Constructs a new PROVYS Sql exception with the specified detail message and parameters.
   *
   * @param message the detail message; displayed to user if translations via database are not
   *                available. Message is prefixed with internal name
   * @param params  is list of parameter and their values that can be embedded in error message
   */
  public SqlException(String message, @Nullable Map<String, String> params) {
    this(message, params, null);
  }

  /**
   * Constructs a new PROVYS Sql exception with the specified detail message and cause. Note that
   * the detail message associated with {@code cause} is not automatically incorporated in this
   * runtime exception's detail message.
   *
   * @param message the detail message; displayed to user if translations via database are not
   *                available. Message is prefixed with internal name
   * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()}
   *                method). (A @{code null} value is permitted, and indicates that the cause is
   *                nonexistent or unknown.)
   */
  public SqlException(String message, @Nullable Throwable cause) {
    this(message, null, cause);
  }

  /**
   * Constructs a new PROVYS Sql exception with the specified detail message.
   *
   * @param message the detail message; displayed to user if translations via database are not
   *                available. Message is prefixed with internal name
   */
  public SqlException(String message) {
    this(message, (Throwable) null);
  }

  @Override
  public String getNameNm() {
    return NAME_NM;
  }
}