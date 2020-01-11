package com.provys.provysdb.dbsqlbuilder.spring;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbsqlbuilder.impl.SqlAdminImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

@Configuration
public class DbSqlBuilderFactory {

    @Bean
    @Nonnull
    SqlAdminImpl getSqlAdmin(DbContext dbContext) {
        return new SqlAdminImpl(dbContext);
    }
}
