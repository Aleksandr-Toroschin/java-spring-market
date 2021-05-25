package ru.toroschin.spring.market.dtos;

import lombok.Data;
import ru.toroschin.spring.market.models.Order;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    private Long id;
    private BigDecimal sum;
    private Long userId;
    private String address;
    private String phone;
    private String email;
    private List<OrderItemDto> orderItems;
    private int orderStatus;
    private int paymentStatus;
    private String createdAt;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.sum = order.getSum();
        this.userId = order.getUserId();
        this.address = order.getAddress();
        this.phone = order.getPhone();
        this.email = order.getEmail();
        this.orderStatus = order.getOrderStatus();
        this.paymentStatus = order.getPaymentStatus();
        this.orderItems = order.getOrderItems().stream().map(oi -> new OrderItemDto(oi)).collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        this.createdAt = order.getCreatedAt().format(formatter);
    }
}
