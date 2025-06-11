package com.example.moviebookings.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviebookings.dto.BookingDto;
import com.example.moviebookings.entity.Booking;
import com.example.moviebookings.entity.Show;
import com.example.moviebookings.entity.ShowSeat;
import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.BookingRepository;
import com.example.moviebookings.repository.ShowRepository;
import com.example.moviebookings.repository.ShowSeatRepository;
import com.example.moviebookings.repository.UserRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private ShowSeatRepository showSeatRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private ShowRepository showRepository;
	
	public Booking createBooking(Long userId,Long showId,List<Long> seatIds) {
		
		Show show=showRepository.findById(showId).orElseThrow(()->new RuntimeException("Show not found"));
		
		User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not found"));
		
		List<ShowSeat> seatsToBook=showSeatRepository.findAllById(seatIds);
		
		//check seat status
		for(ShowSeat seat:seatsToBook) {
			if(seat.getSeatStatus()!=ShowSeat.SeatStatus.AVAILABLE) {
				throw new RuntimeException("Seat "+ seat.getSeatNumber() + "is not available");
			}
		}
		
		//mark seat as booked
		for(ShowSeat seat:seatsToBook) {
			seat.setSeatStatus(ShowSeat.SeatStatus.BOOKED);
		}
		showSeatRepository.saveAll(seatsToBook);
		
		BigDecimal totalAmount=seatsToBook.stream()
				.map(ShowSeat::getPrice)
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		Booking booking=new Booking();
		booking.setShow(show);
		booking.setUser(user);
		booking.setBookedSeats(seatsToBook);
		booking.setTotalAmount(totalAmount);
		booking.setBookingTime(LocalDateTime.now());
		booking.setPaymentStatus(Booking.PaymentStatus.PAID);
		
		return bookingRepository.save(booking);
	}
	
	public List<BookingDto> getBookingHistoryForUser(Long userId){
		User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
		List<Booking> bookings=bookingRepository.findByUser(user);
		List<BookingDto> bookingDto=new ArrayList<BookingDto>();
		//map booking entity to bookingdto
		for(Booking booking:bookings) {
			String movieTitle=booking.getShow().getMovie().getTitle();
			String theatreName=booking.getShow().getScreen().getScreen();
			LocalDateTime showTime=booking.getShow().getShowTime();
			
			List<String> seatNumber=new ArrayList<String>();
			for(ShowSeat seat:booking.getBookedSeats()) {
				seatNumber.add(seat.getSeatNumber());
			}
			
			BookingDto dto=new BookingDto();
			dto.setBookingId(booking.getId());
			dto.setMovieTitle(movieTitle);
			dto.setTheatreName(theatreName);
			dto.setShowtime(showTime);
			dto.setSeatNumber(seatNumber);
			dto.setTotalAmount(booking.getTotalAmount());
			dto.setPaymentStatus(booking.getPaymentStatus());
			
			bookingDto.add(dto);
		}
		return bookingDto;
	}

	public User getUserIdByEmail(String email) {
		User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
		return user;
	}

	public Booking getBookingById(Long bookingId) {
		return bookingRepository.findById(bookingId)
				.orElseThrow(()->new RuntimeException("Booking is not found with id : "+bookingId));
	}
}
