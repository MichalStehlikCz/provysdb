package com.provys.provysdb.dbcontext.spring;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbcontext.impl.ProvysConnectionPoolDataSource;
import com.provys.provysdb.dbcontext.impl.ProvysConnectionPoolDataSourceImpl;
import com.provys.provysdb.dbcontext.impl.ProvysDbConfiguration;
import com.provys.provysdb.dbcontext.impl.ProvysDbContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbContextFactory {

    @Bean
    ProvysConnectionPoolDataSource provysDbDataSource(ProvysDbConfiguration dbConfiguration) {
        return new ProvysConnectionPoolDataSourceImpl(dbConfiguration);
    }

    @Bean
    DbContext provysDbContext(ProvysConnectionPoolDataSource provysDataSource) {
        return new ProvysDbContext(provysDataSource);
    }
}
