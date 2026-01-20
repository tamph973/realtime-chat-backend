-- ====================================
-- V4: Create Messages Table
-- Description: Chat messages in conversations
-- Author: Pham Minh Tam
-- Date: 2026-01-21
-- ====================================

CREATE TABLE messages (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  conversation_id BIGINT NOT NULL,
  sender_id BIGINT NOT NULL,
  content TEXT NOT NULL,
  type ENUM('TEXT', 'IMAGE', 'FILE', 'VIDEO', 'AUDIO', 'SYSTEM') NOT NULL DEFAULT 'TEXT',
  file_url VARCHAR(500) COMMENT 'URL for media/file messages',
  file_name VARCHAR(255) COMMENT 'Original file name',
  file_size BIGINT COMMENT 'File size in bytes',
  reply_to_id BIGINT COMMENT 'ID of message being replied to',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted BOOLEAN DEFAULT FALSE,
  deleted_at DATETIME,
  deleted_by BIGINT COMMENT 'User who deleted the message',
  INDEX idx_conversation (conversation_id),
  INDEX idx_sender (sender_id),
  INDEX idx_created_at (created_at),
  INDEX idx_conversation_created (conversation_id, created_at),
  INDEX idx_type (type),
  INDEX idx_reply_to (reply_to_id),
  FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE,
  FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (reply_to_id) REFERENCES messages(id) ON DELETE SET NULL,
  FOREIGN KEY (deleted_by) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE messages COMMENT = 'Chat messages';