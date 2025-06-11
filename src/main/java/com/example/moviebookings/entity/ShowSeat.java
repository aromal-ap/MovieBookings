package com.example.moviebookings.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "show_seat")
public class ShowSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String seatNumber;  // a1,a2,a3....
	
	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;
	
	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	private SeatStatus seatStatus;
	
	public enum SeatStatus{
		AVAILABLE,
		LOCKED,
		BOOKED
	}

	public ShowSeat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShowSeat(Long id, String seatNumber, Show show, BigDecimal price, SeatStatus seatStatus) {
		super();
		this.id = id;
		this.seatNumber = seatNumber;
		this.show = show;
		this.price = price;
		this.seatStatus = seatStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	
}
