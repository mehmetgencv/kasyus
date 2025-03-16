package com.kasyus.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "wishlist_items")
public class WishlistItem extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_name")
    private String productName;

    // Default constructor for JPA
    public WishlistItem() {}

    // Constructor for API
    public WishlistItem(User user, String productId) {
        this.user = user;
        this.productId = productId;
    }

    // Full constructor
    public WishlistItem(UUID id, User user, String productId, String productName) {
        this.id = id;
        this.user = user;
        this.productId = productId;
        this.productName = productName;
    }

    // Getters
    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }

    // Setters
    public void setId(UUID id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setProductId(String productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
}