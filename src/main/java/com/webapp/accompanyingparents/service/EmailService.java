package com.webapp.accompanyingparents.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String email, String msg, String subject) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(subject);
            message.setContent(msg, "text/html; charset=utf-8");
            message.setFrom("accompanyingparents8888@gmail.com");
            emailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}