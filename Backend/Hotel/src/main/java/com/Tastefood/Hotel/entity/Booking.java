package com.Tastefood.Hotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import org.springframework.security.core.GrantedAuthority;
import lombok.Data;
import software.amazon.awssdk.annotations.NotNull;

import java.time.LocalDate;

@Data
@Entity
@Table(name="Bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;
    @Future(message="Check out date must be in future ")
    private LocalDate checkOutDate;

    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuests;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


}
