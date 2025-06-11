package com.example.moviebookings.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Booking done by user
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	//booking for a specific show
	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;
			
	//Booked seats(list of seats)
	@ManyToMany
	@JoinTable(
			name = "booking_show_seats",
			joinColumns = @JoinColumn(name = "booking_id"),
			inverseJoinColumns = @JoinColumn(name = "show_seat_id"))
	private List<ShowSeat> bookedSeats; /// A1,A2,A3
	private LocalDateTime bookingTime;
	private BigDecimal totalAmount;	
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    public enum PaymentStatus{
    	PENDING,
    	PAID,
    	FAILED
    }

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking(Long id, User user, Show show, List<ShowSeat> bookedSeats, LocalDateTime bookingTime,
			BigDecimal totalAmount, PaymentStatus paymentStatus) {
		super();
		this.id = id;
		this.user = user;
		this.show = show;
		this.bookedSeats = bookedSeats;
		this.bookingTime = bookingTime;
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public List<ShowSeat> getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(List<ShowSeat> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
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
