package com.tamph.chatapp.user.repository;

import com.tamph.chatapp.common.enums.UserStatus;
import com.tamph.chatapp.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findByStatus(UserStatus status);

    @Query("SELECT u FROM UserEntity u WHERE u.isActive = true")
    List<UserEntity> findAllActiveUsers();

    @Modifying
    @Query("UPDATE UserEntity u SET u.status =:status, " +
            "u.lastSeen =: lastSeen WHERE u.id =:userId" )
    void updateUserStatus(@Param("userId") Long userId,
                          @Param("status") UserStatus status,
                          @Param("lastSeen") LocalDateTime lastSeen);

    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<UserEntity> searchUsers(@Param("keyword") String keyword);

}
