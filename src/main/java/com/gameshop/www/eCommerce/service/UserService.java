package com.gameshop.www.eCommerce.service;

import com.gameshop.www.eCommerce.api.model.LoginBody;
import com.gameshop.www.eCommerce.api.model.RegistrationBody;
import com.gameshop.www.eCommerce.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.dao.VerificationTokenDAO;
import com.gameshop.www.eCommerce.exception.EmailFailureException;
import com.gameshop.www.eCommerce.exception.UserAlreadyExistException;
import com.gameshop.www.eCommerce.exception.UserNotVerifiedException;
import com.gameshop.www.eCommerce.model.LocalUser;
import com.gameshop.www.eCommerce.model.VerificationToken;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final LocalUserDAO localUserDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
    private final EmailService emailService;
    private final VerificationTokenDAO verificationTokenDAO;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService, VerificationTokenDAO verificationTokenDAO, EmailService emailService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.verificationTokenDAO = verificationTokenDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailureException {
        if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDAO.findByPhoneNumber(registrationBody.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        LocalUser user = new LocalUser();
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setEmail(registrationBody.getEmail());
        user.setPhoneNumber(registrationBody.getPhoneNumber());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        VerificationToken verificationToken = generateVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return localUserDAO.save(user);
    }

    private VerificationToken generateVerificationToken(LocalUser user) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(loginBody.getEmail());
        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.checkPassword(loginBody.getPassword(), user.getPassword())) {
                if (user.getIsEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else {
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.isEmpty() ||
                            verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if (resend) {
                        VerificationToken verificationToken = generateVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.getIsEmailVerified()) {
                user.setIsEmailVerified(true);
                localUserDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}