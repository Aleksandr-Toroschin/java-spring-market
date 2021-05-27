package ru.toroschin.spring.market.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.toroschin.spring.market.dtos.CartDto;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.models.OrderItem;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.repositories.OrderItemRepository;
import ru.toroschin.spring.market.repositories.OrderRepository;
import ru.toroschin.spring.market.services.ProductService;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
@Slf4j
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {
    private static final long serialVersionUID = 8147169171849348111L;

    private List<OrderItem> items;
    private BigDecimal sum;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }

//    public void addProduct(Long id) {
//        for (OrderItem item : items) {
//            if (item.getProduct().getId().equals(id)) {
//                item.incrementQuantity();
//                recalculate();
//                return;
//            }
//        }
//
//        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id=" + id + "не найден"));
//        items.add(new OrderItem(product));
//        recalculate();
//    }

    public void addProduct(Product product) {
        items.add(new OrderItem(product));
        recalculate();
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(items);
    }

    public void deleteProduct(Long id) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(id)) {
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
        for (OrderItem item : items) {
            sum = sum.add(item.getPrice());
        }
    }
}
