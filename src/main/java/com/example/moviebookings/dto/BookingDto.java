package com.example.moviebookings.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.moviebookings.entity.Booking.PaymentStatus;


public class BookingDto {

	private Long bookingId;
	private String movieTitle;
	private String theatreName;
	private LocalDateTime showtime;
	private List<String> seatNumber;
	private BigDecimal totalAmount;
	private PaymentStatus paymentStatus;
	public BookingDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookingDto(Long bookingId, String movieTitle, String theatreName, LocalDateTime showtime,
			List<String> seatNumber, BigDecimal totalAmount, PaymentStatus paymentStatus) {
		super();
		this.bookingId = bookingId;
		this.movieTitle = movieTitle;
		this.theatreName = theatreName;
		this.showtime = showtime;
		this.seatNumber = seatNumber;
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public String getTheatreName() {
		return theatreName;
	}
	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	public LocalDateTime getShowtime() {
		return showtime;
	}
	public void setShowtime(LocalDateTime showtime) {
		this.showtime = showtime;
	}
	public List<String> getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(List<String> seatNumber) {
		this.seatNumber = seatNumber;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
}
