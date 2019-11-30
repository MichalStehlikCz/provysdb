package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlparser.SqlSymbol;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

abstract class SqlBase extends SqlImpl implements DbSql {

    private final DbContext dbContext;

    SqlBase(DbContext dbContext, SqlTokenizer tokenizer) {
        super(tokenizer);
        this.dbContext = Objects.requireNonNull(dbContext);
    }

    SqlBase(DbContext dbContext) {
        this(dbContext, new DefaultSqlTokenizer());
    }

    @Nonnull
    DbContext getDbContext() {
        return dbContext;
    }

    @Nonnull
    @Override
    public SelectBuilder select() {
        return new SelectBuilderImpl(this);
    }

}
