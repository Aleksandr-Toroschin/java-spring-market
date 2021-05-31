package ru.toroschin.spring.market.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.models.OrderItem;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.utils.Cart;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class CartService {
    private final ProductService productService;

    public void addProduct(Long id, Cart cart) {
        for (OrderItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(id)) {
                item.incrementQuantity();
                cart.recalculate();
                return;
            }
        }
        cart.addProduct(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id=" + id + "не найден")));
    }

}
