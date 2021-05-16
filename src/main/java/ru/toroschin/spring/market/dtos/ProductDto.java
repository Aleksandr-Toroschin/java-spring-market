package ru.toroschin.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.spring.market.models.Product;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductDto {
    private Long id;

    @Size(min = 3, message = "Название не должно быть меньше 3 символов")
    private String title;
    private BigDecimal cost;
    private String categoryTitle;

    public ProductDto(Product product) {
        id = product.getId();
        title = product.getTitle();
        cost = product.getCost();
        categoryTitle = product.getCategory().getTitle();
    }
}
