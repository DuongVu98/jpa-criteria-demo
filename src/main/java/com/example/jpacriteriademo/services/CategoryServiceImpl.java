package com.example.jpacriteriademo.services;

import com.example.jpacriteriademo.entities.Category;
import com.example.jpacriteriademo.entities.Product;
import com.example.jpacriteriademo.entities.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final EntityManager entityManager;

    @Override
    public List<Category> getByShop(String shopName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> cr = cb.createQuery(Category.class);

        Root<Category> categoryRoot = cr.from(Category.class);
        Join<Category, Product> categoryProductJoin = categoryRoot.join("products", JoinType.INNER);
        Join<Product, Shop> productShopJoin = categoryProductJoin.join("shop", JoinType.INNER);

        categoryRoot.join("products", JoinType.INNER).join("shop", JoinType.INNER);
        cr.select(categoryRoot)
           .distinct(true)
           .where(cb.equal(productShopJoin.get("name"), shopName));

        TypedQuery<Category> query = entityManager.createQuery(cr);

        return query.getResultList();
    }
}
