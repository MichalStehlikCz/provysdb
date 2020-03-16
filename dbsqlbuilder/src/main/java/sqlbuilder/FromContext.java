package sqlbuilder;

import com.provys.provysdb.sql.NamePath;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Interface represents information about from clause, available to other parts of query. It gives
 * list of query aliases and their columns (if available).
 */
public interface FromContext {

  /**
   * Retrieve identification of this from context (alias or table name).
   *
   * @return identification of this query in from clause
   */
  @Nullable NamePath getFromSpec();

  /**
   * Retrieve information of columns, present in given context.
   *
   * @return list of column information items
   */
  List<SelectColumnInfo> getColumnInfo();
}
