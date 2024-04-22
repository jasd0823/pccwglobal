package com.pccwglobal.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendWelcomeEmail(String recipientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Welcome to Our Application!");
        message.setText("Dear User,\n\nWelcome to our application!\n\nBest regards,\nThe Team");
        emailSender.send(message);
    }
}

