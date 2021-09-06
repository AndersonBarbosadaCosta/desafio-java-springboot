package br.com.anderson.costa.desafio.repository;

import br.com.anderson.costa.desafio.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
