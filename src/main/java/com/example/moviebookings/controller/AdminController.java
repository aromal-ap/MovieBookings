package com.example.moviebookings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
}
