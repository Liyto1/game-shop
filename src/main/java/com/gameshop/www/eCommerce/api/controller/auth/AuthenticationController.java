package com.gameshop.www.eCommerce.api.controller.auth;

import com.gameshop.www.eCommerce.api.model.AuthResponse;
import com.gameshop.www.eCommerce.api.model.LoginBody;
import com.gameshop.www.eCommerce.api.model.RegistrationBody;
import com.gameshop.www.eCommerce.exception.UserAlreadyExistException;
import com.gameshop.www.eCommerce.model.LocalUser;
import com.gameshop.www.eCommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            String jwt = userService.registerUser(registrationBody);
            log.info("User registered successfully");
            AuthResponse response = new AuthResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already");
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            AuthResponse response = new AuthResponse();
            log.info("User logged in successfully");
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/me")
    public LocalUser getLoggedUserProfile(@AuthenticationPrincipal LocalUser user) {
        return user;
    }
}
