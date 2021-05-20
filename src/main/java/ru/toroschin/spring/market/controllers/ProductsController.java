package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.dtos.ProductDto;
import ru.toroschin.spring.market.error_handling.InvalidDataException;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.services.ProductService;
import ru.toroschin.spring.market.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductsController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(defaultValue = "1") int p) {
        Page<Product> productsPage = productService.findPage(p-1, 5);
        return new PageImpl<>(productsPage.getContent().stream().map(ProductDto::new).collect(Collectors.toList()), productsPage.getPageable(), productsPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public ProductDto getOneProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return new ProductDto(product.orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: "+id)));
    }

    @PostMapping("/add")
    public ProductDto createProduct(@RequestBody @Validated ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        } else {
            return productService.saveDto(productDto);
        }
    }

    @PostMapping("/addFromFile")
    public List<ProductDto> createProductsFromFile(@RequestBody @Validated ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
        } else {
            return List.of(productService.saveDto(productDto));
        }
    }

    @DeleteMapping
    public void deleteProduct(@RequestBody Product product) {
        productService.delete(product.getId());
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }
}
