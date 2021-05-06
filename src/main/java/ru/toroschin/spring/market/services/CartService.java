package ru.toroschin.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.dtos.CartDto;
import ru.toroschin.spring.market.models.Cart;
import ru.toroschin.spring.market.models.Product;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final Cart cart;

    public void addProduct(Long id) {
        Optional<Product> product = productService.findById(id);
        product.ifPresent(cart::addProduct);
    }

    public CartDto getCart() {
//        List<Product> products = cart.getAllProducts();
//        return products.stream().map(p -> new ProductDto(p)).collect(Collectors.toList());
        return new CartDto(cart);
    }

    public void deleteProduct(Long id) {
        cart.deleteProduct(id);
    }

    public void clearCart() {
        cart.deleteAllProducts();
    }
}
