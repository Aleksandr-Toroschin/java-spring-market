package ru.toroschin.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.toroschin.spring.market.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
