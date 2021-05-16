package ru.toroschin.spring.market.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.toroschin.spring.market.utils.OrderStatus;
import ru.toroschin.spring.market.utils.PaymentStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "order_status")
    private int orderStatus;

    @Column(name = "payment_status")
    private int paymentStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order(Long userId, BigDecimal sum, OrderStatus orderStatus, PaymentStatus paymentStatus) {
        this.user_id = userId;
        this.sum = sum;
        this.orderStatus = orderStatus.getValue();
        this.paymentStatus = paymentStatus.getValue();
    }
}
