package com.example.moviebookings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") //apply to all endpoints
		        .allowedOrigins("http://localhost:4200") // angular frontend
		        .allowedMethods("GET","POST","PUT","DELETE")
		        .allowedHeaders("*")
		        .allowCredentials(true);
		        //.exposedHeaders("Authorization"); // expose the token if returned in header
			}
		};
	}
}
