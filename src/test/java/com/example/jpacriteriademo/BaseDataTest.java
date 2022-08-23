package com.example.jpacriteriademo;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseDataTest {

    @Container
    public static PostgreSQLContainer<?> postgresDb =
       new PostgreSQLContainer<>("postgres:13.2")
          .withDatabaseName("jpa_demo_db")
          .withUsername("postgres")
          .withPassword("postgres");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDb::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDb::getUsername);
        registry.add("spring.datasource.password", postgresDb::getPassword);
    }
}
