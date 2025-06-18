package com.example.moviebookings.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviebookings.dto.BookingDto;
import com.example.moviebookings.entity.Booking;
import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.UserRepository;
import com.example.moviebookings.service.BookingService;
import com.example.moviebookings.service.EmailService;
import com.example.moviebookings.service.PdfGeneratorService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PdfGeneratorService pdfGeneratorService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/user/{userId}/show/{showId}")
	public ResponseEntity<Booking> createBooking(@PathVariable Long userId,
			                                     @PathVariable Long showId,
			                                     @RequestBody List<Long> seatId){
		
		Booking booking=bookingService.createBooking(userId, showId, seatId);
		
		try {
			ByteArrayInputStream pdf=pdfGeneratorService.generateBookingPdf(booking);
	        emailService.sendBookingConfirmation(booking.getUser().getEmail(),pdf);		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(booking);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<BookingDto>> getBookingsForUser(@PathVariable Long userId){
		
		List<BookingDto> bookingHistory=bookingService.getBookingHistoryForUser(userId);
		return ResponseEntity.ok(bookingHistory);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<Long> getUserIDByEmail(@RequestParam String email){
		User user=bookingService.getUserIdByEmail(email);
		return ResponseEntity.ok(user.getId());
	}
	
	@GetMapping("/{bookingId}/download-pdf")
	public ResponseEntity<byte[]> downloadBookingPdf(@PathVariable Long bookingId){
		Booking booking=bookingService.getBookingById(bookingId);
		ByteArrayInputStream bis=pdfGeneratorService.generateBookingPdf(booking);
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Disposition","inline; filename=booking_" + bookingId + ".pdf");
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(bis.readAllBytes());
	}
}
