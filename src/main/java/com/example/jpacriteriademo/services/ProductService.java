package com.example.jpacriteriademo.services;

import com.example.jpacriteriademo.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getByProductId(Long id);

    Product getByProductIdEagerly(Long id);

    List<Product> getByNameSearch(String nameSearch);

    List<Product> getByPriceRange(Long from, Long to);

    List<Product> getByCategory(String category);

    List<Product> getByShop(String shop);

    List<Product> getByShopMostExpensiveToCheapest(String shop);

    Product getMostExpensiveProductFromShop(String shop);
}
