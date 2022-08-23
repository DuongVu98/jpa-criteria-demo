package com.example.jpacriteriademo.services;


import com.example.jpacriteriademo.BaseDataTest;
import com.example.jpacriteriademo.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "[ProductRepositoryTest]")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductServiceTest extends BaseDataTest {

    @Autowired
    @Qualifier("criteria")
    private ProductService productService;

    // TODO: Expressions
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenSomeData_whenGetAll_thenReturnAllProducts() {
        List<Product> products = productService.getAll();

        Assertions.assertEquals(23, products.size());
    }

    // TODO: Expressions
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenSomeData_whenGetById_thenReturnExactProduct() {
        Product product = productService.getByProductId(10L);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Zenbook S", product.getName());
    }

    // TODO: Expressions
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("When search for 'pro' then get Pro products")
    void givenSomeData_whenGetByProSearch_thenReturnProProduct() {
        List<Product> products = productService.getByNameSearch("pro");

        Assertions.assertEquals(3, products.size());
        Assertions.assertTrue(
           products.stream().map(Product::getName).collect(Collectors.toList())
              .containsAll(List.of("ROG Phone 6 Pro", "ProArt PA278CV", "K8 Pro"))
        );
    }

    // TODO: Join, Join Fetch
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenSomeData_whenGetByIdAndGetShopLazily_thenThrowException() {
        Product product = productService.getByProductId(10L);

        Assertions.assertNotNull(product);
        Assertions.assertThrows(LazyInitializationException.class, () -> product.getShop().getName());
        Assertions.assertThrows(LazyInitializationException.class, () -> product.getCategory().getName());
    }

    // TODO: Join, Join Fetch
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenSomeData_whenGetByIdAndGetShopEagerly_thenReturnShopNameAndCategoryName() {
        Product product = productService.getByProductIdEagerly(10L);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Asus", product.getShop().getName());
        Assertions.assertEquals("Laptop", product.getCategory().getName());
    }

    // TODO: Join, Join Fetch
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenSomeData_whenGetByKeyboard_thenReturnAllKeyboardProduct() {
        List<Product> products = productService.getByCategory("Keyboard");

        Assertions.assertEquals(5, products.size());
        Assertions.assertTrue(
           products.stream().map(Product::getName).collect(Collectors.toList())
              .containsAll(List.of("MX MECHANICAL", "MX KEYS MINI", "K8 Pro", "Huntsman V2", "K3 V2"))
        );
    }

    // TODO: Join, Join Fetch
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void givenSomeData_whenGetByMSI_thenReturn3Products() {
        List<Product> products = productService.getByShop("MSI");

        Assertions.assertEquals(3, products.size());
    }

    // TODO: Aggregations, Sorting
    @Test
    @Sql(value = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("I want to burn money")
    void givenVietlottPrize_whenBuyNewLaptop_thenBuyTheMostExpensive() {
        List<Product> products = productService.getByShopMostExpensiveToCheapest("MSI");
        Product mostExpensiveProduct = productService.getMostExpensiveProductFromShop("MSI");

        Assertions.assertEquals("GE76 Raider", mostExpensiveProduct.getName());
        Assertions.assertEquals(mostExpensiveProduct, products.get(0));
    }
}
