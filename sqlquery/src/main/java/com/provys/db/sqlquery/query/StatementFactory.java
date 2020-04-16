package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Select;
import com.provys.db.query.elements.SelectT1;
import com.provys.db.query.elements.SelectT2;

/**
 * Basic interface, that allows creation of SelectStatement from Select. Holds references to
 * database context, function map and other settings, affecting creation of statements.
 */
public interface StatementFactory {

  /**
   * Build select statement from supplied select query.
   *
   * @param query is supplied query definition
   * @return select statement based on supplied query
   */
  SelectStatement getSelect(Select query);

  /**
   * Build select statement from supplied select query.
   *
   * @param query is supplied query definition
   * @return select statement based on supplied query
   * @param <T1> is type of first column
   */
  <T1> SelectStatementT1<T1> getSelect(SelectT1<T1> query);

  /**
   * Build select statement from supplied select query.
   *
   * @param query is supplied query definition
   * @return select statement based on supplied query
   * @param <T1> is type of first column
   * @param <T2> is type of second column
   */
  <T1, T2> SelectStatementT2<T1, T2> getSelect(SelectT2<? extends T1, ? extends T2> query);
}
