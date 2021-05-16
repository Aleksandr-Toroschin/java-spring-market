package ru.toroschin.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.spring.market.utils.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<OrderItemDto> items;
    private BigDecimal sum;

    public CartDto(Cart cart) {
        sum = cart.getSum();
        items = cart.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
