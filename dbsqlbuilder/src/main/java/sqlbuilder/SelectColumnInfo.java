package sqlbuilder;

import com.provys.provysdb.sql.SimpleName;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Information about column, useful for preparation to receive query results.
 */
public interface SelectColumnInfo {

  /**
   * Java type, corresponding to this expression's type.
   *
   * @return Java type this column should be mapped to. Used to find proper adapter for value
   *     retrieval
   */
  Class<?> getType();

  /**
   * Alias this column is associated with.  Note that if it is simple column, its name is also used
   * as alias
   *
   * @return alias this column is associated with. Note that if it is simple column, its name is
   *     also used as alias
   */
  @Nullable SimpleName getAlias();

  /**
   * Alias this column is associated with, Optional version.
   *
   * @return alias this column is associated with, empty optional if alias is absent
   */
  default Optional<SimpleName> getOptAlias() {
    return Optional.ofNullable(getAlias());
  }
}
