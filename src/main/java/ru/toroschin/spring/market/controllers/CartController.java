package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.dtos.CartDto;
import ru.toroschin.spring.market.services.CartService;

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
    public CartDto getCart() {
        return cartService.getCart();
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Long id) {
        cartService.deleteProduct(id);
        log.info("Удален продукт c id: " + id);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
        log.info("Очищена корзина");
    }

}
