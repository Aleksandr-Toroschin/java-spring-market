package ru.toroschin.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.toroschin.spring.market.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
