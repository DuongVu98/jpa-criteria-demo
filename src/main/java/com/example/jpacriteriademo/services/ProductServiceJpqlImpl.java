package com.example.jpacriteriademo.services;

import com.example.jpacriteriademo.entities.Product;
import com.example.jpacriteriademo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jpql")
@RequiredArgsConstructor
public class ProductServiceJpqlImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getByProductId(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product getByProductIdEagerly(Long id) {
        return productRepository.findFetchById(id).get();
    }

    @Override
    public List<Product> getByNameSearch(String nameSearch) {
        return this.productRepository.findByNameSearch("%" + nameSearch + "%");
    }

    @Override
    public List<Product> getByPriceRange(Long from, Long to) {
        return this.productRepository.findByPriceBetween(from, to);
    }

    @Override
    public List<Product> getByCategory(String category) {
        return this.productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getByShop(String shop) {
        return this.productRepository.findByShop(shop);
    }

    @Override
    public List<Product> getByShopMostExpensiveToCheapest(String shop) {
        return this.productRepository.findByShopMostExpensiveToCheapest(shop);
    }

    @Override
    public Product getMostExpensiveProductFromShop(String shop) {
        return this.productRepository.findTheMostExpensiveFromShop(shop);
    }
}
