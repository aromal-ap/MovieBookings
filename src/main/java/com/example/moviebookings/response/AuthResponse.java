package com.example.moviebookings.response;

public class AuthResponse {

	private String token;
    //private String message;
    
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuthResponse(String token) {
		super();
		this.token=token;
	}
	
//	public AuthResponse(String token, String message) {
//        this.token = token;
//        this.message = message;
//    }
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
