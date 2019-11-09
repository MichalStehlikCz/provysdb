package com.provys.provysdb.sqlbuilder;

import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import com.provys.provysdb.dbcontext.SqlTypeMap;

public interface SqlColumnT<T> extends SqlColumn, ExpressionT<T> {

    /**
     * Retrieve adapter for given class from adapter map
     */
    SqlTypeAdapter<T> getAdapter(SqlTypeMap typeMap);
}
