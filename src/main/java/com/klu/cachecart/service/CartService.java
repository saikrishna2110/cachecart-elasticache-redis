package com.klu.cachecart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.klu.cachecart.Entity.Cart;
import com.klu.cachecart.repository.CartRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addToCart(String userId, String productId, int quantity) {

        Cart cart = cartRepository
                .findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + quantity);
        } else {
            cart = new Cart(
                    "cart-" + UUID.randomUUID(),
                    userId,
                    productId,
                    quantity
            );
        }

        return cartRepository.save(cart);
    }

    public Cart updateQuantity(
            String userId,
            String productId,
            int quantity) {

        Cart cart = cartRepository
                .findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (cart == null) {
            return null;
        }

        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    @Transactional
    public boolean removeFromCart(
            String userId,
            String productId) {

        Cart cart = cartRepository
                .findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (cart == null) {
            return false;
        }

        cartRepository.deleteByUserIdAndProductId(
                userId,
                productId
        );

        return true;
    }

    public void clearCart(String userId) {

        List<Cart> cartItems =
                cartRepository.findByUserId(userId);

        cartRepository.deleteAll(cartItems);
    }
}