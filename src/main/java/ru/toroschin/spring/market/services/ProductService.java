package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import ru.toroschin.spring.market.dtos.ProductDto;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.models.Category;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private CategoryService categoryService;

    public Page<Product> findPage(int page, int pageSize) {
        return productRepository.findAllBy(PageRequest.of(page, pageSize));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public ProductDto saveDto(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        Optional<Category> category = categoryService.findByTitle(productDto.getCategoryTitle());
        product.setCategory(category.orElseThrow(() -> new ResourceNotFoundException("Такой категории не найдено " + productDto.getCategoryTitle())));
        productRepository.save(product);
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto update(ProductDto productDto) {
        Product product = findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Продукт с id=" + productDto.getId() + "не найден"));
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        Optional<Category> category = categoryService.findByTitle(productDto.getCategoryTitle());
        product.setCategory(category.orElseThrow(() -> new ResourceNotFoundException("Такой категории не найдено " + productDto.getCategoryTitle())));
        return new ProductDto(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
