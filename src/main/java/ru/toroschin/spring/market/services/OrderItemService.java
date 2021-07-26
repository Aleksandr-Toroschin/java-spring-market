package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.dtos.OrderItemDto;
import ru.toroschin.spring.market.models.Order;
import ru.toroschin.spring.market.models.OrderItem;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.repositories.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public void saveAll(List<OrderItemDto> orderItemsDto, Order order) {
        List<OrderItem> orderItems = orderItemsDto.stream().map(oiDto -> new OrderItem(productService.findById(oiDto.getProductDto().getId()).get())).collect(Collectors.toList());
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        orderItemRepository.saveAll(orderItems);
    }
}
