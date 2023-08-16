package com.example.wantedpreonboardingbackend.common.security;

import com.example.wantedpreonboardingbackend.common.security.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
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
public class SpringSecurityConfig {

    private final JwtFilter jwtFilter;

    private final String[] swaggerURL = {
        "/v3/api-docs", "/configuration/ui",
        "/swagger-resources", "/configuration/security",
        "/swagger-ui.html", "/webjars/**", "/swagger/**",
        "/swagger-ui/**", "/swagger-resources/**","/v3/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                .requestMatchers(swaggerURL).permitAll()
                .requestMatchers("/member/**").permitAll()
                .requestMatchers("GET","/board/**").permitAll()
                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
