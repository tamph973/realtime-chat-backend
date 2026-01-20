-- ====================================
-- V5: Create Message Status Table
-- Description: Track message delivery and read status
-- Author: Pham Minh Tam
-- Date: 2026-01-21
-- ====================================

CREATE TABLE message_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status ENUM('SENT', 'DELIVERED', 'SEEN') NOT NULL DEFAULT 'SENT',
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_status (message_id, user_id),
    INDEX idx_message (message_id),
    INDEX idx_user (user_id),
    INDEX idx_status (status),
    INDEX idx_timestamp (timestamp),
    FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE message_status COMMENT = 'Message delivery and read receipts';