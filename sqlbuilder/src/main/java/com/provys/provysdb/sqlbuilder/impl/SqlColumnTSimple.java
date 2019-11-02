package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;

import javax.annotation.Nullable;

public class SqlColumnTSimple<T> extends SqlColumnSimple implements SqlColumnT<T> {
    SqlColumnTSimple(@Nullable SqlTableAlias tableAlias, SqlIdentifier column, @Nullable SqlIdentifier alias) {
        super(tableAlias, column, alias);
    }
}
