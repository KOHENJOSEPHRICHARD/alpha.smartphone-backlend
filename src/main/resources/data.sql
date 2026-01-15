-- Insert default admin user
-- Password: Alpha@2025
INSERT IGNORE INTO admins (id, username, email, password, full_name, phone_number, role, is_active, created_at, updated_at)
VALUES (1, 'admin', 'alpha.smartphone.cz@gmail.com', '$2a$10$XqS4F5gHKB8yP/WnkQvWK.k3HZL9qK3h0gYJvL8yZ9r7X3ZqL8QK2', 
        'Alpha SmartPhone Admin', '255629707898', 'SUPER_ADMIN', true, NOW(), NOW());
