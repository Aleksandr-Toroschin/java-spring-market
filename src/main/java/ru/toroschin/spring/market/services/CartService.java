package ru.toroschin.spring.market.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.dtos.OrderItemDto;
import ru.toroschin.spring.market.dtos.StringDto;
import ru.toroschin.spring.market.error_handling.ResourceNotFoundException;
import ru.toroschin.spring.market.utils.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class CartService {
    private final ProductService productService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String PREF_CART_ID = "market_cart_";

    public void addProduct(Long productId, String cartId) {
        Cart cart = getCurrentCart(cartId);
        if (cart.addProduct(productId)) {
            save(cartId, cart);
            return;
        }
        cart.addProduct(productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Продукт с id=" + productId + "не найден")));
        save(cartId, cart);
    }

    public Cart getCurrentCart(String cartId) {
        if (!redisTemplate.hasKey(PREF_CART_ID + cartId)) {
            redisTemplate.opsForValue().set(PREF_CART_ID + cartId, new Cart());
        }
        return (Cart)redisTemplate.opsForValue().get(PREF_CART_ID + cartId);
    }

    public void save(String cartId, Cart cart) {
        redisTemplate.opsForValue().set(PREF_CART_ID + cartId, cart);
    }

    public void deleteCartFromRedis(String cartId) {
        redisTemplate.delete(PREF_CART_ID + cartId);
    }

    public void clearCart(String cartId) {
        Cart cart = getCurrentCart(cartId);
        cart.clearCart();
        save(cartId, cart);
    }

    public void deleteProduct(Long productId, String cartId) {
        Cart cart = getCurrentCart(cartId);
        cart.deleteProduct(productId);
        save(cartId, cart);
    }

    public BigDecimal getSum(String cartId) {
        Cart cart = getCurrentCart(cartId);
        return cart.getSum();
    }

    public List<OrderItemDto> getItems(String cartId) {
        Cart cart = getCurrentCart(cartId);
        return cart.getItems();
    }

    public StringDto generateCartName() {
        String uuid = null;
        do {
            uuid = UUID.randomUUID().toString();
        } while (isCartExists(uuid));
        return new StringDto(uuid);
    }

    public boolean isCartExists(String cartId) {
        return redisTemplate.hasKey(PREF_CART_ID + cartId);
    }

    public StringDto merge(String userCartId, String guestCartId) {
        Cart userCart = getCurrentCart(userCartId);
        Cart guestCart = getCurrentCart(guestCartId);
        userCart.merge(guestCart);
        save(userCartId, userCart);
//        save(guestCartId, guestCart);
        deleteCartFromRedis(guestCartId);
        return new StringDto(userCartId);
    }
}
