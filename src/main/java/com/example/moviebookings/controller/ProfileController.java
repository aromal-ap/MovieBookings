package com.example.moviebookings.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.UserRepository;
import com.example.moviebookings.service.UserService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
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
	
	@PutMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> changePassword(@RequestBody Map<String, String> body, Principal principal) {
        String email = principal.getName();
        String newPassword = body.get("password");

        System.out.println("New Password: " + newPassword + " | Email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message","Password updated successfully"));
    }
	
	@DeleteMapping
	public ResponseEntity<?> deleteAccount(Principal principal){
		String email=principal.getName();
		userService.deleteUserByEmail(email);
		return ResponseEntity.ok("Account deleted successfully");
	}
}
