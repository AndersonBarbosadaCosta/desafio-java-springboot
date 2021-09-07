package br.com.anderson.costa.desafio.repository;

import br.com.anderson.costa.desafio.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE (p.price >= :minPrice AND p.price <= :maxPrice) AND (p.name LIKE %:q% OR p.description LIKE %:q%)")
    List<Product> findPriceBetweenNameEquals(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, @Param("q") String q);
}
