package com.gameshop.ecommerce.auth.controller;

import com.gameshop.ecommerce.exception.*;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import com.gameshop.ecommerce.auth.models.AuthResponse;
import com.gameshop.ecommerce.auth.models.LoginBody;
import com.gameshop.ecommerce.auth.models.PasswordResetBody;
import com.gameshop.ecommerce.auth.models.RegistrationBody;
import com.gameshop.ecommerce.user.store.LocalUserDto;
import com.gameshop.ecommerce.user.service.AccountService;
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
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistrationBody registrationBody) {
        AuthResponse authResponse = new AuthResponse();
        try {
            userService.registerUser(registrationBody);
            log.info("User registered successfully");
            authResponse.setSuccess(true);
            return ResponseEntity.ok(authResponse);
        } catch (RequestException e) {
            authResponse.setSuccess(false);
            authResponse.setFailureReason("USER_ALREADY_EXISTS");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginBody loginBody) {
        String jwt;
        try {
            jwt = userService.loginUser(loginBody);

        } catch (RequestException ex) {
            AuthResponse response = new AuthResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if (ex.getStatus().is1xxInformational()) {
                reason += "EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

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

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/me")
    public LocalUserEntity getLoggedUserProfile(@AuthenticationPrincipal LocalUserEntity user) {
        return user;
    }

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok().build();
        } catch (RequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid PasswordResetBody body) {
        userService.resetPassword(body);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/account")
    public LocalUserDto updateInfo(@AuthenticationPrincipal LocalUserEntity user, @RequestBody LocalUserDto userDto) {
        return accountService.updateInfo(user, userDto);
    }
}