# 📮 Guía de Pruebas con Postman - Softlearning API

## 🚀 Configuración Inicial

### 1. Descargar e Instalar Postman
- Descarga Postman desde: https://www.postman.com/downloads/
- Instálalo en tu computadora

### 2. Crear una Colección
1. Abre Postman
2. Haz clic en **"Collections"** (en el panel izquierdo)
3. Haz clic en **"+"** para crear una nueva colección
4. Nómbrala: `Softlearning API`

### 3. Crear Variables de Entorno (Recomendado)
1. Haz clic en el ícono de **engranaje** (⚙️) arriba a la derecha
2. Selecciona **"Add"** para crear un nuevo entorno
3. Nómbralo: `Softlearning Local`
4. Agrega las siguientes variables:

| Variable | Valor Inicial | Descripción |
|----------|---------------|-------------|
| `baseUrl` | `http://localhost:8082` | URL base de tu API |
| `token` | *(dejar vacío)* | Se llenará automáticamente después del login |

5. Haz clic en **"Save"**
6. Selecciona este entorno en el dropdown arriba a la derecha (donde dice "No Environment")

---

## 🔐 1. AUTENTICACIÓN

### 📌 Login (Obtener Token JWT)

**Método:** `POST`  
**URL:** `{{baseUrl}}/api/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON):**
```json
{
  "username": "admin",
  "password": "1234"
}
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "id": 1,
  "username": "admin",
  "roles": ["ADMIN", "CREATE", "READ", "UPDATE", "DELETE"]
}
```

#### ⚙️ Script para guardar el token automáticamente:
En la pestaña **"Tests"** del request de login, agrega:

```javascript
// Guardar el token en la variable de entorno
var jsonData = pm.response.json();
pm.environment.set("token", jsonData.token);
console.log("Token guardado: " + jsonData.token);
```

---

### 📌 Registro de Usuario

**Método:** `POST`  
**URL:** `{{baseUrl}}/api/auth/register`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON):**
```json
{
  "username": "nuevousuario",
  "password": "password123",
  "roles": ["USER"]
}
```

**Respuesta esperada:**
```json
{
  "message": "Usuario registrado exitosamente!"
}
```

---

## 📚 2. CRUD DE LIBROS (Books)

### 📌 Obtener todos los libros

**Método:** `GET`  
**URL:** `{{baseUrl}}/api/books`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 📌 Crear un libro

**Método:** `POST`  
**URL:** `{{baseUrl}}/api/books`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{token}}
```

**Body (raw - JSON):**
```json
{
  "id": 1,
  "title": "El Quijote",
  "price": 29.99,
  "author": "Miguel de Cervantes",
  "isbn": "978-84-670-1359-1",
  "releaseDate": "1605-01-16",
  "publisher": "Editorial Clásica",
  "weight": 0.8,
  "height": 23.0,
  "width": 15.0,
  "depth": 3.0
}
```

---

### 📌 Actualizar un libro

**Método:** `PUT`  
**URL:** `{{baseUrl}}/api/books/1`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{token}}
```

**Body (raw - JSON):**
```json
{
  "id": 1,
  "title": "El Quijote - Edición Especial",
  "price": 35.99,
  "author": "Miguel de Cervantes",
  "isbn": "978-84-670-1359-1",
  "releaseDate": "1605-01-16",
  "publisher": "Editorial Clásica",
  "weight": 0.9,
  "height": 23.0,
  "width": 15.0,
  "depth": 3.5
}
```

---

### 📌 Eliminar un libro

**Método:** `DELETE`  
**URL:** `{{baseUrl}}/api/books/1`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

## 👥 3. CRUD DE CLIENTES (Clients)

### 📌 Obtener todos los clientes

**Método:** `GET`  
**URL:** `{{baseUrl}}/api/clients`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

### 📌 Crear un cliente

**Método:** `POST`  
**URL:** `{{baseUrl}}/api/clients`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{token}}
```

**Body (raw - JSON):**
```json
{
  "id": 1,
  "name": "Juan",
  "surname": "Pérez García",
  "email": "juan.perez@email.com",
  "phone": "+34600123456",
  "address": "Calle Mayor 123",
  "city": "Madrid",
  "country": "España",
  "zipcode": "28001"
}
```

---

### 📌 Actualizar un cliente

**Método:** `PUT`  
**URL:** `{{baseUrl}}/api/clients/1`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{token}}
```

---

### 📌 Eliminar un cliente

**Método:** `DELETE`  
**URL:** `{{baseUrl}}/api/clients/1`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

## 📦 4. CRUD DE ÓRDENES (Orders)

### 📌 Obtener todas las órdenes

**Método:** `GET`  
**URL:** `{{baseUrl}}/api/rest/orders`

**Headers:**
```
Authorization: Bearer {{token}}
Accept: application/json
```

---

### 📌 Obtener una orden por ID (JSON)

**Método:** `GET`  
**URL:** `{{baseUrl}}/api/rest/orders/1`

**Headers:**
```
Authorization: Bearer {{token}}
Accept: application/json
```

---

### 📌 Obtener una orden por ID (XML)

**Método:** `GET`  
**URL:** `{{baseUrl}}/api/rest/orders/1`

**Headers:**
```
Authorization: Bearer {{token}}
Accept: application/xml
```

---

### 📌 Crear una orden (JSON)

**Método:** `POST`  
**URL:** `{{baseUrl}}/api/rest/orders`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{token}}
```

