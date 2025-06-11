package com.example.moviebookings.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviebookings.entity.Show;
import com.example.moviebookings.entity.ShowSeat;
import com.example.moviebookings.repository.ShowRepository;
import com.example.moviebookings.repository.ShowSeatRepository;

@Service
public class ShowSeatService {

	@Autowired
	private ShowSeatRepository showSeatRepository;
	
	@Autowired
	private ShowRepository showRepository;
	
	public List<ShowSeat> getAllSeatsForShow(Long showId){
		return showSeatRepository.findByShowId(showId);
	}
	
	public List<ShowSeat> getAvailableSeatsForShow(Long showId){
		return showSeatRepository.findByShowIdAndSeatStatus(showId,ShowSeat.SeatStatus.AVAILABLE);
	}
	public Optional<ShowSeat> getSeatById(Long seatId){
		return showSeatRepository.findById(seatId);
	}
	
	/*// in case of single seat
	public ShowSeat createSeatForShow(Long showId,ShowSeat seat) {
		Show show=showRepository.findById(showId)
				.orElseThrow(()->new RuntimeException("Show not found with ID: "+showId));
		seat.setShow(show);
		seat.setSeatStatus(ShowSeat.SeatStatus.AVAILABLE);
		return showSeatRepository.save(seat);
	}*/
	
	/*//in case of multiple seats
	public List<ShowSeat> createSeatsForShow(Long showId,List<ShowSeat> seats){
		Show show=showRepository.findById(showId)
				.orElseThrow(()->new RuntimeException("Show not Found With ID: "+showId));
		for(ShowSeat seat:seats) {
			seat.setShow(show);
			seat.setSeatStatus(ShowSeat.SeatStatus.AVAILABLE);
		}
		return showSeatRepository.saveAll(seats);
	}*/
	
	//update seat
	public ShowSeat updateSeat(Long seatId,ShowSeat updatedSeatData){
		ShowSeat existingSeat=showSeatRepository.findById(seatId)
				.orElseThrow(()->new RuntimeException("Seat not found with ID: "+seatId ));
		
		existingSeat.setSeatNumber(updatedSeatData.getSeatNumber());
		//existingSeat.setShow(updatedSeatData.getShow());
		existingSeat.setPrice(updatedSeatData.getPrice());
		existingSeat.setSeatStatus(updatedSeatData.getSeatStatus());
		return showSeatRepository.save(existingSeat);
	}
	
	//update Multiple seats
	public List<ShowSeat> updateMultipleSeats(List<ShowSeat> updatedSeats) {
	    List<ShowSeat> updatedSeatList = new ArrayList<>();

	    for (ShowSeat updatedSeatData : updatedSeats) {
	        Long seatId = updatedSeatData.getId();  // Ensure updatedSeatData has seatId

	        ShowSeat existingSeat = showSeatRepository.findById(seatId)
	                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));

	        // Update fields
	        existingSeat.setSeatNumber(updatedSeatData.getSeatNumber());
	        existingSeat.setPrice(updatedSeatData.getPrice());
	        existingSeat.setSeatStatus(updatedSeatData.getSeatStatus());

	        updatedSeatList.add(existingSeat);
	    }

	    return showSeatRepository.saveAll(updatedSeatList);
	}

	
	//delete seat
	public void deleteSeat(Long seatId) {
		if(!showSeatRepository.existsById(seatId)) {
			throw new RuntimeException("Seat not found with ID: "+seatId);
		}
		showSeatRepository.deleteById(seatId);
	}
	
	//delete multiple seats
	public void deleteMultipleSeats(List<Long> seatIds) {
		List<ShowSeat> seats=showSeatRepository.findAllById(seatIds);
		for(ShowSeat seat:seats) {
			if(seat.getSeatStatus() == ShowSeat.SeatStatus.BOOKED) {
				throw new RuntimeException("Cannot delete seat with ID: "+seat.getId()+" as it is already Booked");
			}
		}
	    showSeatRepository.deleteAll(seats);
	}
}
