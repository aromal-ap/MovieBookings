package com.example.moviebookings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moviebookings.entity.Booking;
import com.example.moviebookings.entity.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>{

	//Find all bookings of a user (for "My Bookings" feature)
    List<Booking> findByUser(User user);
}
