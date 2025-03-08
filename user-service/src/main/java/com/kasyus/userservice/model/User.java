package com.kasyus.userservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    private String id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<WishlistItem> wishlistItems = new HashSet<>();


    protected User() {}

    public User(String id) {
        this.id = id;
    }

    public User(String id, UserProfile profile, Set<Address> addresses, Set<PaymentMethod> paymentMethods,
                Set<WishlistItem> wishlistItems) {
        this.id = id;
        this.profile = profile;
        this.addresses = addresses != null ? addresses : new HashSet<>();
        this.paymentMethods = paymentMethods != null ? paymentMethods : new HashSet<>();
        this.wishlistItems = wishlistItems != null ? wishlistItems : new HashSet<>();
    }

    // Getters
    public String getId() { return id; }
    public UserProfile getProfile() { return profile; }
    public Set<Address> getAddresses() { return addresses; }
    public Set<PaymentMethod> getPaymentMethods() { return paymentMethods; }
    public Set<WishlistItem> getWishlistItems() { return wishlistItems; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setProfile(UserProfile profile) { this.profile = profile; }
    public void setAddresses(Set<Address> addresses) { this.addresses = addresses; }
    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) { this.paymentMethods = paymentMethods; }
    public void setWishlistItems(Set<WishlistItem> wishlistItems) { this.wishlistItems = wishlistItems; }

}