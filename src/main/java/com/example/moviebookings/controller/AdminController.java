package com.example.moviebookings.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.UserRepository;
import com.example.moviebookings.service.UserService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	@PutMapping("/{email}/toggle-status")
    public ResponseEntity<?> toggleUserStatus(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        System.out.println("Old status: " + user.isEnabled());
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        System.out.println("New status: " + user.isEnabled());

        String status = user.isEnabled() ? "User unblocked successfully" : "User blocked successfully";
        return ResponseEntity.ok(Map.of("message", status));
    }
	

	@DeleteMapping("/{email}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable String email) {
		 Optional<User> user = userRepository.findByEmail(email);
		    if (user.isEmpty()) {
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found"));
		    }
		    userRepository.delete(user.get());
		    return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
