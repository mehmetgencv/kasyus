package com.kasyus.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kasyus.userservice.model.enums.PaymentType;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "provider")
    private String provider;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "last_four")
    private String lastFour;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    // Default constructor for JPA
    public PaymentMethod() {}

    // Constructor for API
    public PaymentMethod(User user, PaymentType type, String token) {
        this.user = user;
        this.type = type;
        this.token = token;
    }

    // Full constructor
    public PaymentMethod(UUID id, User user, String name,  PaymentType type, boolean isDefault, String provider,
                         String token, String lastFour, LocalDate expiryDate) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.type = type;
        this.isDefault = isDefault;
        this.provider = provider;
        this.token = token;
        this.lastFour = lastFour;
        this.expiryDate = expiryDate;
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public User getUser() { return user; }
    public PaymentType getType() { return type; }
    public boolean isDefault() { return isDefault; }
    public String getProvider() { return provider; }
    public String getToken() { return token; }
    public String getLastFour() { return lastFour; }
    public LocalDate getExpiryDate() { return expiryDate; }

    // Setters
    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUser(User user) { this.user = user; }
    public void setType(PaymentType type) { this.type = type; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
    public void setProvider(String provider) { this.provider = provider; }
    public void setToken(String token) { this.token = token; }
    public void setLastFour(String lastFour) { this.lastFour = lastFour; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}