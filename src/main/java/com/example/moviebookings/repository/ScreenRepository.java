package com.example.moviebookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moviebookings.entity.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long>{

}
