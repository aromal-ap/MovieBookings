package com.example.moviebookings.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviebookings.entity.Movie;
import com.example.moviebookings.entity.Screen;
import com.example.moviebookings.entity.Show;
import com.example.moviebookings.repository.MovieRepository;
import com.example.moviebookings.repository.ScreenRepository;
import com.example.moviebookings.repository.ShowRepository;
import com.example.moviebookings.repository.ShowSeatRepository;
import com.example.moviebookings.service.ShowService;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

	@Autowired
	private ShowService showService;
			
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ScreenRepository screenRepository;
	
	@GetMapping
	public List<Show> getAllShows(){
		return showService.getAllShows();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Show> getShowByID(@PathVariable Long id){
		Optional<Show> show=showService.getShowByID(id);
		return show.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
	}
	
	//get show by movie id
	@GetMapping("/movie/{movieId}")
	public ResponseEntity<List<Show>>getShowByMovieId(@PathVariable Long movieId){
		List<Show> shows=showService.getShowByMovieId(movieId);
		return ResponseEntity.ok(shows);
	}
	
	@PostMapping
	public ResponseEntity<?> addShow(@RequestBody Show show){
		//validate movie and Screen id's
		Optional<Movie> movie=movieRepository.findById(show.getMovie().getId());
		Optional<Screen> screen=screenRepository.findById(show.getScreen().getId());
		
		if(!movie.isPresent() || !screen.isPresent()) {
			return new ResponseEntity<>("Movie or Screen not found",HttpStatus.BAD_REQUEST);
		}
		show.setMovie(movie.get());
		show.setScreen(screen.get());
		Show savedShow=showService.createShow(show);
		return new ResponseEntity<>(savedShow,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateShow(@PathVariable Long id,@RequestBody Show showDetails){
		try {
		Show updatedShow=showService.updateShow(id,showDetails);
		return ResponseEntity.ok(updatedShow);
		}catch (RuntimeException e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShow(@PathVariable Long id){
		showService.deleteShow(id);
		return ResponseEntity.noContent().build();
	}
}
