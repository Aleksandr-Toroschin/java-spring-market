package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.toroschin.spring.market.dtos.CartDto;
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
    private Cart cart;

    public void addProduct(Long id, Cart cart) {
        for (OrderItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(id)) {
                item.incrementQuantity();
                cart.recalculate();
                return;
            }
        }

        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id=" + id + "не найден"));
        cart.addProduct(product);
    }

}
