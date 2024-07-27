package com.joaovictor.PhegonHotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of adults must be at least 1")
    private Integer numOfAdults;

    @Min(value = 0, message = "Number of children must be at least 0")
    private Integer numOfChildren;

    private Integer numOfGuests;
    private String bookingConfigurationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void calculateNumOfGuests() {
        this.numOfGuests = this.numOfAdults + this.numOfChildren;
    }

    public void setNumOfAdults(@Min(value = 1, message = "Number of adults must be at least 1") Integer numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateNumOfGuests();
    }

    public void setNumOfChildren(@Min(value = 0, message = "Number of children must be at least 0") Integer numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateNumOfGuests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", numOfGuests=" + numOfGuests +
                ", bookingConfigurationCode='" + bookingConfigurationCode + '\'' +
                '}';
    }
}
