-- ====================================
-- V3: Create Conversation Members Table
-- Description: Members in each conversation
-- Author: Pham Minh Tam
-- Date: 2026-01-21
-- ====================================

CREATE TABLE conversation_members (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  conversation_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  role ENUM('ADMIN', 'MEMBER') NOT NULL DEFAULT 'MEMBER',
  joined_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_read_at DATETIME COMMENT 'Last time user read messages',
  is_muted BOOLEAN DEFAULT FALSE COMMENT 'User muted this conversation',
  nickname VARCHAR(100) COMMENT 'Custom nickname in this conversation',
  UNIQUE KEY unique_member (conversation_id, user_id),
  INDEX idx_conversation (conversation_id),
  INDEX idx_user (user_id),
  INDEX idx_role (role),
  FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE conversation_members COMMENT = 'Users participating in conversations';