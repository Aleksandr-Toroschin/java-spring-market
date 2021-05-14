package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.models.Order;
import ru.toroschin.spring.market.repositories.OrderRepository;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {

    }
}
