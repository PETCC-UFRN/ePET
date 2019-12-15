package br.ufrn.ePET.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
/*
@Component
public class EmailConfig {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void enviarEmail(String email_petiano, String email_requisitante, String tutoria, String petiano) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(email_petiano);
		smm.setText("Olá " + petiano + "!\n"
				+ "Tem uma nova solicitação de tutoria da disciplina de " + tutoria + " feita por " + email_requisitante);
		try {
			javaMailSender.send(smm);
		} catch (Exception e) {
			throw new RuntimeException("Houve algum erro no envio do seu email!\n" + e);
		}
	}
}*/

