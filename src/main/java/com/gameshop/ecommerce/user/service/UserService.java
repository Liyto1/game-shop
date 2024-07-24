package com.gameshop.ecommerce.user.service;

import com.gameshop.ecommerce.auth.repository.VerificationTokenRepository;
import com.gameshop.ecommerce.auth.models.LoginBody;
import com.gameshop.ecommerce.auth.models.PasswordResetBody;
import com.gameshop.ecommerce.auth.models.RegistrationBody;
import com.gameshop.ecommerce.auth.models.VerificationToken;
import com.gameshop.ecommerce.security.JWTService;
import com.gameshop.ecommerce.security.MyPasswordEncoder;
import com.gameshop.ecommerce.user.store.LocalUserRepository;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.gameshop.ecommerce.exception.RequestException.badRequestException;
import static com.gameshop.ecommerce.exception.RequestException.notFoundRequestException;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final LocalUserRepository localUserRepository;
    private final EncryptionService encryptionService;
    private final EmailService emailService;
    private final MyPasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Transactional
    @SneakyThrows
    public void registerUser(RegistrationBody registration) {
        if (localUserRepository.findByEmailIgnoreCase(registration.getEmail()).isPresent()
                || localUserRepository.findByPhone(registration.getPhoneNumber()).isPresent()) {
            throw badRequestException("404");
        }

        final var user = LocalUserEntity.builder()
                .firstName(registration.getFirstName())
                .lastName(registration.getLastName())
                .email(registration.getEmail())
                .authProvider("Registration Form")
                .phone(registration.getPhoneNumber())
                .password(passwordEncoder.passwordEncoder().encode(registration.getPassword()))
                .authType("Site")
                .build();

        VerificationToken verificationToken = generateVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        localUserRepository.save(user);
    }

    private VerificationToken generateVerificationToken(LocalUserEntity user) {
        final var verificationToken = VerificationToken.builder()
                .token(jwtService.generateVerificationJWT(user))
                .createdTimestamp(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .build();

        user.getVerificationTokens().add(verificationToken);

        return verificationToken;
    }

    @SneakyThrows
    public String loginUser(LoginBody loginBody) {
        Optional<LocalUserEntity> opUser = localUserRepository.findByEmailIgnoreCase(loginBody.getEmail());
        if (opUser.isPresent()) {
            LocalUserEntity user = opUser.get();
            if (passwordEncoder.checkPassword(loginBody.getPassword(), user.getPassword())) {
                if (Boolean.TRUE.equals(user.getIsEmailVerified())) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.isEmpty() ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = generateVerificationToken(user);
                        verificationTokenRepository.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw notFoundRequestException(String.valueOf(resend));
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUserEntity user = verificationToken.getUser();
            if (Boolean.FALSE.equals(user.getIsEmailVerified())) {
                user.setIsEmailVerified(true);
                localUserRepository.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public void forgotPassword(String email) {
        Optional<LocalUserEntity> opUser = localUserRepository.findByEmailIgnoreCase(email);

        if (opUser.isPresent()) {
            LocalUserEntity user = opUser.get();
            String token = jwtService.generatePasswordResetJWT(user);
            emailService.sendPasswordResetEmail(user, token);
        } else {
            throw badRequestException("404");
        }
    }

    @Transactional
    public void resetPassword(PasswordResetBody body) {
        final var opUser = localUserRepository.findByEmailIgnoreCase(
                jwtService.getResetPasswordEmail(body.getToken())
        );

        if (opUser.isPresent()) {
            LocalUserEntity user = opUser.get();
            user.setPassword(passwordEncoder.passwordEncoder().encode(body.getPassword()));
            localUserRepository.save(user);
        }
    }
}