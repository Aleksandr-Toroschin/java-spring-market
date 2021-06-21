package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.services.CartService;
import ru.toroschin.spring.market.utils.Cart;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id, @RequestParam String cartId) {
        cartService.addProduct(id, cartId);
    }

    @GetMapping
    public Cart getCart(@RequestParam String cartId) {
        return cartService.getCurrentCart(cartId);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Long productId, @RequestParam String cartId) {
        cartService.deleteProduct(productId, cartId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@RequestParam String cartId) {
        cartService.clearCart(cartId);
    }

    @GetMapping("/get")
    public BigDecimal getSum(@RequestParam String cartId) {
        return cartService.getCurrentCart(cartId).getSum();
    }
}
