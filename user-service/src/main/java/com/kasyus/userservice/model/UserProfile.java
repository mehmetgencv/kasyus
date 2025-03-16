package com.kasyus.userservice.model;

import com.kasyus.userservice.model.enums.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "customer_segment")
    private String customerSegment;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    // Default constructor for JPA
    protected UserProfile() {}

    // Constructor for event handling
    public UserProfile(User user, String firstName, String lastName, String email,
                       UserRole role) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;

    }

    // Full constructor
    public UserProfile(UUID id, User user, String firstName, String lastName, String email,
                       String phoneNumber, LocalDate dateOfBirth, String customerSegment,
                       Integer loyaltyPoints) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.customerSegment = customerSegment;
        this.loyaltyPoints = loyaltyPoints;
    }

    public UserProfile(User user, String firstName, String lastName, String email) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters
    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getCustomerSegment() { return customerSegment; }
    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public UserRole getRole() { return role; }

    // Setters
    public void setId(UUID id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setCustomerSegment(String customerSegment) { this.customerSegment = customerSegment; }
    public void setLoyaltyPoints(Integer loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
    public void setRole(UserRole role) { this.role = role; }
}