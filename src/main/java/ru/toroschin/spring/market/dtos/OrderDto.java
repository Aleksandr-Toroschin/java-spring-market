package ru.toroschin.spring.market.dtos;

import lombok.Data;
import ru.toroschin.spring.market.models.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long id;
    private BigDecimal sum;
    private Long userId;
    private List<OrderItemDto> orderItems;
    private int orderStatus;
    private int paymentStatus;
    private LocalDateTime createdAt;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.sum = order.getSum();
        this.userId = order.getUser_id();
        this.orderStatus = order.getOrderStatus();
        this.paymentStatus = order.getPaymentStatus();
        this.orderItems = order.getOrderItems().stream().map(oi -> new OrderItemDto(oi)).collect(Collectors.toList());
        this.createdAt = order.getCreatedAt();
    }
}
