package com.provys.db.dbcontext.spring;

import static org.assertj.core.api.Assertions.*;

import com.provys.db.provysdb.ProvysDbConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProvysDbConfigurationImplTest {

  private final ProvysDbConfiguration dbConfiguration;

  @Autowired
  ProvysDbConfigurationImplTest(ProvysDbConfiguration dbConfiguration) {
    this.dbConfiguration = dbConfiguration;
  }

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