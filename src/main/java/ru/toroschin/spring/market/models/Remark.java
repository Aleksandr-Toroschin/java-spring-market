package ru.toroschin.spring.market.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "prod_remarks")
@NoArgsConstructor
public class Remark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
