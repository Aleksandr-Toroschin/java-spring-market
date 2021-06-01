package ru.toroschin.spring.market.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.soap.entities.CategoryEntity;
import ru.toroschin.spring.market.soap.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllBy(Pageable pageable);

    @Query("select p from ProductEntity p")
    List<ProductEntity> findAllEntities();

    @Query("select p from ProductEntity p where p.id=?1")
    Optional<ProductEntity> findEntityById(Long id);
}
