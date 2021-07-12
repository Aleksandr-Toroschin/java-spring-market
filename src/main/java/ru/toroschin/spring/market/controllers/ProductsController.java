package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.dtos.ProductDto;
import ru.toroschin.spring.market.dtos.RemarkDto;
import ru.toroschin.spring.market.dtos.StringDto;
import ru.toroschin.spring.market.error_handling.InvalidDataException;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.mappers.CategoryMapper;
import ru.toroschin.spring.market.mappers.ProductMapper;
import ru.toroschin.spring.market.models.Category;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.models.Remark;
import ru.toroschin.spring.market.services.ProductService;
import ru.toroschin.spring.market.services.RemarkService;
import ru.toroschin.spring.market.services.UserService;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductsController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(defaultValue = "1") int p) {
        Page<Product> productsPage = productService.findPage(p-1, 5);
        return new PageImpl<>(productsPage.getContent().stream().map(ProductDto::new).collect(Collectors.toList()), productsPage.getPageable(), productsPage.getTotalElements());
    }

    @GetMapping("/list")
    public List<ProductDto> getAllProductsList(@RequestParam(defaultValue = "1") int p) {
        Page<Product> productsPage = productService.findPage(p-1, 5);
        return new PageImpl<>(productsPage.getContent().stream().map(ProductDto::new).collect(Collectors.toList()), productsPage.getPageable(), productsPage.getTotalElements()).getContent();
    }

    @GetMapping("/{id}")
    public ProductDto getOneProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return new ProductDto(product.orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: "+id)));
    }

    @GetMapping("/remarks/{id}")
    public List<RemarkDto> getProductRemarks(@PathVariable Long id) {

        return productService.findRemarks(id);
    }

    @PostMapping("/addremark")
    public void addRemark(@RequestParam Long prodId, @RequestParam String content) {
        productService.addRemark(prodId, content);
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

    //  ДЗ 6 по архитектурам и шаблонам проектирования.

    @GetMapping("/findById")
    public ProductDto findById(@RequestParam Long id) throws SQLException {
        return new ProductDto(productMapper.findById(id));
    }

    @GetMapping("/findAll")
    public List<ProductDto> findAll() throws SQLException {
        return productMapper.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public ProductDto saveProduct(@RequestBody ProductDto productDto) {
        try {
            Product product = new Product();
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setCost(productDto.getCost());
            Optional<Category> category = categoryMapper.findByTitle(productDto.getCategoryTitle());
            product.setCategory(category.orElseThrow(() -> new ResourceNotFoundException("Такой категории не найдено " + productDto.getCategoryTitle())));
            productMapper.save(product);
            return new ProductDto(product);
        } catch (SQLException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    @GetMapping("/delete")
    public void delete(@RequestParam Long id) throws SQLException {
        productMapper.delete(id);
    }

    @GetMapping("/test")
    public ProductDto test() throws SQLException {
        return new ProductDto();
    }
}
