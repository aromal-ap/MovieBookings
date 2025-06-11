package com.example.moviebookings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.moviebookings.entity.ShowSeat;
import com.example.moviebookings.repository.ShowSeatRepository;
import com.example.moviebookings.service.ShowSeatService;

@RestController
@RequestMapping("/api/showseats")
public class ShowSeatController {

	@Autowired
	private ShowSeatService showSeatService;
	
	//get all seats for a particular show
	@GetMapping("/show/{showId}")
	public ResponseEntity<List<ShowSeat>> getAllSeatsForShow(@PathVariable Long showId){
		List<ShowSeat> seats=showSeatService.getAllSeatsForShow(showId);
		return ResponseEntity.ok(seats);
	}
	
	//get available seats for a show
	@GetMapping("/show/{showId}/available")
	public ResponseEntity<List<ShowSeat>> getAvailableSeatsForShow(@PathVariable Long showId){
		List<ShowSeat> availableSeats=showSeatService.getAvailableSeatsForShow(showId);
		return ResponseEntity.ok(availableSeats);
	}
	
	/*//save a single seat
	@PostMapping("/show/{showId}")
	public ResponseEntity<ShowSeat> saveSeat(@PathVariable Long showId,@RequestBody ShowSeat seat){
		ShowSeat savedSeat=showSeatService.createSeatForShow(showId,seat);
		return ResponseEntity.ok(savedSeat);
	}*/
		
	/*//save multiple seats
	@PostMapping("/show/{showId}/bulk")
	public ResponseEntity<List<ShowSeat>> saveMultipleSeats(@PathVariable Long showId,@RequestBody List<ShowSeat> seats){
		List<ShowSeat> savedSeats=showSeatService.createSeatsForShow(showId,seats);
		return ResponseEntity.ok(savedSeats);
	}*/
	
	//update seat
	@PutMapping("/{seatId}")
	public ResponseEntity<ShowSeat> updateSeat(@PathVariable Long seatId,@RequestBody ShowSeat seat){
		ShowSeat updatedSeat=showSeatService.updateSeat(seatId,seat);
		return ResponseEntity.ok(updatedSeat);
	}
	
	//update multiple seats
	@PutMapping("/bulk")
	public ResponseEntity<List<ShowSeat>> updateMultipleSeats(@RequestBody List<ShowSeat> seatsToUpdate) {
	    List<ShowSeat> updatedSeats = showSeatService.updateMultipleSeats(seatsToUpdate);
	    return ResponseEntity.ok(updatedSeats);
	}

	
	// delete Seat
	@DeleteMapping("/{seatId}")
	public ResponseEntity<Void> deleteSeat(@PathVariable Long seatId){
		showSeatService.deleteSeat(seatId);
		return ResponseEntity.noContent().build();
	}
	
	//delete multiple seats
	// ShowSeatController.java

	@DeleteMapping("/bulk")
	public ResponseEntity<String> deleteMultipleSeats(@RequestBody List<Long> seatIds) {
	    showSeatService.deleteMultipleSeats(seatIds);
	    return ResponseEntity.ok("Seats deleted successfully");
	}

}
