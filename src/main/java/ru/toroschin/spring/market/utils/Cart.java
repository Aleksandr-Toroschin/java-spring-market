package ru.toroschin.spring.market.utils;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.toroschin.spring.market.models.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
public class Cart {
    private List<Product> items;
    private int sum;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        items.add(product);
        sum += product.getCost();
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(items);
    }

    public void deleteProduct(Long id) {
        for (Product item : items) {
            if (item.getId() == id) {
                sum -= item.getCost();
                items.remove(item);
                return;
            }
        }
    }

    public void deleteAllProducts() {
        items.clear();
        sum = 0;
    }

}
