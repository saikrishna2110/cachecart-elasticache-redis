package com.klu.cachecart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.klu.cachecart.Entity.Cart;
import com.klu.cachecart.Entity.User;
import com.klu.cachecart.service.CartService;
import com.klu.cachecart.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(
            CartService cartService,
            UserService userService) {

        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCart(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                cartService.getCart(user.getId())
        );
    }

    @PostMapping
    public ResponseEntity<Cart> addToCart(
            @RequestParam String productId,
            @RequestParam int quantity,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = cartService.addToCart(
                user.getId(),
                productId,
                quantity
        );

        return ResponseEntity.ok(cart);
    }

    @PutMapping
    public ResponseEntity<?> updateQuantity(
            @RequestParam String productId,
            @RequestParam int quantity,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = cartService.updateQuantity(
                user.getId(),
                productId,
                quantity
        );

        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping
    public ResponseEntity<?> removeFromCart(
            @RequestParam String productId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        boolean removed = cartService.removeFromCart(
                user.getId(),
                productId
        );

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Product removed from cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        cartService.clearCart(user.getId());

        return ResponseEntity.ok("Cart cleared");
    }
}