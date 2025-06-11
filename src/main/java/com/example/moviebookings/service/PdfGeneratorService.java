package com.example.moviebookings.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.example.moviebookings.entity.Booking;
import com.example.moviebookings.entity.ShowSeat;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfGeneratorService {

	public ByteArrayInputStream generateBookingPdf(Booking booking) {
		
		Document document=new Document();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		try {
		
			PdfWriter.getInstance(document, out);
			document.open();
			Font titleFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD,18);
			Font normalFont=FontFactory.getFont(FontFactory.HELVETICA,12);
			
			Paragraph title=new Paragraph("🎟️ Movie Booking Confirmation",titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			
			document.add(Chunk.NEWLINE);
			
			document.add(new Paragraph("Booking Id: "+booking.getId(),normalFont));
			document.add(new Paragraph("Movie : "+booking.getShow().getMovie().getTitle(),normalFont));
			document.add(new Paragraph("Screen : "+booking.getShow().getScreen().getScreen(),normalFont));
			document.add(new Paragraph("Show Time : "+booking.getShow().getShowTime(),normalFont));
			document.add(new Paragraph("Seat Price : "+booking.getShow().getSeatPrice(),normalFont));
			
			//adds seats table
			document.add(new Paragraph("Seats :",normalFont));
			PdfPTable table =new PdfPTable(1);
			table.setWidthPercentage(50);
			for(ShowSeat seat:booking.getBookedSeats()) {
				table.addCell(seat.getSeatNumber());
			}
			document.add(table);
			
			document.add(new Paragraph("Total Amount : "+booking.getTotalAmount(),normalFont));
			document.add(new Paragraph("Payment Status : "+booking.getPaymentStatus(),normalFont));
			document.add(new Paragraph("Booking Time : "+booking.getBookingTime(),normalFont));
		
			document.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
}
