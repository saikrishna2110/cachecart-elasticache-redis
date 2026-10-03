package com.klu.cachecart.repository;

import com.klu.cachecart.Entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository
        extends JpaRepository<Wishlist, String> {

    List<Wishlist> findByUserId(String userId);

    Optional<Wishlist> findByUserIdAndProductId(
            String userId,
            String productId
    );

    void deleteByUserIdAndProductId(
            String userId,
            String productId
    );
}