package com.klu.cachecart.service;

import com.klu.cachecart.Entity.Wishlist;
import com.klu.cachecart.repository.WishlistRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {

        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getWishlist(String userId) {

        return wishlistRepository.findByUserId(userId);
    }

    public Wishlist addToWishlist(
            String userId,
            String productId) {

        Wishlist existing =
                wishlistRepository
                        .findByUserIdAndProductId(
                                userId,
                                productId
                        )
                        .orElse(null);

        if (existing != null) {

            return existing;
        }

        Wishlist wishlist = new Wishlist(
                "wishlist-" + UUID.randomUUID(),
                userId,
                productId
        );

        return wishlistRepository.save(wishlist);
    }

    @Transactional
    public boolean removeFromWishlist(
            String userId,
            String productId) {

        Wishlist existing =
                wishlistRepository
                        .findByUserIdAndProductId(
                                userId,
                                productId
                        )
                        .orElse(null);

        if (existing == null) {

            return false;
        }

        wishlistRepository.deleteByUserIdAndProductId(
                userId,
                productId
        );

        return true;
    }
}