**Body (raw - JSON):**
```json
{
  "id": 1,
  "orderDate": "2024-01-15",
  "totalPrice": 59.98,
  "status": "PENDING",
  "paymentMethod": "CREDIT_CARD",
  "clientId": 1,
  "orderDetails": [
    {
      "id": 1,
      "ref": "BOOK001",
      "price": 29.99,
      "discount": 0.0,
      "amount": 2
    }
  ]
}
```

---

### 📌 Crear una orden (XML)

**Método:** `POST`  
**URL:** `{{baseUrl}}/api/rest/orders`

**Headers:**
```
Content-Type: application/xml
Authorization: Bearer {{token}}
```

**Body (raw - XML):**
```xml
<order>
    <id>1</id>
    <orderDate>2024-01-15</orderDate>
    <totalPrice>59.98</totalPrice>
    <status>PENDING</status>
    <paymentMethod>CREDIT_CARD</paymentMethod>
    <clientId>1</clientId>
    <orderDetails>
        <orderDetail>
            <id>1</id>
            <ref>BOOK001</ref>
            <price>29.99</price>
            <discount>0.0</discount>
            <amount>2</amount>
        </orderDetail>
    </orderDetails>
</order>
```

---

### 📌 Actualizar una orden (JSON)

**Método:** `PUT`  
**URL:** `{{baseUrl}}/api/rest/orders/1`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{token}}
```

---

### 📌 Actualizar una orden (XML)

**Método:** `PUT`  
**URL:** `{{baseUrl}}/api/rest/orders/1`

**Headers:**
```
Content-Type: application/xml
Authorization: Bearer {{token}}
```

---

### 📌 Eliminar una orden

**Método:** `DELETE`  
**URL:** `{{baseUrl}}/api/rest/orders/1`

**Headers:**
```
Authorization: Bearer {{token}}
```

---

## 🎯 EJEMPLOS DE FLUJO COMPLETO

### Flujo 1: Crear un libro completo

1. **Login** → Obtener token
2. **POST /api/books** → Crear libro
3. **GET /api/books** → Verificar que se creó
4. **PUT /api/books/1** → Actualizar precio
5. **DELETE /api/books/1** → Eliminar libro

### Flujo 2: Crear una orden

1. **Login** → Obtener token
2. **POST /api/clients** → Crear cliente
3. **POST /api/books** → Crear libro
4. **POST /api/rest/orders** → Crear orden con el cliente y libro
5. **GET /api/rest/orders/1** → Ver la orden creada

---

## 💡 TIPS Y BUENAS PRÁCTICAS

### ✅ Organización
- Crea carpetas dentro de tu colección para agrupar endpoints:
  - 🔐 `Auth` (Login, Register)
  - 📚 `Books` (GET, POST, PUT, DELETE)
  - 👥 `Clients`
  - 📦 `Orders`

### ✅ Reutilización
- Siempre usa `{{baseUrl}}` y `{{token}}` en lugar de escribir las URLs completas
- Así si cambia el puerto o el token, solo lo cambias en un lugar

### ✅ Documentación
- Agrega descripciones a cada request
- Documenta qué hace cada endpoint

### ✅ Testing Automático
- Agrega tests en la pestaña **"Tests"** para validar respuestas:

```javascript
// Verificar que el status sea 200
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

// Verificar que la respuesta tenga un token
pm.test("Response has token", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.token).to.exist;
});
```

### ✅ Variables
- Usa variables para datos que cambian frecuentemente
- Ejemplo: IDs de recursos creados durante las pruebas

---

## 🔧 SOLUCIÓN DE PROBLEMAS

### Error 401 - Unauthorized
- Verifica que el token esté en el header `Authorization: Bearer {{token}}`
- Verifica que el token no haya expirado (haz login de nuevo)

### Error 403 - Forbidden
- El usuario no tiene permisos para ese recurso
- Intenta con un usuario con rol ADMIN

### Error 404 - Not Found
- Verifica que el recurso exista
- Verifica la URL (puede faltar un `/` o estar mal escrita)

### Error 500 - Internal Server Error
- Revisa los logs de Spring Boot
- Verifica que los datos enviados sean correctos

### Error de conexión
- Verifica que Spring Boot esté corriendo (`mvnw spring-boot:run`)
- Verifica que esté en el puerto correcto (8082)
- Verifica que no haya otro servicio usando ese puerto

---

## 📝 EJEMPLOS DE DATOS DE PRUEBA

### Usuarios disponibles (pre-cargados):
```
admin   / 1234 (ADMIN)
manager / 1234 (MANAGER)
user1   / 1234 (USER)
user2   / 1234 (USER)
```

### Ejemplo de libro completo:
```json
{
  "id": 1,
  "title": "Spring Boot en Acción",
  "price": 45.99,
  "author": "Craig Walls",
  "isbn": "978-1-61729-254-5",
  "releaseDate": "2023-03-15",
  "publisher": "Manning Publications",
  "weight": 1.2,
  "height": 24.0,
  "width": 18.5,
  "depth": 2.5
}
```

### Ejemplo de cliente completo:
```json
{
  "id": 1,
  "name": "María",
  "surname": "López Martínez",
  "email": "maria.lopez@email.com",
  "phone": "+34600987654",
  "address": "Avenida de la Constitución 45",
  "city": "Barcelona",
  "country": "España",
  "zipcode": "08001"
}
```

---

## 🎓 RECURSOS ADICIONALES

- **Documentación de Postman:** https://learning.postman.com/
- **Variables de entorno:** https://learning.postman.com/docs/sending-requests/variables/
- **Tests automatizados:** https://learning.postman.com/docs/writing-scripts/test-scripts/

---

¡Listo! Ahora puedes empezar a probar tu API con Postman. 🚀
