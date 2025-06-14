package org.rodeflow.RodeFlowServer.service.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("norely@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }

    public static int generateVerificationCode() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }

}
