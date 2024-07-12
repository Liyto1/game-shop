package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.auth.model.VerificationToken;
import com.gameshop.ecommerce.exception.EmailFailureException;
import com.gameshop.ecommerce.user.model.LocalUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromAddress;
    @Value("${app.frontend.url}")
    private String url;

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
                url + "/verify?token=" + token.getToken());
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException("Failed to send verification email. Message: " + ex.getMessage(), ex.getCause());
        }
    }

    public void sendPasswordResetEmail(LocalUser user, String token) throws EmailFailureException {
        SimpleMailMessage message = prepareMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password reset request.");
        message.setText("Please follow the link below to reset your password.\n " +
                url + "/reset?token=" + token);
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException("Failed to send password reset email. Message: " + ex.getMessage(), ex.getCause());
        }
    }
}
