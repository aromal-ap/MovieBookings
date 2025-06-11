package com.example.moviebookings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.moviebookings.entity.Show;
import com.example.moviebookings.entity.ShowSeat;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long>{

	List<ShowSeat> findByShowId(Long showId);
	List<ShowSeat> findByShowIdAndSeatStatus(Long showId,ShowSeat.SeatStatus status);
	void deleteByShowId(Long showId);
}
