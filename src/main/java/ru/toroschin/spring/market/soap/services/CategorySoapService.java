package ru.toroschin.spring.market.soap.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.repositories.CategoryRepository;
import ru.toroschin.spring.market.soap.entities.CategoryEntity;
import ru.toroschin.spring.market.soap.soap.categories.Category;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategorySoapService {
    private final CategoryRepository categoryRepository;

    public static final Function<CategoryEntity, Category> functionEntityToSoap = categoryEntity -> {
        Category category = new Category();
        category.setTitle(categoryEntity.getTitle());
//        categoryEntity.getProducts().stream().map(ProductSoapService.functionEntityToSoap).forEach(p -> category.getProducts().add(p));
        return category;
    };

    public Category getByTitle(String title) {
        return categoryRepository.findByTitle(title).map(functionEntityToSoap).get();
    }
}
