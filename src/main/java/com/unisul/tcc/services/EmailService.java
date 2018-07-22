package com.unisul.tcc.services;

import org.springframework.mail.SimpleMailMessage;

import com.unisul.tcc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
