package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.auth.models.VerificationToken;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.gameshop.ecommerce.exception.RequestException.badRequestException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;
    @Value("${app.frontend.url}")
    private String url;

    private SimpleMailMessage prepareMessage() {
        final var message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        return message;
    }

    @SneakyThrows
    public void sendVerificationEmail(VerificationToken token) {
        final var message = prepareMessage();
        message.setTo(token.getUser().getEmail());
        message.setSubject("Verify your e-mail to activate your account.");
        message.setText("Please follow the link below to verify your e-mail to activate your account.\n " +
                url + "/verify?token=" + token.getToken());
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw badRequestException("Failed to send verification email. Message: " + ex.getMessage());
        }
    }

    @SneakyThrows
    public void sendPasswordResetEmail(LocalUserEntity user, String token) {
        final var message = prepareMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password reset request.");
        message.setText("Please follow the link below to reset your password.\n " + url + "/reset?token=" + token);

        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw badRequestException("Failed to send password reset email. Message: " + ex.getMessage());
        }
    }
}