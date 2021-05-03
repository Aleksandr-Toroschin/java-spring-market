package ru.toroschin.spring.market.models;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
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
        return items;
    }

    public void deleteProduct(Long id) {
        for (Product item : items) {
            if (item.getId() == id) {
                items.remove(item);
                return;
            }
        }
    }

    public void deleteAllProducts() {
        items.clear();
    }

}
