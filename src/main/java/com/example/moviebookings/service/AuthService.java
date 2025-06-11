package com.example.moviebookings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.UserRepository;
import com.example.moviebookings.request.LoginRequest;
import com.example.moviebookings.request.RegisterRequest;
import com.example.moviebookings.response.AuthResponse;

import jakarta.annotation.PostConstruct;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthResponse register(RegisterRequest request) {
		try {
		User user=new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("ROLE_USER");
		
		userRepository.save(user);
		String token= jwtService.generateToken(user.getEmail(),user.getRole());
		return new AuthResponse(token);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("Error during registration");
		}
	}
	
	public AuthResponse login(LoginRequest request) {
		
		 // Authenticate using the email and password from the request
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
     // Extract email and role manually
        String email = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        String jwtToken = jwtService.generateToken(email, role);

        return new AuthResponse(jwtToken);
	}
	

}
