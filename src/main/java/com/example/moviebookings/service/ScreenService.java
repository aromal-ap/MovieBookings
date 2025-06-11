package com.example.moviebookings.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moviebookings.entity.Screen;
import com.example.moviebookings.repository.ScreenRepository;

@Service
public class ScreenService {

	@Autowired
	private ScreenRepository screenRepository;
	
	public List<Screen> getAllScreens(){
		return screenRepository.findAll();
	}
	
	public Optional<Screen> getScreenByID(Long id){
		return screenRepository.findById(id);
	}
	
	public Screen addScreen(Screen screen) {
		return screenRepository.save(screen);
	}
	
	public Screen updateScreen(Long id,Screen screenDetails) {
		
		Screen screen=screenRepository.findById(id).orElseThrow(()->new RuntimeException("Screen not found"));
		screen.setScreen(screenDetails.getScreen());
		screen.setTotalSeats(screenDetails.getTotalSeats());
		return screenRepository.save(screen);
	}
	
	public void deleteSCreen(Long id) {
		screenRepository.deleteById(id);
	}
}
