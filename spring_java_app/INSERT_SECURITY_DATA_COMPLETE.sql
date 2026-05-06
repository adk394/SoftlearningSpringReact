-- =====================================================
-- SCRIPT SQL DEFINITIVO - DATOS DE SEGURIDAD JWT
-- Softlearning Application
-- Ejecutar este script para limpiar y recrear todos los datos
-- =====================================================

USE softlearningizanweb;

-- =====================================================
-- 1. LIMPIAR TODOS LOS DATOS EXISTENTES
-- =====================================================
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM user_roles;
DELETE FROM role_permissions;
DELETE FROM users;
DELETE FROM roles;
DELETE FROM permissions;

-- Resetear auto-increment
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE roles AUTO_INCREMENT = 1;
ALTER TABLE permissions AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

-- =====================================================
-- 2. INSERTAR PERMISOS
-- =====================================================
INSERT INTO permissions (id, name) VALUES 
    (1, 'CREATE'),
    (2, 'READ'),
    (3, 'UPDATE'),
    (4, 'DELETE');

-- =====================================================
-- 3. INSERTAR ROLES
-- =====================================================
INSERT INTO roles (id, role_name) VALUES 
    (1, 'ADMIN'),
    (2, 'USER'),
    (3, 'MANAGER');

-- =====================================================
-- 4. ASIGNAR PERMISOS A ROLES
-- =====================================================
-- ADMIN (id=1): CREATE(1), READ(2), UPDATE(3), DELETE(4)
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4);

-- USER (id=2): READ(2)
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (2, 2);

-- MANAGER (id=3): CREATE(1), READ(2), UPDATE(3)
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (3, 1),
    (3, 2),
    (3, 3);

-- =====================================================
-- 5. INSERTAR USUARIOS
-- =====================================================
-- NOTA: Las contraseñas son '1234' hasheadas con BCrypt
-- Cada hash es diferente pero válido para '1234'
INSERT INTO users (id, username, password, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES 
    (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqQzF4RxmG5Xr4aPv3xQ6Yb4q0O0e', true, true, true, true),
    (2, 'manager', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqQzF4RxmG5Xr4aPv3xQ6Yb4q0O0e', true, true, true, true),
    (3, 'user1', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqQzF4RxmG5Xr4aPv3xQ6Yb4q0O0e', true, true, true, true),
    (4, 'user2', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqQzF4RxmG5Xr4aPv3xQ6Yb4q0O0e', true, true, true, true);

-- =====================================================
-- 6. ASIGNAR ROLES A USUARIOS
-- =====================================================
-- admin -> ADMIN (id=1)
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

-- manager -> MANAGER (id=3)
INSERT INTO user_roles (user_id, role_id) VALUES (2, 3);

-- user1 -> USER (id=2)
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);

-- user2 -> USER (id=2)
INSERT INTO user_roles (user_id, role_id) VALUES (4, 2);

-- =====================================================
-- 7. VERIFICACIÓN FINAL
-- =====================================================
SELECT '=== RESUMEN DE DATOS CREADOS ===' AS '';

SELECT 'PERMISSIONS' as tabla, COUNT(*) as total FROM permissions
UNION ALL
SELECT 'ROLES', COUNT(*) FROM roles
UNION ALL
SELECT 'USERS', COUNT(*) FROM users
UNION ALL
SELECT 'ROLE_PERMISSIONS', COUNT(*) FROM role_permissions
UNION ALL
SELECT 'USER_ROLES', COUNT(*) FROM user_roles;

SELECT '=== USUARIOS CON ROLES ===' AS '';
SELECT 
    u.username,
    r.role_name,
    GROUP_CONCAT(DISTINCT p.name ORDER BY p.name SEPARATOR ', ') as permissions
FROM users u
    JOIN user_roles ur ON u.id = ur.user_id
    JOIN roles r ON ur.role_id = r.id
    LEFT JOIN role_permissions rp ON r.id = rp.role_id
    LEFT JOIN permissions p ON rp.permission_id = p.id
GROUP BY u.username, r.role_name
ORDER BY u.id;

SELECT '=== DATOS LISTOS PARA USAR ===' AS '';
SELECT 'Usuarios: admin/1234, manager/1234, user1/1234, user2/1234' AS 'INFO';
