package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.models.Order;
import ru.toroschin.spring.market.models.OrderItem;
import ru.toroschin.spring.market.repositories.OrderItemRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void saveAll(List<OrderItem> orderItems, Order order) {
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        orderItemRepository.saveAll(orderItems);
    }
}
