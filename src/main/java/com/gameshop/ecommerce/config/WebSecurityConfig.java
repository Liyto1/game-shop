package com.gameshop.ecommerce.config;

import com.gameshop.ecommerce.security.JWTRequestFilter;
import com.gameshop.ecommerce.security.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final JWTRequestFilter jwtRequestFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers("/auth/**", "/login/**", "/swagger-ui.html", "/products/**",
//                            "/reviews/best-rate", "/swagger-ui/**", "/v3/api-docs/**", "/"
//                            , "/oauth2/callback", "/error", "/google/about", "/logout", "/generate/**").permitAll();
                    authorize.anyRequest().permitAll();
                })
                .oauth2Login(auth ->
                        auth.successHandler(oAuth2SuccessHandler))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

