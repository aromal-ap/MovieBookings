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

import com.example.moviebookings.entity.Screen;
import com.example.moviebookings.service.ScreenService;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

	@Autowired
	private ScreenService screenService;
	
	@GetMapping
	public List<Screen> getAllSCreens(){
		return screenService.getAllScreens();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Screen> getScreenByID(@PathVariable Long id){
		Optional<Screen> screen=screenService.getScreenByID(id);
		return screen.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Screen> addScreen(@RequestBody Screen screen){
		Screen savedScreen=screenService.addScreen(screen);
		return new ResponseEntity<>(savedScreen,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Screen> updateScreen(@PathVariable Long id,@RequestBody Screen screenDetails){
		Screen updatedScreen=screenService.updateScreen(id, screenDetails);
		return ResponseEntity.ok(updatedScreen);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteScreen(@PathVariable Long id){
		screenService.deleteSCreen(id);
		return ResponseEntity.noContent().build();
	}
}
