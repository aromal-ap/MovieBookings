package com.example.moviebookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moviebookings.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>{

}
