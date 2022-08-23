package com.example.jpacriteriademo.services;

import com.example.jpacriteriademo.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getByShop(String shopName);
}
