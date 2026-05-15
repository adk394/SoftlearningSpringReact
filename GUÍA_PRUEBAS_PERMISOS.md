# Guía de Pruebas de Permisos con Postman

## Usuarios de Prueba

| Usuario | Contraseña | Rol     | Permisos                             |
| ------- | ---------- | ------- | ------------------------------------ |
| admin   | 1234       | ADMIN   | CRUD completo                        |
| manager | 1234       | MANAGER | Crear, Leer, Actualizar (sin Delete) |
| user1   | 1234       | USER    | Solo Lectura                         |

---

## Configuración en Postman

### 1. Crear Entorno

Crea un entorno llamado `Softlearning` con estas variables:

```
baseUrl: http://localhost:8082
token: (dejar vacío)
```

### 2. Script de Login

En cada request de login, pestaña **Tests**, agrega:

```javascript
var jsonData = pm.response.json();
pm.environment.set("token", jsonData.access_token);
```

---

## Orden de Pruebas

### PASO 1: Probar USER (Solo Lectura)

**Login:**

```http
POST {{baseUrl}}/api/auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "1234"
}
```

**Pruebas:**
| Método | Endpoint | Resultado Esperado |
|--------|----------|-------------------|
| GET | {{baseUrl}}/api/books | 200 OK ✅ |
| POST | {{baseUrl}}/api/books | 403 Forbidden ❌ |
| PUT | {{baseUrl}}/api/books/1 | 403 Forbidden ❌ |
| DELETE | {{baseUrl}}/api/books/1 | 403 Forbidden ❌ |

**Header para todas las peticiones:**

```
Authorization: Bearer {{token}}
```

---

### PASO 2: Probar MANAGER (CRU sin Delete)

**Login:**

```http
POST {{baseUrl}}/api/auth/login
Content-Type: application/json

{
  "username": "manager",
  "password": "1234"
}
```

**Pruebas:**
| Método | Endpoint | Resultado Esperado |
|--------|----------|-------------------|
| GET | {{baseUrl}}/api/books | 200 OK ✅ |
| POST | {{baseUrl}}/api/books | 200 OK ✅ |
| PUT | {{baseUrl}}/api/books/1 | 200 OK ✅ |
| DELETE | {{baseUrl}}/api/books/1 | 403 Forbidden ❌ |

**Body para POST/PUT:**

```json
{
  "id": 1,
  "title": "Libro Test",
  "price": 10.0,
  "author": "Autor",
  "isbn": "123-4567890123",
  "releaseDate": "2024-01-01",
  "publisher": "Test",
  "weight": 1.0,
  "height": 20.0,
  "width": 15.0,
  "depth": 2.0
}
```

---

### PASO 3: Probar ADMIN (CRUD Completo)

**Login:**

```http
POST {{baseUrl}}/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "1234"
}
```

**Pruebas:**
| Método | Endpoint | Resultado Esperado |
|--------|----------|-------------------|
| GET | {{baseUrl}}/api/books | 200 OK ✅ |
| POST | {{baseUrl}}/api/books | 200 OK ✅ |
| PUT | {{baseUrl}}/api/books/1 | 200 OK ✅ |
| DELETE | {{baseUrl}}/api/books/1 | 200 OK ✅ |

---

### PASO 4: Probar Refresh Token

**Con el token de ADMIN:**

```http
POST {{baseUrl}}/api/auth/refresh-token
Authorization: Bearer {{token}}
```

**Resultado:** Devuelve nuevo access_token (el refresh_token sigue siendo válido)

---

## Endpoints Adicionales para Probar

Repite los mismos tests con:

- `/api/clients/**` (clientes)
- `/api/rest/orders/**` (pedidos)

Resultados deben ser idénticos: USER solo GET, MANAGER GET/POST/PUT, ADMIN todo.

---

## Códigos de Respuesta

| Código | Significado                               |
| ------ | ----------------------------------------- |
| 200    | OK - Petición exitosa                     |
| 201    | Created - Recurso creado                  |
| 401    | Unauthorized - Token inválido o expirado  |
| 403    | Forbidden - Sin permisos para esta acción |
| 404    | Not Found - Recurso no existe             |

---

## Checklist Rápido

- [ ] USER: GET funciona, POST/PUT/DELETE dan 403
- [ ] MANAGER: GET/POST/PUT funcionan, DELETE da 403
- [ ] ADMIN: Todos los métodos funcionan
- [ ] Refresh token funciona
- [ ] Books, Clients y Orders tienen mismo comportamiento
