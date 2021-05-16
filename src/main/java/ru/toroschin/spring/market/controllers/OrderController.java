package ru.toroschin.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.spring.market.dtos.OrderDto;
import ru.toroschin.spring.market.models.Order;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.services.OrderService;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Page<OrderDto> showOrders(@RequestParam(defaultValue = "1") int p) {
        Page<Order> ordersPage = orderService.findPage(p-1, 5);
        return new PageImpl<>(ordersPage.getContent().stream().map(OrderDto::new).collect(Collectors.toList()), ordersPage.getPageable(), ordersPage.getTotalElements());
    }

    @PostMapping
    public void placeAnOrder(Principal principal) {
        orderService.placeAnOrder(principal.getName());
    }

    @GetMapping("/{id}")
    public OrderDto showOrder(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }
}
