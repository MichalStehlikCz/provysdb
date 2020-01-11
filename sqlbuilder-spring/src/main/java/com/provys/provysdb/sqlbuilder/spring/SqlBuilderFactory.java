package com.provys.provysdb.sqlbuilder.spring;

import com.provys.provysdb.sqlbuilder.impl.NoDbSqlImpl;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

@Configuration
class SqlBuilderFactory {

    @Bean
    @Nonnull
    NoDbSqlImpl getNoDbSql() {
        return new NoDbSqlImpl(new DefaultSqlTokenizer());
    }
}
