-- ====================================
-- V1: Create Users Table
-- Description: Core user management table
-- Author: Pham Minh Tam
-- Date: 2026-01-21
-- ====================================

CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   email VARCHAR(100) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   name VARCHAR(100) NOT NULL,
   avatar VARCHAR(255),
   status ENUM('ONLINE', 'OFFLINE', 'AWAY') NOT NULL DEFAULT 'OFFLINE',
   last_seen DATETIME,
   created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   is_active BOOLEAN DEFAULT TRUE,
   INDEX idx_email (email),
   INDEX idx_status (status),
   INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Add comment to table
ALTER TABLE users COMMENT = 'User accounts and profiles';