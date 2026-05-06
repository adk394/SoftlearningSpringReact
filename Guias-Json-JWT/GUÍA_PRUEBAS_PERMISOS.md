# 🔐 Guía de Pruebas de Permisos con Postman

## 📋 RESUMEN DE PERMISOS

### 👥 Roles y sus Permisos

| Rol | Permisos | Descripción |
|-----|----------|-------------|
| **ADMIN** | CREATE, READ, UPDATE, DELETE | Acceso total a todos los endpoints |
| **MANAGER** | CREATE, READ, UPDATE | Puede crear, leer y actualizar, pero NO eliminar |
| **USER** | READ | Solo puede leer datos, NO crear, actualizar ni eliminar |

### 🔒 Matriz de Permisos por Endpoint

| Endpoint | Método | USER | MANAGER | ADMIN |
|----------|--------|------|---------|-------|
| `/api/auth/login` | POST | ✅ | ✅ | ✅ |
| `/api/auth/register` | POST | ✅ | ✅ | ✅ |
| `/api/books/**` | GET | ✅ | ✅ | ✅ |
| `/api/books/**` | POST | ❌ | ✅ | ✅ |
| `/api/books/**` | PUT | ❌ | ✅ | ✅ |
| `/api/books/**` | DELETE | ❌ | ❌ | ✅ |
| `/api/clients/**` | GET | ✅ | ✅ | ✅ |
| `/api/clients/**` | POST | ❌ | ✅ | ✅ |
| `/api/clients/**` | PUT | ❌ | ✅ | ✅ |
| `/api/clients/**` | DELETE | ❌ | ❌ | ✅ |
| `/api/rest/orders/**` | GET | ✅ | ✅ | ✅ |
| `/api/rest/orders/**` | POST | ❌ | ✅ | ✅ |
| `/api/rest/orders/**` | PUT | ❌ | ✅ | ✅ |
| `/api/rest/orders/**` | DELETE | ❌ | ❌ | ✅ |

### 👤 Usuarios de Prueba

```
┌─────────────────────────────────────────────────────────────┐
│  USUARIO      │ CONTRASEÑA  │ ROL          │ PERMISOS       │
├─────────────────────────────────────────────────────────────┤
│  admin        │ 1234        │ ADMIN        │ CRUD completo  │
│  manager      │ 1234        │ MANAGER      │ CRU (sin D)    │
│  user1        │ 1234        │ USER         │ Solo R (read)  │
│  user2        │ 1234        │ USER         │ Solo R (read)  │
└─────────────────────────────────────────────────────────────┘
```

---

## 🧪 PLAN DE PRUEBAS COMPLETO

### 📁 Estructura recomendada en Postman

```
📁 Softlearning API Tests
├── 📁 01 - Auth (Público)
│   ├── POST Login - ADMIN
│   ├── POST Login - MANAGER
│   ├── POST Login - USER
│   └── POST Register
├── 📁 02 - USER Tests (Solo Lectura)
│   ├── GET Books ✅
│   ├── POST Books ❌ (403)
│   ├── PUT Books ❌ (403)
│   ├── DELETE Books ❌ (403)
│   └── [Test similar para Clients y Orders]
├── 📁 03 - MANAGER Tests (CRU sin Delete)
│   ├── GET Books ✅
│   ├── POST Books ✅
│   ├── PUT Books ✅
│   ├── DELETE Books ❌ (403)
│   └── [Test similar para Clients y Orders]
└── 📁 04 - ADMIN Tests (CRUD Completo)
    ├── GET Books ✅
    ├── POST Books ✅
    ├── PUT Books ✅
    ├── DELETE Books ✅
    └── [Test similar para Clients y Orders]
```

---

## 🔑 CONFIGURACIÓN DE POSTMAN PARA PRUEBAS

### Variables de Entorno por Usuario

Crea **3 entornos diferentes** para probar cada rol:

#### Entorno: `Softlearning - ADMIN`
```
baseUrl: http://localhost:8082
token: (vacío)
username: admin
password: 1234
```

#### Entorno: `Softlearning - MANAGER`
```
baseUrl: http://localhost:8082
token: (vacío)
username: manager
password: 1234
```

#### Entorno: `Softlearning - USER`
```
baseUrl: http://localhost:8082
token: (vacío)
username: user1
password: 1234
```

### Script de Login Automático

