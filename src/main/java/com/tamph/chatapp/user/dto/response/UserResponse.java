package com.tamph.chatapp.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tamph.chatapp.common.enums.Role;
import com.tamph.chatapp.common.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String avatar;
    private UserStatus status;
    private Role role;
    private LocalDateTime lastSeen;
    private LocalDateTime createdAt;

    // Security status
    private Boolean isActive;
    private Boolean isLocked;
    private LocalDateTime lockedUntil;
    private LocalDateTime accountExpiredAt;


    // Computed fields (not in DB)
    private Boolean isOnline;
    private Boolean isAccountLocked;

    /**
     * Check if user is currently online
     */
    public Boolean getIsOnline() {
        return status == UserStatus.ONLINE;
    }

    /**
     * Check if account is currently locked (permanent or temporary)
     */
    public Boolean getIsAccountLocked() {
        if (isLocked != null && isLocked) {
            return true;
        }
        if (lockedUntil != null) {
            return LocalDateTime.now().isBefore(lockedUntil);
        }
        return false;
    }

}