package com.example.moviebookings.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.UserRepository;
import com.example.moviebookings.request.LoginRequest;
import com.example.moviebookings.request.RegisterRequest;
import com.example.moviebookings.response.AuthResponse;
import com.example.moviebookings.service.AuthService;
import com.example.moviebookings.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		AuthResponse response=authService.register(request);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new AuthResponse("Invalid username or password"));
        } catch (DisabledException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Your account is blocked. Please contact admin."));
        }
	}
	
	@PutMapping("/reset-password")
	public ResponseEntity<Map<String,String>> resetPassword(@RequestBody Map<String,String> body){
	    String email=body.get("email");
	    String newPassword=body.get("newPassword");
	    
	    User user=userRepository.findByEmail(email)
	    		.orElseThrow(()->new RuntimeException("User not found with email: "+email));
	    user.setPassword(passwordEncoder.encode(newPassword));
	    userRepository.save(user);
	    
	    return ResponseEntity.ok(Map.of("message","Password Updated Successfully"));
	    
	}
}