En cada request de login, agrega este script en la pestaña **Tests**:

```javascript
// Guardar el token en el entorno actual
var jsonData = pm.response.json();
pm.environment.set("token", jsonData.token);

// Verificar que el login fue exitoso
pm.test("Login exitoso - Status 200", function () {
    pm.response.to.have.status(200);
});

// Verificar que se recibió un token
pm.test("Token recibido", function () {
    pm.expect(jsonData.token).to.exist;
    pm.expect(jsonData.token).to.not.be.empty;
});

// Verificar que se recibieron roles
pm.test("Roles recibidos", function () {
    pm.expect(jsonData.roles).to.exist;
    pm.expect(jsonData.roles).to.be.an('array');
    console.log("Roles del usuario: " + jsonData.roles.join(", "));
});

// Log del usuario
console.log("Usuario: " + jsonData.username);
console.log("Token: " + jsonData.token.substring(0, 50) + "...");
```

---

## 📝 TESTS DETALLADOS POR ROL

### 🔴 TEST 1: USUARIO USER (Solo Lectura)

#### Paso 1: Login como USER
```http
POST {{baseUrl}}/api/auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "1234"
}
```

#### Paso 2: Probar GET (✅ Debe funcionar)
```http
GET {{baseUrl}}/api/books
Authorization: Bearer {{token}}
```

**Test en Postman:**
```javascript
pm.test("USER puede hacer GET - Status 200", function () {
    pm.response.to.have.status(200);
});
```

#### Paso 3: Probar POST (❌ Debe fallar con 403)
```http
POST {{baseUrl}}/api/books
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "id": 999,
  "title": "Libro de Prueba USER",
  "price": 10.00,
  "author": "Test",
  "isbn": "123-4567890123",
  "releaseDate": "2024-01-01",
  "publisher": "Test Publisher",
  "weight": 1.0,
  "height": 20.0,
  "width": 15.0,
  "depth": 2.0
}
```

**Test en Postman:**
```javascript
pm.test("USER NO puede hacer POST - Status 403", function () {
    pm.response.to.have.status(403);
});
```

#### Paso 4: Probar PUT (❌ Debe fallar con 403)
```http
PUT {{baseUrl}}/api/books/1
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "id": 1,
  "title": "Libro Modificado USER",
  "price": 20.00
}
```

**Test en Postman:**
```javascript
pm.test("USER NO puede hacer PUT - Status 403", function () {
    pm.response.to.have.status(403);
});
```

#### Paso 5: Probar DELETE (❌ Debe fallar con 403)
```http
DELETE {{baseUrl}}/api/books/1
Authorization: Bearer {{token}}
```

**Test en Postman:**
```javascript
pm.test("USER NO puede hacer DELETE - Status 403", function () {
    pm.response.to.have.status(403);
});
```

---

### 🟡 TEST 2: USUARIO MANAGER (Crear, Leer, Actualizar - Sin Eliminar)

#### Paso 1: Login como MANAGER
```http
POST {{baseUrl}}/api/auth/login
Content-Type: application/json

{
  "username": "manager",
  "password": "1234"
}
```

#### Paso 2: Probar GET (✅ Debe funcionar)
```http
GET {{baseUrl}}/api/books
Authorization: Bearer {{token}}
```

**Test en Postman:**
```javascript
pm.test("MANAGER puede hacer GET - Status 200", function () {
    pm.response.to.have.status(200);
});
```

#### Paso 3: Probar POST (✅ Debe funcionar)
```http
POST {{baseUrl}}/api/books
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "id": 100,
  "title": "Libro de Prueba MANAGER",
  "price": 25.99,
  "author": "Autor Manager",
  "isbn": "978-1234567890",
  "releaseDate": "2024-01-15",
  "publisher": "Manager Publishing",
  "weight": 0.8,
  "height": 22.0,
  "width": 16.0,
  "depth": 2.5
}
```

**Test en Postman:**
```javascript
pm.test("MANAGER puede hacer POST - Status 200", function () {
    pm.response.to.have.status(200);
});

// Guardar el ID del libro creado para pruebas posteriores
var jsonData = pm.response.json();
pm.environment.set("bookId", jsonData.id);
console.log("Libro creado con ID: " + jsonData.id);
```

#### Paso 4: Probar PUT (✅ Debe funcionar)
```http
PUT {{baseUrl}}/api/books/100
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "id": 100,
  "title": "Libro Actualizado MANAGER",
  "price": 29.99
}
```

