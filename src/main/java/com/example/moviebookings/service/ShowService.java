package com.example.moviebookings.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviebookings.entity.Movie;
import com.example.moviebookings.entity.Screen;
import com.example.moviebookings.entity.Show;
import com.example.moviebookings.entity.ShowSeat;
import com.example.moviebookings.entity.ShowSeat.SeatStatus;
import com.example.moviebookings.repository.MovieRepository;
import com.example.moviebookings.repository.ScreenRepository;
import com.example.moviebookings.repository.ShowRepository;
import com.example.moviebookings.repository.ShowSeatRepository;

import jakarta.transaction.Transactional;

@Service
public class ShowService {

	@Autowired
	private ShowRepository showRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ScreenRepository screenRepository;
	
	@Autowired
	private ShowSeatRepository showSeatRepository;
	
	public List<Show> getAllShows(){
		return showRepository.findAll();
	}
	
	public Optional<Show> getShowByID(Long id){
		return showRepository.findById(id);
	}
	

	public List<Show> getShowByMovieId(Long movieId) {
		return showRepository.findByMovieId(movieId);
	}
	
	@Transactional
	public Show createShow(Show show) {
		// Validate movie
        Optional<Movie> movieOpt = movieRepository.findById(show.getMovie().getId());
        if (!movieOpt.isPresent()) {
            throw new RuntimeException("Movie not found with ID: " + show.getMovie().getId());
        }

        // Validate screen
        Optional<Screen> screenOpt = screenRepository.findById(show.getScreen().getId());
        if (!screenOpt.isPresent()) {
            throw new RuntimeException("Screen not found with ID: " + show.getScreen().getId());
        }

        show.setMovie(movieOpt.get());
        show.setScreen(screenOpt.get());
		//save show first
		Show savedShow= showRepository.save(show);
		
		//auto create seats
		int totalSeats=screenOpt.get().getTotalSeats();
		BigDecimal seatPrice=savedShow.getSeatPrice();
		List<String> seatNumbers=generateSeatNumbers(totalSeats);
		
		List<ShowSeat> showSeats=new ArrayList<ShowSeat>();
		for(String seatNum: seatNumbers) {
			ShowSeat seat=new ShowSeat();
			seat.setShow(savedShow);
			seat.setSeatNumber(seatNum);
			seat.setPrice(seatPrice);
			seat.setSeatStatus(SeatStatus.AVAILABLE);
			showSeats.add(seat);
		}
		showSeatRepository.saveAll(showSeats);
		return savedShow;
	}
	
	//utility method for generating seat number
	public List<String> generateSeatNumbers(int totalSeats){
		List<String> seatNumbers= new ArrayList<String>();
		int colsPerRow=10;
		int totalRows=(int)Math.ceil((double)totalSeats/colsPerRow);
		for(int row=0;row<totalRows;row++) {
			char rowChar=(char)('A'+row);
			for(int col=1;col<=colsPerRow;col++) {
				int seatCount=row*colsPerRow+col;
				if(seatCount>totalSeats) {
					break;
				}
				seatNumbers.add(rowChar+String.valueOf(col));
			}
		}
		return seatNumbers;
	}
	
	public Show updateShow(Long id, Show showDetails) {
		Show existingShow=showRepository.findById(id).orElseThrow(()->new RuntimeException("Show not found"));
		Movie movie=movieRepository.findById(showDetails.getMovie().getId())
				.orElseThrow(()->new RuntimeException("Movie not found"));
		Screen screen=screenRepository.findById(showDetails.getScreen().getId())
				.orElseThrow(()->new RuntimeException("Screen Not found"));
		existingShow.setMovie(movie);
		existingShow.setScreen(screen);
		existingShow.setShowTime(showDetails.getShowTime());
		existingShow.setSeatPrice(showDetails.getSeatPrice());
		return showRepository.save(existingShow);
	}
	
	@Transactional
	public void deleteShow(Long id) {
		showSeatRepository.deleteByShowId(id);
		showRepository.deleteById(id);
	}

}
