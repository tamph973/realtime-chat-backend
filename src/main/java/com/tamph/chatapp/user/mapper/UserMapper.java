package com.tamph.chatapp.user.mapper;

import com.tamph.chatapp.user.dto.response.UserResponse;
import com.tamph.chatapp.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    /**
     * Convert User entity to UserResponse DTO
     */
    public UserResponse toResponse(UserEntity user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .status(user.getStatus())
                .role(user.getRole())
                .lastSeen(user.getLastSeen())
                .createdAt(user.getCreatedAt())
                .isActive(user.getIsActive())
                .isLocked(user.getIsLocked())
                .lockedUntil(user.getLockedUntil())
                .accountExpiredAt(user.getAccountExpiredAt())
                .build();
    }

    /**
     * Convert User entity to simple UserResponse (minimal fields)
     * Used for chat participants list, search results, etc.
     */
    public UserResponse toSimpleResponse(UserEntity user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .avatar(user.getAvatar())
                .status(user.getStatus())
                .build();
    }

    /**
     * Convert list of Users to list of UserResponse
     */
    public List<UserResponse> toResponseList(List<UserEntity> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convert list of Users to list of simple UserResponse
     */
    public List<UserResponse> toSimpleResponseList(List<UserEntity> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(this::toSimpleResponse)
                .collect(Collectors.toList());
    }
}
