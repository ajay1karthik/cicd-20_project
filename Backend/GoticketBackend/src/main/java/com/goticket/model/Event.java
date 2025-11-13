package com.goticket.model;

// Make sure these imports are at the top
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore; // <-- 1. ADD THIS IMPORT

@Entity
@Table(name = "events")
public class Event {

    // ... (All your other fields like id, title, description, etc. stay the same)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String venue;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    private Double price;
    private Integer totalSeats;
    
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore // This is already here for seats, which is good.
    private List<Seat> seats;

    // --- THIS IS THE FIX ---
    // This is the list that is causing the infinite loop.
    @OneToMany(mappedBy = "event") // You might need to add this field if it's missing
    @JsonIgnore // <-- 2. ADD THIS ANNOTATION HERE
    private List<Booking> bookings;

    // ... (All your getters and setters stay exactly the same)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
    public List<Booking> getBookings() { return bookings; } // Add getter/setter if missing
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}