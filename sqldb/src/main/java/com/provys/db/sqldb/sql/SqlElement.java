package com.provys.db.sqldb.sql;

import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Element;

/**
 * Usually used on descendants ofs {@link Element}, adds ability to append statement to
 * {@link CodeBuilder}.
 */
public interface SqlElement {

  /**
   * Append expression to code builder. Used to append expression to its default place; some
   * elements might also have secondary place (e.g. join clause might want to add additional hint).
   * Such situations are solved by additional procedures, specific for given type of statement.
   *
   * @param builder is code builder to which com.provys.db.sql text should be appended
   */
  void append(CodeBuilder builder);
}
