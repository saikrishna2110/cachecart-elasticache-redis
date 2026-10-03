package com.klu.cachecart.controller;

import com.klu.cachecart.Entity.User;
import com.klu.cachecart.Entity.Wishlist;
import com.klu.cachecart.service.UserService;
import com.klu.cachecart.service.WishlistService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:5173")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(
            WishlistService wishlistService,
            UserService userService) {

        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Wishlist>> getWishlist(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                wishlistService.getWishlist(user.getId())
        );
    }

    @PostMapping
    public ResponseEntity<Wishlist> addToWishlist(
            @RequestParam String productId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Wishlist wishlist =
                wishlistService.addToWishlist(
                        user.getId(),
                        productId
                );

        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping
    public ResponseEntity<?> removeFromWishlist(
            @RequestParam String productId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        boolean removed =
                wishlistService.removeFromWishlist(
                        user.getId(),
                        productId
                );

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Product removed from wishlist"
        );
    }
}