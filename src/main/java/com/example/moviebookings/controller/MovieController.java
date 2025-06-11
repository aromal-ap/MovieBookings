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
import com.example.moviebookings.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	//Get all movies
	@GetMapping
	public List<Movie> getAllMovies(){
		return movieService.getAllMovies();
	}
	
	//Get a movie By id
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
		Optional<Movie> movie = movieService.getMovieById(id);
		return movie.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
	}
	
	//Add a movie
	@PostMapping
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
		
		Movie savedMovie=movieService.addMovie(movie);
		return new ResponseEntity<>(savedMovie,HttpStatus.CREATED);
	}
	
	//update An exsiting movie
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable Long id,@RequestBody Movie movieDetails){
		
		Movie updatedMovie=movieService.updateMovie(id, movieDetails);
		return ResponseEntity.ok(updatedMovie);
	}
	
	//delete movie
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
		movieService.deleteMovie(id);
		return ResponseEntity.noContent().build();
	}
}
