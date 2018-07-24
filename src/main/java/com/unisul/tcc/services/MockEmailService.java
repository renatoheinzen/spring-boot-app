package com.unisul.tcc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.unisul.tcc.domain.Pedido;

public class MockEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		// TODO Auto-generated method stub
		LOG.info("Simulando envio de email");
		LOG.info(msg.toString());
		LOG.info("Email Enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		// TODO Auto-generated method stub
		LOG.info("Simulando envio de email HTML");
		LOG.info(msg.toString());
		LOG.info("Email Enviado");
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		// TODO Auto-generated method stub
		
	}

}
