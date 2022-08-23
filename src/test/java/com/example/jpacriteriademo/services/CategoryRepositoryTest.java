package com.example.jpacriteriademo.services;

import com.example.jpacriteriademo.BaseDataTest;
import com.example.jpacriteriademo.entities.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "[CategoryRepositoryTest]")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest extends BaseDataTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @Sql("/data.sql")
    void givenData_whenGetByAsus_thenReturnLapMonitorPhone() {
        List<Category> categories = categoryService.getByShop("Asus");

        Assertions.assertEquals(3, categories.size());
        Assertions.assertTrue(
           categories.stream()
              .map(Category::getName).collect(Collectors.toList())
              .containsAll(List.of("Laptop", "Monitor", "Phone"))
        );
    }
}
