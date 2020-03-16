package com.provys.provysdb.dbsqlbuilder.spring;

import com.provys.db.dbcontext.DbContext;
import com.provys.provysdb.dbsqlbuilder.impl.SqlAdminImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbSqlBuilderFactory {

  @Bean
  SqlAdminImpl getSqlAdmin(DbContext dbContext) {
    return new SqlAdminImpl(dbContext);
  }

  @Override
  public String toString() {
    return "DbSqlBuilderFactory{}";
  }
}
