package ru.toroschin.spring.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.spring.market.models.Category;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String title;

    public CategoryDto(Category category) {
        id = category.getId();
        title = category.getTitle();
    }
}
