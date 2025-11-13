package com.goticket.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore; // <-- 1. ADD THIS IMPORT

@Entity
@Table(name = "users")
public class User {

    // ... (id, name, email, password, role fields are the same)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "USER", "ADMIN", "MANAGER"

    @Column(nullable = false)
    private boolean approved = false;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore // <-- 2. ADD THIS ANNOTATION HERE
    private List<Booking> bookings;

    // ... (all getters and setters are the same)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}