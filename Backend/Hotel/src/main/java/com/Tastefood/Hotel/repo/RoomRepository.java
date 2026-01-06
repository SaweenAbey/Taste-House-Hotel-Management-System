package com.Tastefood.Hotel.repo;

import com.Tastefood.Hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // Get unique room types
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomType();

    // Find available rooms by date range + room type
    @Query("""
        SELECT r FROM Room r
        WHERE r.roomType LIKE CONCAT('%', :roomType, '%')
        AND r.id NOT IN (
            SELECT bk.room.id FROM Booking bk
            WHERE bk.checkInDate <= :checkOutDate
            AND bk.checkOutDate >= :checkInDate
        )
    """)
    List<Room> findAvailableRoomByDatesandType(
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomType") String roomType
    );

    // Rooms that have never been booked
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
    List<Room> getAllAvailableRooms();
}
