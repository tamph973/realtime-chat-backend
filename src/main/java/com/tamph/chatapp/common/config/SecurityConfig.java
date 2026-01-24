package com.tamph.chatapp.common.config;

import com.tamph.chatapp.common.constants.SecurityConstants;
import com.tamph.chatapp.common.security.CustomUserDetailsService;
import com.tamph.chatapp.common.security.jwt.JwtAuthenticationEntryPoint;
import com.tamph.chatapp.common.security.jwt.JwtAuthenticationFilter;
import com.tamph.chatapp.common.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
//    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter(
//                tokenProvider,
//                userDetailsService
//        );
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SecurityConstants.PUBLIC_URLS)
                        .permitAll()
                        .anyRequest().authenticated()
                )
//                .exceptionHandling(ex ->
//                        ex.authenticationEntryPoint(authenticationEntryPoint)
//                )
                .authenticationProvider(authenticationProvider());
//                .addFilterBefore(
//                        jwtAuthenticationFilter(),
//                        UsernamePasswordAuthenticationFilter.class
//                );

        return http.build();
    }
}
