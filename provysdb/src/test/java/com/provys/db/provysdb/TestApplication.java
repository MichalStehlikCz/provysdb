package com.provys.db.provysdb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.provys")
@ConfigurationPropertiesScan(basePackages = "com.provys")
public class TestApplication {

}
