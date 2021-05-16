package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.dtos.CartDto;
import ru.toroschin.spring.market.utils.Cart;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {
    private final Cart cart;

    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id) {
        cart.addProduct(id);
        log.info("Добавлен продукт с id: " + id);
    }

    @GetMapping
    public CartDto getCart() {
        return new CartDto(cart);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Long id) {
        cart.deleteProduct(id);
        log.info("Удален продукт c id: " + id);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cart.clearCart();
    }

}
