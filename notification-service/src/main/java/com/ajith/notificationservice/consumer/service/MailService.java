package com.ajith.notificationservice.consumer.service;

import com.ajith.notificationservice.event.QuizSubmitEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(QuizSubmitEvent event) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailMessage = javaMailSender.createMimeMessage ();
        MimeMessageHelper helper = new MimeMessageHelper ( emailMessage );

        helper.setFrom ( "jquiz@gmail.com" ,"J QUIZ" );
        helper.setTo ( event.getUserEmail () );
        helper.setSubject (" Your quiz result is here!" );
        String content = "<html><body style='font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #007bff;'>"+"Quiz name : "+ event.getQuiz_name ()+"</h2>"
                + "<h3>Hello,</h3>"
                + "<4>You have attempted very well </h4>"
                + "<h3> You scored    "+ event.getScore () + "   out of   " +event.getTotal_question ()+"</h3>"
                + "<p>Appreciate your participation try another quiz All the best:</p>"
                + "</body></html>";

        helper.setText(content, true);
        javaMailSender.send ( emailMessage );
    }
}
