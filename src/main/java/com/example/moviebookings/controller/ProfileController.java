package com.example.moviebookings.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviebookings.entity.User;
import com.example.moviebookings.service.UserService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	//get logged-in users profile
	@GetMapping
	public ResponseEntity<?> getProfile(Principal principal){
		String email=principal.getName();
		Optional<User> userOpt=userService.getUserByEmail(email);
		if(userOpt.isEmpty()) {
			ResponseEntity.notFound().build();
		}
		User user=userOpt.get();
		//don't send password back
		user.setPassword(null);
		return ResponseEntity.ok(user);
	}

	//update profile example: name, email etc.
	@PutMapping
	public ResponseEntity<?> updateProfile(Principal principal,@RequestBody User updatedUser){
		String email=principal.getName();
		Optional<User> userOpt=userService.getUserByEmail(email);
		if(userOpt.isEmpty()) {
			ResponseEntity.notFound().build();
		}
		User existingUser=userOpt.get();
		existingUser.setName(updatedUser.getName());
		existingUser.setEmail(updatedUser.getEmail());
		//for security do not update password or role here
		
		User savedUser=userService.updateUser(existingUser);
		savedUser.setPassword(null);
		return ResponseEntity.ok(savedUser);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteAccount(Principal principal){
		String email=principal.getName();
		userService.deleteUserByEmail(email);
		return ResponseEntity.ok("Account deleted successfully");
	}
}
