package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;

import javax.annotation.Nullable;
import java.util.List;

public class SqlColumnTSql<T> extends SqlColumnSql implements SqlColumnT<T> {
    SqlColumnTSql(String sql, @Nullable SqlIdentifier alias) {
        super(sql, alias);
    }

    SqlColumnTSql(String sql, @Nullable SqlIdentifier alias, List<BindName> binds) {
        super(sql, alias, binds);
    }

}
