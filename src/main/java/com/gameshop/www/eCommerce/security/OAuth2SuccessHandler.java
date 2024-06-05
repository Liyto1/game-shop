package com.gameshop.www.eCommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameshop.www.eCommerce.auth.model.AuthResponse;
import com.gameshop.www.eCommerce.user.dao.LocalUserDAO;
import com.gameshop.www.eCommerce.user.model.LocalUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JWTService jwtService;
    private final LocalUserDAO localUserDao;
    private final ObjectMapper objectMapper;

    public OAuth2SuccessHandler(JWTService jwtService, LocalUserDAO localUserDao, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.localUserDao = localUserDao;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<LocalUser> opUser = localUserDao.findByEmailIgnoreCase(email);

        LocalUser user;
        if (opUser.isPresent()) {
            user = opUser.get();
        } else {
            user = LocalUser.builder()
                    .firstName(oAuth2User.getAttribute("given_name"))
                    .lastName(oAuth2User.getAttribute("family_name"))
                    .email(email)
                    .authType("OAuth2")
                    .authProvider("Google")
                    .isEmailVerified(true)
                    .build();
            localUserDao.save(user);
        }

        AuthResponse authResponse = new AuthResponse();
        String token = jwtService.generateJWT(user);
        authResponse.setJwt(token);
        authResponse.setSuccess(true);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(authResponse));
        response.getWriter().flush();
    }

}

