# Documentación de Seguridad - Softlearning JWT

## Resumen de Roles y Permisos

Este documento describe la política de seguridad implementada en Softlearning usando JWT (JSON Web Tokens).

## Roles Definidos

| Rol | Descripción |
|-----|-------------|
| **ADMIN** | Administrador del sistema. Acceso total a todos los recursos. |
| **USER** | Usuario estándar. Solo puede consultar información. |
| **MANAGER** | Gestor de contenido. Puede crear, leer y actualizar, pero no eliminar. |

## Permisos Definidos

| Permiso | Descripción |
|---------|-------------|
| **READ** | Permite leer/consultar recursos |
| **CREATE** | Permite crear nuevos recursos |
| **UPDATE** | Permite actualizar recursos existentes |
| **DELETE** | Permite eliminar recursos |

## Matriz de Permisos por Rol

| Rol | READ | CREATE | UPDATE | DELETE |
|-----|:----:|:------:|:------:|:------:|
| ADMIN | ✅ | ✅ | ✅ | ✅ |
| USER | ✅ | ❌ | ❌ | ❌ |
| MANAGER | ✅ | ✅ | ✅ | ❌ |

## Acceso a Endpoints por Rol

### Endpoints Públicos (Sin autenticación)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/login` | Iniciar sesión y obtener token JWT |
| POST | `/api/auth/register` | Registrar nuevo usuario |

### Book Controller (`/api/books`)
| Método | Endpoint | Rol Requerido | Permiso |
|--------|----------|---------------|---------|
| GET | `/api/books` | USER, MANAGER, ADMIN | READ |
| POST | `/api/books` | MANAGER, ADMIN | CREATE |
| PUT | `/api/books/{id}` | MANAGER, ADMIN | UPDATE |
| DELETE | `/api/books/{id}` | ADMIN | DELETE |

### Client Controller (`/api/clients`)
| Método | Endpoint | Rol Requerido | Permiso |
|--------|----------|---------------|---------|
| GET | `/api/clients` | USER, MANAGER, ADMIN | READ |
| POST | `/api/clients` | MANAGER, ADMIN | CREATE |
| PUT | `/api/clients/{id}` | MANAGER, ADMIN | UPDATE |
| DELETE | `/api/clients/{id}` | ADMIN | DELETE |

### Order Controller (`/api/orders`)
| Método | Endpoint | Rol Requerido | Permiso |
|--------|----------|---------------|---------|
| GET | `/api/orders` | USER, MANAGER, ADMIN | READ |
| GET | `/api/orders/public` | USER, MANAGER, ADMIN | READ |
| GET | `/api/orders/{id}` | USER, MANAGER, ADMIN | READ |
| POST | `/api/orders` | MANAGER, ADMIN | CREATE |
| POST | `/api/orders/public` | MANAGER, ADMIN | CREATE |
| PUT | `/api/orders/{id}` | MANAGER, ADMIN | UPDATE |
| DELETE | `/api/orders/{id}` | ADMIN | DELETE |
| GET | `/api/orders/public/{id}` | USER, MANAGER, ADMIN | READ |

## Usuarios de Prueba

Los siguientes usuarios se crean automáticamente al iniciar la aplicación:

| Usuario | Contraseña | Rol | Descripción |
|---------|------------|-----|-------------|
| admin | 1234 | ADMIN | Acceso total al sistema |
| manager | 1234 | MANAGER | Gestor de contenido |
| user1 | 1234 | USER | Usuario estándar |
| user2 | 1234 | USER | Usuario estándar |

## Configuración JWT

- **Issuer**: softlearning
- **Secret Key**: softlearning-secret-key-2024-must-be-at-least-256-bits-long-for-security
- **Expiration**: 24 horas (86400000 ms)

## Flujo de Autenticación

1. El cliente envía credenciales (username/password) a `/api/auth/login`
2. El servidor valida las credenciales y genera un token JWT
3. El cliente incluye el token en el header `Authorization: Bearer <token>` en cada petición
4. El servidor valida el token y permite/deniega el acceso según los roles/permisos

## Notas de Implementación

- Los tokens JWT son stateless (no se almacenan en servidor)
- Se utiliza BCrypt para el hash de contraseñas
- La configuración de seguridad está en `SecurityConfig.java`
- El filtro JWT intercepta todas las peticiones para validar el token
