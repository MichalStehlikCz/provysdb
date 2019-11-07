package com.provys.provysdb.dbcontext;

public interface DbRowMapper<T> {
    T map(DbResultSet resultSet, long rowNumber);
}
