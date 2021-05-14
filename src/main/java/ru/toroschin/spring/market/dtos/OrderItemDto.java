package ru.toroschin.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.spring.market.models.OrderItem;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
    private ProductDto productDto;

    public OrderItemDto(OrderItem orderItem) {
        this.quantity = orderItem.getQuantity();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.price = orderItem.getPrice();
        this.productDto = new ProductDto(orderItem.getProduct());
    }
}
