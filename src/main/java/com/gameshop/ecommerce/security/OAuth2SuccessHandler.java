package com.gameshop.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameshop.ecommerce.auth.models.AuthResponse;
import com.gameshop.ecommerce.user.store.LocalUserRepository;
import com.gameshop.ecommerce.user.store.LocalUserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JWTService jwtService;
    private final LocalUserRepository localUserRepository;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Optional<LocalUserEntity> opUser = localUserRepository.findByEmailIgnoreCase(email);

        LocalUserEntity user;
        if (opUser.isPresent()) {
            user = opUser.get();
        } else {
            user = LocalUserEntity.builder()
                    .firstName(oAuth2User.getAttribute("given_name"))
                    .lastName(oAuth2User.getAttribute("family_name"))
                    .email(email)
                    .authType("OAuth2")
                    .authProvider("Google")
                    .isEmailVerified(true)
                    .build();
            localUserRepository.save(user);
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

