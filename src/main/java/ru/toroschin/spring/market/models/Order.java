package ru.toroschin.spring.market.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.toroschin.spring.market.utils.OrderStatus;
import ru.toroschin.spring.market.utils.PaymentStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order implements Serializable {
//    private static final long serialVersionUID = 8147169171849348115L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

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
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order(Long userId, BigDecimal sum, OrderStatus orderStatus, PaymentStatus paymentStatus) {
        this.userId = userId;
        this.sum = sum;
        this.orderStatus = orderStatus.getValue();
        this.paymentStatus = paymentStatus.getValue();
    }
}