**Test en Postman:**
```javascript
pm.test("MANAGER puede hacer PUT - Status 200", function () {
    pm.response.to.have.status(200);
});
```

#### Paso 5: Probar DELETE (❌ Debe fallar con 403)
```http
DELETE {{baseUrl}}/api/books/100
Authorization: Bearer {{token}}
```

**Test en Postman:**
```javascript
pm.test("MANAGER NO puede hacer DELETE - Status 403", function () {
    pm.response.to.have.status(403);
});
```

---

### 🟢 TEST 3: USUARIO ADMIN (CRUD Completo)

#### Paso 1: Login como ADMIN
```http
POST {{baseUrl}}/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "1234"
}
```

#### Paso 2: Probar GET (✅ Debe funcionar)
```http
GET {{baseUrl}}/api/books
Authorization: Bearer {{token}}
```

**Test en Postman:**
```javascript
pm.test("ADMIN puede hacer GET - Status 200", function () {
    pm.response.to.have.status(200);
});
```

#### Paso 3: Probar POST (✅ Debe funcionar)
```http
POST {{baseUrl}}/api/books
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "id": 200,
  "title": "Libro de Prueba ADMIN",
  "price": 35.99,
  "author": "Autor Admin",
  "isbn": "978-0987654321",
  "releaseDate": "2024-02-01",
  "publisher": "Admin Publishing",
  "weight": 1.1,
  "height": 24.0,
  "width": 17.0,
  "depth": 3.0
}
```

**Test en Postman:**
```javascript
pm.test("ADMIN puede hacer POST - Status 200", function () {
    pm.response.to.have.status(200);
});

// Guardar el ID del libro creado
var jsonData = pm.response.json();
pm.environment.set("adminBookId", jsonData.id);
```

#### Paso 4: Probar PUT (✅ Debe funcionar)
```http
PUT {{baseUrl}}/api/books/200
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "id": 200,
  "title": "Libro Actualizado ADMIN",
  "price": 39.99
}
```

**Test en Postman:**
```javascript
pm.test("ADMIN puede hacer PUT - Status 200", function () {
    pm.response.to.have.status(200);
});
```

#### Paso 5: Probar DELETE (✅ Debe funcionar)
```http
DELETE {{baseUrl}}/api/books/200
Authorization: Bearer {{token}}
```

**Test en Postman:**
```javascript
pm.test("ADMIN puede hacer DELETE - Status 200", function () {
    pm.response.to.have.status(200);
});
```

---

## 🔄 TESTS DE REGRESIÓN COMPLETOS

### Colección "Pruebas de Permisos Completa"

Crea una colección con este flujo:

#### 1️⃣ Setup - Crear datos de prueba (como ADMIN)
```javascript
// Pre-request Script
console.log("=== INICIANDO PRUEBAS DE PERMISOS ===");
```

**Crear libro de prueba:**
```http
POST {{baseUrl}}/api/books
Authorization: Bearer {{token}}

{
  "id": 9999,
  "title": "Libro Test Permisos",
  "price": 99.99,
  "author": "Test",
  "isbn": "999-9999999999",
  "releaseDate": "2024-01-01",
  "publisher": "Test",
  "weight": 1.0,
  "height": 20.0,
  "width": 15.0,
  "depth": 2.0
}
```

#### 2️⃣ USER Tests
- Cambiar al entorno "Softlearning - USER"
- Hacer login
- Probar todos los endpoints
- Verificar que solo GET funciona

#### 3️⃣ MANAGER Tests
- Cambiar al entorno "Softlearning - MANAGER"
- Hacer login
- Probar todos los endpoints
- Verificar que GET, POST, PUT funcionan
- Verificar que DELETE devuelve 403

#### 4️⃣ ADMIN Tests
- Cambiar al entorno "Softlearning - ADMIN"
- Hacer login
- Probar todos los endpoints
- Verificar que todo funciona

#### 5️⃣ Cleanup - Eliminar datos de prueba (como ADMIN)
```http
DELETE {{baseUrl}}/api/books/9999
Authorization: Bearer {{token}}
```

---

## 🎯 SCRIPTS DE PRUEBA AVANZADOS

### Test Suite Completo

Agrega este script en la pestaña **Tests** de tu colección:

