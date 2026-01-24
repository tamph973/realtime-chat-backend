package com.tamph.chatapp.auth.service.impl;

import com.tamph.chatapp.auth.dto.request.RegisterRequest;
import com.tamph.chatapp.auth.dto.response.AuthResponse;
import com.tamph.chatapp.common.enums.Role;
import com.tamph.chatapp.common.enums.UserStatus;
import com.tamph.chatapp.common.exception.ConflictException;
import com.tamph.chatapp.common.security.jwt.JwtTokenProvider;
import com.tamph.chatapp.user.dto.response.UserResponse;
import com.tamph.chatapp.user.entity.UserEntity;
import com.tamph.chatapp.user.mapper.UserMapper;
import com.tamph.chatapp.user.repository.UserRepository;
import com.tamph.chatapp.user.service.AccountSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final UserMapper userMapper;


    /**
     * Register new user
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists: " + request.getEmail());
        }

        // Create new user
        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.OFFLINE)
                .role(Role.USER)
                .isActive(true)
                .isLocked(false)
                .failedLoginAttempts(0)
                .build();



        UserEntity savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getEmail());

        // Generate tokens
        String accessToken = tokenProvider.generateToken(savedUser.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toResponse(savedUser))
                .build();
    }


}
