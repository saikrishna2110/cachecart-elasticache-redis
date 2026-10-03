package com.klu.cachecart.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    private String id;

    private String userId;
    private String productId;

    public Wishlist() {
    }

    public Wishlist(String id, String userId, String productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}