package com.provys.provysdb.dbcontext.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProvysDbConfigurationImplTest {


    @Inject
    ProvysDbConfiguration dbConfiguration;

    @Test
    void getUrlTest() {
        assertThat(dbConfiguration.getUrl()).isEqualTo("testurl");
    }

    @Test
    void getUserTest() {
        assertThat(dbConfiguration.getUser()).isEqualTo("testuser");
    }

    @Test
    void getPwdTest() {
        assertThat(dbConfiguration.getPwd()).isEqualTo("testpwd");
    }

    @Test
    void getMinPoolSizeTest() {
        assertThat(dbConfiguration.getMinPoolSize()).isEqualTo(5);
    }

    @Test
    void getMaxPoolSizeTest() {
        assertThat(dbConfiguration.getMaxPoolSize()).isEqualTo(20);
    }
}