package com.example.moviebookings.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moviebookings.entity.User;
import com.example.moviebookings.repository.UserRepository;

import jakarta.annotation.PostConstruct;


@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
		
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return userRepository.findByEmail(email)
				.orElseThrow(()->new UsernameNotFoundException("User not found with email:"));
		
	}
	
	public Optional<User> getUserByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public User updateUser(User updatedUser) {
		return userRepository.save(updatedUser);
	}

	@Transactional
	public void deleteUserByEmail(String email) {
		userRepository.deleteByEmail(email);
	}
	
	
	
//	@PostConstruct
//	public void init() {
//	    System.out.println("PasswordEncoder injected: " + (passwordEncoder != null));
//	}
}
