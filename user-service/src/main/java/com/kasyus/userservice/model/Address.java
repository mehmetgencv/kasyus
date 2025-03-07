package com.kasyus.userservice.model;

import com.kasyus.userservice.model.enums.AddressType;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType type;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "phone")
    private String phone;


    // Default constructor for JPA
    public Address() {}

    // Constructor for API
    public Address(User user, AddressType type, String streetAddress, String city, String state,
                   String postalCode, String country, String phone) {
        this.user = user;
        this.type = type;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;

    }

    // Full constructor
    public Address(UUID id, User user, AddressType type, boolean isDefault, String streetAddress,
                   String city, String state, String postalCode, String country, String phone) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.isDefault = isDefault;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }

    // Getters
    public UUID getId() { return id; }
    public User getUser() { return user; }
    public AddressType getType() { return type; }
    public boolean isDefault() { return isDefault; }
    public String getStreetAddress() { return streetAddress; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public String getPhone() { return phone; }

    // Setters
    public void setId(UUID id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setType(AddressType type) { this.type = type; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setCountry(String country) { this.country = country; }
    public void setPhone(String phone) { this.phone = phone; }

}