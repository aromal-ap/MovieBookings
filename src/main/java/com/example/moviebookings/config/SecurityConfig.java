package com.example.moviebookings.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.moviebookings.service.UserService;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.Filter;
import jakarta.websocket.Session;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService; 
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.cors(Customizer.withDefaults())
		.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth
				//public authentication endpoints(login,register)
				.requestMatchers("/api/auth/**").permitAll()
				
				//admin endpoints
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				
				.requestMatchers(HttpMethod.GET,"/api/movies/**").permitAll()
				.requestMatchers("/api/movies/**").hasRole("ADMIN")
								
				.requestMatchers(HttpMethod.GET,"/api/screens/**").permitAll()
				.requestMatchers("/api/screens/**").hasRole("ADMIN")
				
				
				
				.requestMatchers(HttpMethod.GET,"/api/shows/**").permitAll()
				.requestMatchers("/api/shows/**").hasRole("ADMIN")
				
				//Allow booking APIs to USER and ADMIN
				.requestMatchers("/api/bookings/**").permitAll()
				
				//showseats
				.requestMatchers(HttpMethod.GET,"/api/showseats/**").permitAll()
				.requestMatchers("/api/showseats/**").hasRole("ADMIN")
				
				//Allow authenticated users to access and delete profile
				.requestMatchers("/api/profile/**").permitAll()
				.requestMatchers("/api/profile/change-password").authenticated()

							
				.anyRequest().authenticated()
				)
		        .sessionManagement(session-> session
		        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        )
		        .userDetailsService(userService)
		        .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
		
		return config.getAuthenticationManager();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:4200")); // frontend URL
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("*"));
	    configuration.setAllowCredentials(true);

	    org.springframework.web.cors.UrlBasedCorsConfigurationSource source =new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);

	    return source;
	}


}
