package ru.toroschin.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.spring.market.models.OrderItem;
import ru.toroschin.spring.market.models.Product;

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

    public OrderItemDto(Product product) {
        this.quantity = 1;
        this.pricePerProduct = product.getCost();
        this.price = product.getCost();
        this.productDto = new ProductDto(product);
    }

    public void incrementQuantity() {
        quantity ++;
        price = pricePerProduct.multiply(new BigDecimal(quantity));
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }
}
