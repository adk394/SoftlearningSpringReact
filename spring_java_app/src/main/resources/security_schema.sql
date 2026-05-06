-- Script SQL para crear las tablas de seguridad (JWT)
-- Nota: Con spring.jpa.hibernate.ddl-auto=update, estas tablas se crean automáticamente
-- Este script es útil para referencia o para crear manualmente las tablas

-- Tabla de permisos
CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Tabla de roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255)
);

-- Tabla de relación roles-permisos
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    is_enabled BOOLEAN,
    account_no_expired BOOLEAN,
    account_no_locked BOOLEAN,
    credential_no_expired BOOLEAN
);

-- Tabla de relación usuarios-roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Consulta para ver usuarios con sus roles y permisos
SELECT 
    u.username,
    r.role_name AS role_name,
    p.name AS permission_name
FROM
    users u
        INNER JOIN
    user_roles ur ON u.id = ur.user_id
        INNER JOIN
    roles r ON ur.role_id = r.id
        INNER JOIN
    role_permissions rp ON r.id = rp.role_id
        INNER JOIN
    permissions p ON rp.permission_id = p.id;
