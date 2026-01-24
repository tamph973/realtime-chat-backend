-- ====================================
-- V6: Add security fields to users table
-- Description: Add account locking, expiration, and login attempt tracking
-- Author: Pham Minh Tam
-- Date: 2026-01-24 - 3:56 AM
-- ====================================

ALTER TABLE users
    ADD COLUMN is_locked BOOLEAN DEFAULT FALSE AFTER is_email_verified,
ADD COLUMN account_expired_at DATETIME DEFAULT NULL AFTER is_locked,
ADD COLUMN credentials_expired_at DATETIME DEFAULT NULL AFTER account_expired_at,
ADD COLUMN failed_login_attempts INT DEFAULT 0 AFTER credentials_expired_at,
ADD COLUMN locked_until DATETIME DEFAULT NULL AFTER failed_login_attempts;

-- Add indexes for performance
CREATE INDEX idx_is_locked ON users(is_locked);
CREATE INDEX idx_locked_until ON users(locked_until);

-- Add comments
ALTER TABLE users MODIFY COLUMN is_locked BOOLEAN DEFAULT FALSE COMMENT 'Permanent lock flag';
ALTER TABLE users MODIFY COLUMN account_expired_at DATETIME COMMENT 'Account expiration timestamp';
ALTER TABLE users MODIFY COLUMN credentials_expired_at DATETIME COMMENT 'Password expiration timestamp';
ALTER TABLE users MODIFY COLUMN failed_login_attempts INT DEFAULT 0 COMMENT 'Number of consecutive failed login attempts';
ALTER TABLE users MODIFY COLUMN locked_until DATETIME COMMENT 'Temporary lock expiration timestamp';