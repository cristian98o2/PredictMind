package com.predictmind.backend.PredictMindapp.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void enviarEmail(String para, String asunto, String mensaje) {
		SimpleMailMessage email= new SimpleMailMessage();
		email.setFrom("mindpredict@gmail.com");
		email.setTo(para);
		email.setText(mensaje);
		email.setSubject(asunto);
		
		mailSender.send(email);
	}
}
