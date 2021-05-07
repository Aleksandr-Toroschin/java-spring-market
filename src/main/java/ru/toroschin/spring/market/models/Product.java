package ru.toroschin.spring.market.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.spring.market.dtos.ProductDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private int cost;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
