package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.services.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id) {
        cartService.addProduct(id);
        log.info("Добавлен продукт с id: " + id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return cartService.getAllProducts();
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Long id) {
        cartService.deleteProduct(id);
        log.info("Удален продукт c id: " + id);
    }

    @DeleteMapping("/del")
    public void deleteProduct() {
        cartService.deleteAllProducts();
        log.info("Очищена корзина");
    }

}
