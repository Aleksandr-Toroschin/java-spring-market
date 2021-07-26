package ru.toroschin.spring.market.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.toroschin.spring.market.dtos.OrderItemDto;
import ru.toroschin.spring.market.models.OrderItem;
import ru.toroschin.spring.market.models.Product;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Slf4j
public class Cart implements Serializable {
    private List<OrderItemDto> items;
    private BigDecimal sum;

    public Cart() {
        items = new ArrayList<>();
        sum = BigDecimal.ZERO;
    }

    public boolean addProduct(Long productId) {
        for (OrderItemDto item : items) {
            if (item.getProductDto().getId().equals(productId)) {
                item.incrementQuantity();
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product) {
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public void deleteProduct(Long id) {
        for (OrderItemDto item : items) {
            if (item.getProductDto().getId().equals(id)) {
                items.remove(item);
                log.info("Удален продукт c id: " + id);
                recalculate();
                return;
            }
        }
    }

    public void clearCart() {
        items.clear();
        sum = BigDecimal.ZERO;
    }

    public void recalculate() {
        sum = BigDecimal.ZERO;
        for (OrderItemDto item : items) {
            sum = sum.add(item.getPrice());
        }
    }

    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : items) {
                if (myItem.getProductDto().getId().equals(anotherItem.getProductDto().getId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clearCart();
    }
}
