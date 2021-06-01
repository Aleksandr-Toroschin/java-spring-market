package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.dtos.CartDto;
import ru.toroschin.spring.market.services.CartService;
import ru.toroschin.spring.market.utils.Cart;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {
    private final Cart cart;
    private final CartService cartService;

    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id) {
        cartService.addProduct(id, cart);
    }

    @GetMapping
    public CartDto getCart() {
        return new CartDto(cart);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Long id) {
//        cartService.deleteProduct(id, cart);
        cart.deleteProduct(id);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
//        cartService.clearCart(cart);
        cart.clearCart();
    }

    @GetMapping("/get")
    public BigDecimal getSum() {
        return cart.getSum();
        // cartService.getSum(cart);
    }
}
