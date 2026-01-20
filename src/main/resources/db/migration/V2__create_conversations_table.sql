-- ====================================
-- V2: Create Conversations Table
-- Description: Chat conversations (1-1 and Group)
-- Author: Pham Minh Tam
-- Date: 2026-01-21
-- ====================================

CREATE TABLE conversations (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   type ENUM('PRIVATE', 'GROUP') NOT NULL,
   name VARCHAR(100) COMMENT 'Group name for GROUP type, NULL for PRIVATE',
   avatar VARCHAR(255) COMMENT 'Group avatar URL',
   description TEXT COMMENT 'Group description',
   created_by BIGINT COMMENT 'User who created the conversation',
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   is_active BOOLEAN DEFAULT TRUE,
   INDEX idx_type (type),
   INDEX idx_created_by (created_by),
   INDEX idx_created_at (created_at),
   FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE conversations COMMENT = 'Chat conversations (private and group)';