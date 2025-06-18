package com.example.moviebookings.service;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendBookingConfirmation(String toEmail,ByteArrayInputStream pdf) {
		try {
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper =new MimeMessageHelper(message,true);
			helper.setTo(toEmail);
			helper.setSubject("Your Movie Ticket");
            helper.setText("Thank you for booking with us! Find your ticket attached.");
            byte[] pdfBytes=pdf.readAllBytes();
            
            helper.addAttachment("Ticket.pdf",new ByteArrayResource(pdfBytes));
            
            mailSender.send(message);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}
