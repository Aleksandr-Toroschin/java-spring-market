package ru.toroschin.spring.market.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.dtos.OrderDto;
import ru.toroschin.spring.market.dtos.OrderParamsDto;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.models.Order;
import ru.toroschin.spring.market.models.Product;
import ru.toroschin.spring.market.models.User;
import ru.toroschin.spring.market.repositories.OrderRepository;
import ru.toroschin.spring.market.utils.Cart;
import ru.toroschin.spring.market.utils.OrderStatus;
import ru.toroschin.spring.market.utils.PaymentStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final Cart cart;

    @Transactional
    public Order createNewOrder(String userName, OrderParamsDto orderParamsDto) {
        Long userId = userService.findUserByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("User not found by login " + userName)).getId();
        Order order = new Order(userId, cart.getSum(), OrderStatus.PLACED, PaymentStatus.WAIT_PAY);
        order.setAddress(orderParamsDto.getAddress());
        order.setPhone(orderParamsDto.getPhone());
        order.setEmail(orderParamsDto.getEmail());
        Order orderSaved = orderRepository.saveAndFlush(order);
        // TODO сделать привязку к OrderItems и сохранять только заказ, каскадно сохранятся OrderItems
        orderItemService.saveAll(cart.getOrderItems(), orderSaved);
        return orderSaved;
    }

    public OrderDto findOrderById(Long id) {
        return new OrderDto(orderRepository.getOne(id));
    }

    public List<OrderDto> findAllOrders() {
        return orderRepository.findAll().stream().map(o->new OrderDto(o)).collect(Collectors.toList());
    }

    public Page<Order> findPage(int page, int pageSize, String userName) {
        User user = userService.findUserByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("Пользователь " + userName + " не найден"));
        return orderRepository.findAllByUserId(PageRequest.of(page, pageSize), user.getId());
    }
}
