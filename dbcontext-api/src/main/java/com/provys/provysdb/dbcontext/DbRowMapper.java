package com.provys.provysdb.dbcontext;

/**
 * RowMapper is used to convert data record in {@link DbResultSet} to object.
 *
 * @param <T> is type of object {@code DbResultSet} record is converted to
 */
public interface DbRowMapper<T> {

  /**
   * Method that converts current row in supplied DbResultSet and current row number to object.
   *
   * @param resultSet is the source of data
   * @param rowNumber is current row number (sometimes it might be included in result object)
   * @return object created from current record in ResultSet
   */
  T map(DbResultSet resultSet, long rowNumber);
}
