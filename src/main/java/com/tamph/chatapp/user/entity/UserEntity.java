package com.tamph.chatapp.user.entity;

import com.tamph.chatapp.common.enums.Role;
import com.tamph.chatapp.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.OFFLINE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_locked")
    private Boolean isLocked = false;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "account_expired_at")
    private LocalDateTime accountExpiredAt;

    @Column(name = "credentials_expired_at")
    private LocalDateTime credentialsExpiredAt;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Account is not expired if accountExpiredAt is null or in the future
     * Trail account (30d)
     */

    @Override
    public boolean isAccountNonExpired() {
        return accountExpiredAt == null ||
                LocalDateTime.now().isBefore(accountExpiredAt);
    }

    /**
     * Account is not locked if:
     * 1. isLocked is false
     * 2. lockedUntil is null or already passed
     */

    @Override
    public boolean isAccountNonLocked() {
        if(isLocked != null && isLocked) {
            return false;
        }

        return lockedUntil == null || LocalDateTime.now().isAfter(lockedUntil);
    }

    /**
     * Credentials (password) not expired if credentialsExpiredAt is null or in the future
     *  Change password (90d)
     */

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpiredAt == null ||
                LocalDateTime.now().isBefore(credentialsExpiredAt);
    }

    /**
     * Account is enabled if isActive is true and email is verified (optional)
     *  Require email verification for account to be enabled (uncomment if needed)
     *  return isActive != null && isActive && isEmailVerified != null && isEmailVerified;
     */
    /**
     * Account is enabled if isActive is true
     *  Verify email => active = true
     */
    @Override
    public boolean isEnabled() {
        return isActive != null && isActive;
    }
}
