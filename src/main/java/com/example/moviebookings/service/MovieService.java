package com.example.moviebookings.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviebookings.entity.Movie;
import com.example.moviebookings.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	public List<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	public Optional<Movie> getMovieById(Long id){
		return movieRepository.findById(id);
	}

	public Movie addMovie(Movie movie) {
		return movieRepository.save(movie);
	}
	
	public Movie updateMovie(Long id,Movie movieDetails) {
		Movie movie=movieRepository.findById(id).orElseThrow(()->new RuntimeException("Movie not found"));
	    movie.setTitle(movieDetails.getTitle());
	    movie.setDescription(movieDetails.getDescription());
	    movie.setDuration(movieDetails.getDuration());
	    movie.setPosterUrl(movieDetails.getPosterUrl());
	    return movieRepository.save(movie);
	}
	
	public void deleteMovie(Long id) {
		movieRepository.deleteById(id);
	}
}
