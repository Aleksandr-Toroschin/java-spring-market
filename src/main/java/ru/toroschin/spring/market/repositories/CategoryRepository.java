package ru.toroschin.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.toroschin.spring.market.models.Category;
import ru.toroschin.spring.market.soap.entities.CategoryEntity;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByTitle(String title);

    @Query("select c from CategoryEntity c where c.title = ?1")
    Optional<CategoryEntity> findByTitle(String title);
}
