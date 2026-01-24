package com.tamph.chatapp.user.service;

import com.tamph.chatapp.user.entity.UserEntity;
import com.tamph.chatapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountSecurityService {

    private final UserRepository userRepository;

    // Configuration constants
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_DURATION_MINUTES = 15;

    /**
     * Handle failed login attempt
     * Lock account after MAX_FAILED_ATTEMPTS
     */
    @Transactional
    public void handleFailedLogin(UserEntity user) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);

        if(attempts >= MAX_FAILED_ATTEMPTS) {
            log.warn("User {} exceeded max login attempts. Locking account for {} minutes",
                    user.getEmail(), LOCK_DURATION_MINUTES);

            user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCK_DURATION_MINUTES));
        }

        userRepository.save(user);
    }

    /**
     * Reset failed login attempts on successful login
     */
    @Transactional
    public void handleSuccessfulLogin(UserEntity user) {
        if(user.getFailedLoginAttempts() > 0) {
            user.setFailedLoginAttempts(0);
            user.setLockedUntil(null);
            userRepository.save(user);
            log.info("Reset failed login attempts for user: {}", user.getEmail());
        }
    }

    /**
     * Permanently lock account
     */
    @Transactional
    public void lockAccount(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setIsLocked(true);
        userRepository.save(user);
        log.warn("Account permanently locked: {}", user.getEmail());
    }

    /**
     * Unlock account
     */
    @Transactional
    public void unlockAccount(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setIsLocked(false);
        user.setLockedUntil(null);
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
        log.info("Account unlocked: {}", user.getEmail());
    }

    /**
     * Check if temporary lock has expired
     */
    public boolean isTemporaryLockExpired(UserEntity user) {
        if (user.getLockedUntil() == null) {
            return true;
        }
        return LocalDateTime.now().isAfter(user.getLockedUntil());
    }

    /**
     * Set account expiration date
     * Trail account (30d)
     */
    @Transactional
    public void setAccountExpiration(Long userId, LocalDateTime expirationDate) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountExpiredAt(expirationDate);
        userRepository.save(user);
        log.info("Account expiration set for user {}: {}", user.getEmail(), expirationDate);
    }

    /**
     * Set password expiration (force password change)
     */
    @Transactional
    public void setCredentialsExpiration(Long userId, LocalDateTime expirationDate) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setCredentialsExpiredAt(expirationDate);
        userRepository.save(user);
        log.info("Credentials expiration set for user {}: {}", user.getEmail(), expirationDate);
    }

    /**
     * Force password change (expire credentials immediately)
     */
    @Transactional
    public void forcePasswordChange(Long userId) {
        setCredentialsExpiration(userId, LocalDateTime.now());
    }
}
