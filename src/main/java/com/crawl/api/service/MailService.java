package com.crawl.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.crawl.api.entity.NotificationEmail;
import com.crawl.api.exceptions.SpringException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailService {
	
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	 @Async
	    void sendMail(NotificationEmail notificationEmail) {
	        MimeMessagePreparator messagePreparator = mimeMessage -> {
	            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	            messageHelper.setFrom("devprogress23@email.com");
	            messageHelper.setTo(notificationEmail.getRecipient());
	            messageHelper.setSubject(notificationEmail.getSubject());
	            messageHelper.setText(notificationEmail.getBody());
	        };
	        try {
	            mailSender.send(messagePreparator);
	            System.out.println("Activation email sent!!");
	        } catch (MailException e) {
	        	System.out.println("Exception occurred when sending mail");
	            throw new SpringException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
	        }
	    }

}
