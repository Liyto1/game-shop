package com.gameshop.www.eCommerce.user.service;

import com.gameshop.www.eCommerce.auth.model.VerificationToken;
import com.gameshop.www.eCommerce.exception.EmailFailureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Value("${app.frontend.url}")
    private String url;

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private SimpleMailMessage prepareMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        return message;
    }

    public void sendVerificationEmail(VerificationToken token) throws EmailFailureException {
        SimpleMailMessage message = prepareMessage();
        message.setTo(token.getUser().getEmail());
        message.setSubject("Verify your e-mail to activate your account.");
        message.setText("Please follow the link below to verify your e-mail to activate your account.\n " +
                url + "/auth/verify?token=" + token.getToken());
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }
}
