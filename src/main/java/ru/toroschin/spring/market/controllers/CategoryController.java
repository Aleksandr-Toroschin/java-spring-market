package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.toroschin.spring.market.dtos.CategoryDto;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.models.Category;
import ru.toroschin.spring.market.services.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDto findOneById(@PathVariable Long id) {
        Category category = categoryService.findOneById(id).orElseThrow(() -> new ResourceNotFoundException("Категория с id="+id+" не найдена"));
        return new CategoryDto(category);
    }

}
