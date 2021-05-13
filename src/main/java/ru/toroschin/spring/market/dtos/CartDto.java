package ru.toroschin.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.spring.market.utils.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<ProductDto> products;
    private int sum;

    public CartDto(Cart cart) {
        sum = cart.getSum();
        products = cart.getAllProducts().stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
