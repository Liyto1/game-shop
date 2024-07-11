package com.gameshop.ecommerce.auth.controller;

import com.gameshop.ecommerce.user.model.LocalUser;
import com.gameshop.ecommerce.auth.model.AuthResponse;
import com.gameshop.ecommerce.auth.model.LoginBody;
import com.gameshop.ecommerce.auth.model.PasswordResetBody;
import com.gameshop.ecommerce.auth.model.RegistrationBody;
import com.gameshop.ecommerce.exception.EmailFailureException;
import com.gameshop.ecommerce.exception.EmailNotFoundException;
import com.gameshop.ecommerce.exception.UserAlreadyExistException;
import com.gameshop.ecommerce.exception.UserNotVerifiedException;
import com.gameshop.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistrationBody registrationBody) {
        AuthResponse authResponse = new AuthResponse();
        try {
            userService.registerUser(registrationBody);
            log.info("User registered successfully");
            authResponse.setSuccess(true);
            return ResponseEntity.ok(authResponse);
        } catch (UserAlreadyExistException e) {
            authResponse.setSuccess(false);
            authResponse.setFailureReason("USER_ALREADY_EXISTS");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponse);
        } catch (EmailFailureException e) {
            log.info(e.getMessage());
            authResponse.setSuccess(false);
            authResponse.setFailureReason("EMAIL_SEND_FAILURE");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(authResponse);
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginBody loginBody) {
        String jwt = null;
        try {
            jwt = userService.loginUser(loginBody);

        } catch (UserNotVerifiedException ex) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if (ex.isNewEmailSent()) {
                reason += "EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            AuthResponse response = new AuthResponse();
            log.info("User logged in successfully");
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }

    @CrossOrigin
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @CrossOrigin
    @GetMapping("/me")
    public LocalUser getLoggedUserProfile(@AuthenticationPrincipal LocalUser user) {
        return user;
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok().build();
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found");
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid PasswordResetBody body) {

        userService.resetPassword(body);
        return ResponseEntity.ok().build();
    }


}
