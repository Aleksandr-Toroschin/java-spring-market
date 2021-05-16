package ru.toroschin.spring.market.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.toroschin.spring.market.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllBy(Pageable pageable);
}
