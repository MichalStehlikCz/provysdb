package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NoDbSqlImpl extends SqlImpl implements NoDbSql {

    NoDbSqlImpl(SqlTokenizer tokenizer) {
        super(tokenizer);
    }

    public NoDbSqlImpl() {
        this(new DefaultSqlTokenizer());
    }

    @Nonnull
    @Override
    public SelectBuilderT0 select() {
        return new SelectBuilderT0Impl<>(this);
    }
}
