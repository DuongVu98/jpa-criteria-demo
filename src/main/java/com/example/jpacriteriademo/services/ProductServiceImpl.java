package com.example.jpacriteriademo.services;

import com.example.jpacriteriademo.entities.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;
import java.util.Locale;


@Service("criteria")
@RequiredArgsConstructor
@Slf4j(topic = "[ProductRepositoryCustomImpl]")
public class ProductServiceImpl implements ProductService {

    private final EntityManager entityManager;

    @Override
    public List<Product> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> productRoot = cq.from(Product.class);
        cq.select(productRoot);

        TypedQuery<Product> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public Product getByProductId(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> productRoot = cq.from(Product.class);
        cq.select(productRoot).where(cb.equal(productRoot.get("id"), id));

        TypedQuery<Product> query = entityManager.createQuery(cq);

        return query.getSingleResult();
    }

    @Override
    public Product getByProductIdEagerly(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);

        Root<Product> productRoot = cr.from(Product.class);

        productRoot.fetch("shop", JoinType.INNER);
        productRoot.fetch("category", JoinType.INNER);
        cr.select(productRoot).where(cb.equal(productRoot.get("id"), id));

        TypedQuery<Product> query = entityManager.createQuery(cr);

        return query.getSingleResult();
    }

    @Override
    public List<Product> getByNameSearch(String nameSearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);

        Root<Product> productRoot = cr.from(Product.class);
        cr.select(productRoot)
           .where(
              cb.like(
                 cb.lower(productRoot.get("name")),
                 "%" + nameSearch.toLowerCase(Locale.ROOT) + "%"
              )
           );

        TypedQuery<Product> query = entityManager.createQuery(cr);

        return query.getResultList();
    }

    @Override
    public List<Product> getByPriceRange(Long from, Long to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);

        Root<Product> productRoot = cr.from(Product.class);
        cr.select(productRoot).where(cb.between(productRoot.get("price"), from, to));

        TypedQuery<Product> query = entityManager.createQuery(cr);

        return query.getResultList();
    }

    @Override
    public List<Product> getByCategory(String category) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);

        Root<Product> productRoot = cr.from(Product.class);

        productRoot.join("category", JoinType.INNER);
        cr.select(productRoot)
           .where(cb.equal(productRoot.get("category").get("name"), category));

        TypedQuery<Product> query = entityManager.createQuery(cr);

        return query.getResultList();
    }


    @Override
    public List<Product> getByShop(String shop) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);

        Root<Product> productRoot = cr.from(Product.class);

        productRoot.join("shop", JoinType.INNER);
        cr.select(productRoot)
           .where(cb.equal(productRoot.get("shop").get("name"), shop));

        TypedQuery<Product> query = entityManager.createQuery(cr);

        return query.getResultList();
    }

    @Override
    public List<Product> getByShopMostExpensiveToCheapest(String shop) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);

        Root<Product> productRoot = cr.from(Product.class);

        productRoot.join("shop", JoinType.INNER);
        cr.select(productRoot)
           .where(cb.equal(productRoot.get("shop").get("name"), shop))
           .orderBy(cb.desc(productRoot.get("price")));

        TypedQuery<Product> query = entityManager.createQuery(cr);

        return query.getResultList();
    }

    @Override
    public Product getMostExpensiveProductFromShop(String shop) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);

        Root<Product> productRoot = cr.from(Product.class);

        Subquery<Long> subQuery = cr.subquery(Long.class);
        Root<Product> productSub = subQuery.from(Product.class);

        productSub.join("shop", JoinType.INNER);
        subQuery.select(cb.max(productSub.get("price")))
           .where(cb.equal(productSub.get("shop").get("name"), shop))
           .groupBy(productSub.get("shop").get("name"));

        productRoot.join("shop", JoinType.INNER);
        cr.select(productRoot).where(
           cb.and(
              cb.in(productRoot.get("price")).value(subQuery),
              cb.equal(productRoot.get("shop").get("name"), shop)
           )
        );

        TypedQuery<Product> query = entityManager.createQuery(cr);

        return query.getSingleResult();
    }
}
