package ru.toroschin.spring.market.soap.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.soap.soap.products.*;
import ru.toroschin.spring.market.repositories.ProductRepository;
import ru.toroschin.spring.market.soap.entities.ProductEntity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSoapService {
    private final ProductRepository productRepository;

    public static final Function<ProductEntity, Product> functionEntityToSoap = pe -> {
        Product product = new Product();
        product.setId(pe.getId());
        product.setTitle(pe.getTitle());
        product.setCost(pe.getCost());
        product.setCategoryTitle(pe.getCategory().getTitle());
        return product;
    };

    public List<Product> getAllProducts() {
        return productRepository.findAllEntities().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public Product getById(Long id) {
        return productRepository.findEntityById(id).map(functionEntityToSoap).get();
    }
}
