package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import ru.toroschin.spring.market.models.Category;
import ru.toroschin.spring.market.repositories.CategoryRepository;

import java.io.Serializable;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findCategoryByTitle(title);
    }

    public Optional<Category> findOneById(Long id) {
        return categoryRepository.findById(id);
    }

}