```javascript
// Tests generales para cualquier endpoint
pm.test("Respuesta válida", function () {
    pm.response.to.be.ok; // Status 2xx
    pm.response.to.be.withBody;
    pm.response.to.have.jsonBody();
});

// Verificar tiempo de respuesta
pm.test("Tiempo de respuesta aceptable", function () {
    pm.expect(pm.response.responseTime).to.be.below(1000); // Menos de 1 segundo
});

// Log de resultados
console.log("Status: " + pm.response.code);
console.log("Tiempo: " + pm.response.responseTime + "ms");
console.log("Tamaño: " + pm.response.size().body + " bytes");
```

### Verificación de Headers de Seguridad

```javascript
pm.test("Headers de seguridad presentes", function () {
    pm.response.to.have.header("X-Content-Type-Options");
    pm.response.to.have.header("X-Frame-Options");
    // Nota: Estos headers dependen de tu configuración de Spring Security
});
```

---

## ❌ CÓDIGOS DE ERROR ESPERADOS

| Código | Significado | Cuándo ocurre |
|--------|-------------|---------------|
| **200** | OK | Operación exitosa |
| **201** | Created | Recurso creado exitosamente |
| **400** | Bad Request | Datos de entrada inválidos |
| **401** | Unauthorized | Token JWT inválido o expirado |
| **403** | Forbidden | Usuario autenticado pero sin permisos |
| **404** | Not Found | Recurso no existe |
| **500** | Internal Server Error | Error del servidor |

---

## 🚀 FLUJO DE PRUEBAS RÁPIDO

### Opción 1: Manual (Paso a paso)
1. Abre Postman
2. Selecciona el entorno del usuario a probar
3. Ejecuta el login
4. Prueba cada endpoint
5. Verifica los resultados
6. Cambia al siguiente usuario

### Opción 2: Runner Automático
1. Crea una colección con todos los tests
2. Ve a la pestaña "Runner" en Postman
3. Selecciona tu colección
4. Selecciona el entorno
5. Haz clic en "Run"
6. Revisa el reporte de resultados

---

## 📊 CHECKLIST DE VERIFICACIÓN

### ✅ USER (user1 / 1234)
- [ ] GET /api/books → 200 ✅
- [ ] POST /api/books → 403 ❌
- [ ] PUT /api/books/1 → 403 ❌
- [ ] DELETE /api/books/1 → 403 ❌
- [ ] GET /api/clients → 200 ✅
- [ ] POST /api/clients → 403 ❌
- [ ] GET /api/rest/orders → 200 ✅
- [ ] POST /api/rest/orders → 403 ❌

### ✅ MANAGER (manager / 1234)
- [ ] GET /api/books → 200 ✅
- [ ] POST /api/books → 200 ✅
- [ ] PUT /api/books/1 → 200 ✅
- [ ] DELETE /api/books/1 → 403 ❌
- [ ] GET /api/clients → 200 ✅
- [ ] POST /api/clients → 200 ✅
- [ ] PUT /api/clients/1 → 200 ✅
- [ ] DELETE /api/clients/1 → 403 ❌

### ✅ ADMIN (admin / 1234)
- [ ] GET /api/books → 200 ✅
- [ ] POST /api/books → 200 ✅
- [ ] PUT /api/books/1 → 200 ✅
- [ ] DELETE /api/books/1 → 200 ✅
- [ ] GET /api/clients → 200 ✅
- [ ] POST /api/clients → 200 ✅
- [ ] PUT /api/clients/1 → 200 ✅
- [ ] DELETE /api/clients/1 → 200 ✅
- [ ] GET /api/rest/orders → 200 ✅
- [ ] POST /api/rest/orders → 200 ✅
- [ ] PUT /api/rest/orders/1 → 200 ✅
- [ ] DELETE /api/rest/orders/1 → 200 ✅

---

## 💡 CONSEJOS PARA PRUEBAS EFECTIVAS

1. **Usa entornos separados**: Uno para cada rol
2. **Automatiza el login**: Guarda el token automáticamente
3. **Prueba en orden**: Siempre empieza con el login
4. **Limpia después**: Elimina los datos de prueba creados
5. **Documenta resultados**: Guarda screenshots de errores
6. **Verifica los logs**: Revisa la consola de Spring Boot
7. **Usa Postman Tests**: Automatiza las verificaciones

---

¡Ahora estás listo para probar todos los permisos de tu API! 🎉
