package com.provys.provysdb.builder.sqlbuilder.spring;

import com.provys.provysdb.builder.sqlbuilder.impl.NoDbSqlImpl;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SqlBuilderFactory {

  @Bean
  NoDbSqlImpl getNoDbSql() {
    return new NoDbSqlImpl(new DefaultSqlTokenizer());
  }
}
