package com.example.jpacriteriademo.repository;

import com.example.jpacriteriademo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select p from Product p where upper(p.name) like upper(:name) order by p.id")
    List<Product> findByNameSearch(@Param("name") String name);

    @Query("select p from Product p where p.price between :priceStart and :priceEnd")
    List<Product> findByPriceBetween(@Param("priceStart") Long priceStart, @Param("priceEnd") Long priceEnd);

    @Query("select p from Product p where p.category.name = :name")
    List<Product> findByCategory(@Param("name") String name);

    @Query("select p from Product p where p.shop.name = :name")
    List<Product> findByShop(@Param("name") String name);

    @Query("select p from Product p where p.shop.name = :name order by p.price desc")
    List<Product> findByShopMostExpensiveToCheapest(@Param("name") String name);

    @Query("select p from Product p where p.shop.name = :name and p.price = (select max(p1.price) from Product p1 where p1.shop.name = :name)")
    Product findTheMostExpensiveFromShop(@Param("name") String name);

    @Query("select p from Product p join fetch p.shop s join fetch p.category where p.id = :aLong")
    Optional<Product> findFetchById(@Param("aLong") Long aLong);
}
