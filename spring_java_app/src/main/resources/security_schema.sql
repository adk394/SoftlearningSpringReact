-- Script SQL para crear las tablas de seguridad (JWT)
-- Nota: Con spring.jpa.hibernate.ddl-auto=update, estas tablas se crean automaticamente

-- Tabla de roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255)
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

-- Tabla de relacion usuarios-roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Tabla de tokens JWT
CREATE TABLE IF NOT EXISTS tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) UNIQUE,
    token_type VARCHAR(255) DEFAULT 'BEARER',
    is_revoked BOOLEAN,
    is_expired BOOLEAN,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Consulta para ver usuarios con sus roles (para pruebas)
SELECT 
    u.username,
    r.role_name AS role_name
FROM
    users u
        INNER JOIN
    user_roles ur ON u.id = ur.user_id
        INNER JOIN
    roles r ON ur.role_id = r.id;
