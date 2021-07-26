package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.dtos.StringDto;
import ru.toroschin.spring.market.services.CartService;
import ru.toroschin.spring.market.utils.Cart;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public void addProductInCart(@RequestParam Long prodId, @RequestParam String cartName) {
        cartService.addProduct(prodId, cartName);
    }

    @GetMapping
    public Cart getCart(@RequestParam String cartName) {
        return cartService.getCurrentCart(cartName);
    }

    @GetMapping("/generate")
    public StringDto generateCartName() {
        return cartService.generateCartName();
    }

    @GetMapping("/merge")
    public StringDto mergeCart(@RequestParam String cartName, Principal principal) {
        return cartService.merge(principal.getName(), cartName);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam Long prodId, @RequestParam String cartName) {
        cartService.deleteProduct(prodId, cartName);
    }

    @DeleteMapping("/clear")
    public void clearCart(@RequestParam String cartName) {
        cartService.clearCart(cartName);
    }

    @GetMapping("/get")
    public BigDecimal getSum(@RequestParam String cartName) {
        return cartService.getCurrentCart(cartName).getSum();
    }
}
