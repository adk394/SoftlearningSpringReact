# 📚 CHULETA EXAMEN - Softlearning Spring Boot

> **Guía completa para crear entidades y configurar seguridad JWT**  
> Fecha: Mañana del examen  
> Proyecto: Spring Boot + React + JWT

---

## 📋 ÍNDICE

1. [Estructura del Proyecto](#1-estructura-del-proyecto)
2. [Orden de Creación de Entidad](#2-orden-de-creación-de-entidad)
3. [Tipos de Seguridad JWT](#3-tipos-de-seguridad-jwt)
4. [Tipos de Campos y Anotaciones](#4-tipos-de-campos-y-anotaciones)
5. [Qué Hacer Si Me Piden...](#5-qué-hacer-si-me-piden)
6. [Ejemplos JSON para Postman](#6-ejemplos-json-para-postman)
7. [Códigos HTTP y Errores](#7-códigos-http-y-errores)
8. [Errores Comunes y Soluciones](#8-errores-comunes-y-soluciones)
9. [Checklist Pre-Examen](#9-checklist-pre-examen)
10. [Preguntas Frecuentes](#10-preguntas-frecuentes)

---

## 1. ESTRUCTURA DEL PROYECTO

```
SoftlearningSpringReact/
├── spring_java_app/
│   └── src/main/java/com/example/
│       ├── SoftlearningApplication.java          (Punto de entrada)
│       ├── config/
│       │   └── CorsConfig.java                   (Configuración CORS)
│       ├── security/                              🔐 TODO LO DE SEGURIDAD
│       │   ├── config/
│       │   │   ├── SecurityConfig.java           (Configura quién puede acceder a qué)
│       │   │   ├── DataInitializer.java          (Crea usuarios iniciales)
│       │   │   └── RoleEnum.java                 (ADMIN, MANAGER, USER)
│       │   ├── controllers/
│       │   │   └── AuthController.java           (Login/Register)
│       │   ├── dtos/                             (LoginRequest, TokenResponse)
│       │   ├── entities/                         (UserEntity, RoleEntity, TokenEntity)
│       │   ├── jwt/
│       │   │   ├── JwtUtils.java                 (Crea/valida tokens)
│       │   │   └── JwtAuthenticationFilter.java  (Intercepta peticiones)
│       │   ├── repositories/
│       │   └── services/
│       ├── core/entities/                         📦 ENTIDADES DEL NEGOCIO
│       │   ├── book/                             (Ejemplo existente)
│       │   ├── electronics/                      (TU ENTIDAD CON JWT)
│       │   │   ├── model/Electronics.java        (Modelo de dominio)
│       │   │   ├── dtos/ElectronicsDTO.java      (Entidad JPA)
│       │   │   └── persistence/
│       │   │       └── ElectronicsRepository.java
│       │   └── category/                         (TU ENTIDAD SIN JWT)
│       │       ├── dtos/CategoryDTO.java
│       │       └── persistence/CategoryRepository.java
│       └── presentation/api/rest/                 🌐 CONTROLADORES REST
│           ├── BookController.java
│           ├── ElectronicsController.java        (CON JWT)
│           ├── CategoryController.java           (SIN JWT)
│           └── auth/AuthController.java
└── docs/
    ├── CHULETA_EXAMEN.md                         (ESTE ARCHIVO)
    ├── GUÍA_PRUEBAS_PERMISOS.md
    └── electronics_schema.sql
```

### Convenciones de Nombres

| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| **Paquetes** | Minúsculas, separado por puntos | `com.example.core.entities.electronics` |
| **Clases** | PascalCase | `ElectronicsDTO`, `ElectronicsController` |
| **Métodos** | camelCase | `getAllElectronics()`, `findByBrand()` |
| **Variables** | camelCase | `electronicsRepository`, `idProduct` |
| **Constantes** | MAYÚSCULAS_CON_GUIONES | `FORMATO_DE_FECHA` |
| **Tablas SQL** | Minúsculas, plural | `electronics`, `categories` |

---

## 2. ORDEN DE CREACIÓN DE ENTIDAD

### Paso 1: Crear Estructura de Directorios

```bash
mkdir -p spring_java_app/src/main/java/com/example/core/entities/[tu_entidad]/dtos
mkdir -p spring_java_app/src/main/java/com/example/core/entities/[tu_entidad]/persistence
```

**Importante:** Los nombres deben ser en plural para la carpeta (`electronics`, no `electronic`).

### Paso 2: Crear el DTO (Entidad JPA)

**Ubicación:** `core/entities/[nombre]/dtos/[Nombre]DTO.java`

**Elementos obligatorios:**
1. `@Entity` - Marca como entidad JPA
2. `@Table(name = "nombre_tabla")` - Nombre en la BD
3. `@Id` + `@GeneratedValue` - Clave primaria autoincremental
4. `@Column` en cada campo
5. Constructor vacío (obligatorio para JPA)
6. Getters y setters (obligatorios para serialización JSON)

**Campos típicos:**
- `int id` - ID autogenerado
- `String` - Textos (nombre, descripción, código)
- `int` - Cantidades, stocks
- `double` - Precios (mayor precisión)
- `float` - Pesos, medidas (menor precisión suficiente)
- `boolean` - Disponible, activo
- `LocalDate` - Fechas sin hora
- `LocalDateTime` - Fechas con hora
- `String` con separadores - Arrays (ej: "item1|item2|item3")

### Paso 3: Crear el Repository

**Ubicación:** `core/entities/[nombre]/persistence/[Nombre]Repository.java`

**Elementos obligatorios:**
1. `@Repository` - Marca como componente Spring
2. `extends JpaRepository<[Nombre]DTO, Integer>` - Hereda CRUD
3. Métodos personalizados opcionales:
   - `Optional<[Nombre]DTO> findById(int id)`
   - `List<[Nombre]DTO> findByNameContaining(String name)`
   - `List<[Nombre]DTO> findByBrand(String brand)`
   - `boolean existsByIdProduct(String idProduct)`

### Paso 4: Crear el Controller

**Ubicación:** `presentation/api/rest/[Nombre]Controller.java`

**Elementos obligatorios:**
1. `@RestController` - Marca como API REST
2. `@RequestMapping("/api/[nombre]")` - URL base
3. `@Autowired` del Repository
4. Métodos CRUD:
   - `@GetMapping` - Obtener todos / por ID
   - `@PostMapping` - Crear nuevo
   - `@PutMapping("/{id}")` - Actualizar
   - `@DeleteMapping("/{id}")` - Eliminar
5. `ResponseEntity<?>` para controlar respuestas HTTP

### Paso 5: Configurar Seguridad

**Archivo:** `security/config/SecurityConfig.java`

**Buscar el método** `securityFilterChain()` y agregar dentro de `.authorizeHttpRequests(http -> { ... })`:

```java
// Opción A: CON JWT (requiere token)
http.requestMatchers("/api/tu_entidad/**").authenticated();

// Opción B: SIN JWT (público)
http.requestMatchers("/api/tu_entidad/**").permitAll();

// Opción C: CON JWT + Rol específico
http.requestMatchers("/api/tu_entidad/**").hasRole("ADMIN");
```

**Ubicación:** Antes de `http.anyRequest().authenticated()`

### Paso 6: Probar en Postman

**Secuencia de pruebas:**

1. **Si es CON JWT:**
   - POST `/api/auth/login` → Obtener token
   - Usar token en Header: `Authorization: Bearer [token]`
   - Probar GET, POST, PUT, DELETE

2. **Si es SIN JWT:**
   - Directo a los endpoints
   - Probar GET, POST, PUT, DELETE sin header

---

## 3. TIPOS DE SEGURIDAD JWT

### Comparativa Visual

```
┌─────────────────────────────────────────────────────────────┐
│  PETICIÓN SIN JWT (Público)                                 │
├─────────────────────────────────────────────────────────────┤
│  Cliente ──GET /api/category────▶ Controller                │
│                                      ↓                      │
│                                   Repository                │
│                                      ↓                      │
│                                   Base de datos             │
│                                      ↓                      │
│  Cliente ◀──Respuesta JSON─────── Controller                │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  PETICIÓN CON JWT (Protegido)                               │
├─────────────────────────────────────────────────────────────┤
│  Cliente ──POST /api/auth/login──▶ AuthController           │
│                                      ↓                      │
│  Cliente ◀──Token JWT──────────── AuthController            │
│                                                              │
│  Cliente ──GET /api/electronics──▶ JwtAuthenticationFilter │
│            Header: Bearer xxx        ↓                      │
│            Valida token OK        Controller                │
│                                      ↓                      │
│                                   Repository                │
│                                      ↓                      │
│                                   Base de datos             │
│                                      ↓                      │
│  Cliente ◀──Respuesta JSON─────── Controller                │
└─────────────────────────────────────────────────────────────┘
```

### Tabla de Configuración

| Tipo | Configuración | ¿Requiere Token? | Usuarios permitidos |
|------|--------------|------------------|---------------------|
| **SIN JWT** | `.permitAll()` | ❌ No | Todos (sin autenticar) |
| **CON JWT (sin roles)** | `.authenticated()` | ✅ Sí | Cualquiera logueado |
| **CON JWT (con roles)** | `.hasRole("ADMIN")` | ✅ Sí | Solo usuarios con ese rol |
| **CON JWT (varios roles)** | `.hasAnyRole("USER","ADMIN")` | ✅ Sí | Usuarios con alguno de esos roles |

### Configuración por Método HTTP

```java
// Diferentes permisos por operación
http.requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("USER", "MANAGER", "ADMIN");
http.requestMatchers(HttpMethod.POST, "/api/books/**").hasAnyRole("MANAGER", "ADMIN");
http.requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("MANAGER", "ADMIN");
http.requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN");
```

---

## 4. TIPOS DE CAMPOS Y ANOTACIONES

### Mapeo Java ↔ SQL

| Tipo Java | Tipo SQL | Anotaciones JPA | Uso típico |
|-----------|----------|-----------------|------------|
| `int` | `INTEGER` | `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)` | IDs, cantidades |
| `String` | `VARCHAR(n)` | `@Column(name="nombre", length=100)` | Nombres, códigos, descripciones |
| `String` | `VARCHAR(50) UNIQUE` | `@Column(unique=true, nullable=false)` | Códigos únicos de producto |
| `double` | `DOUBLE` | `@Column(nullable=false)` | Precios, montos |
| `float` | `FLOAT` | `@Column` | Pesos, medidas, consumos |
| `boolean` | `TINYINT(1)` | `@Column(name="is_active")` | Disponible, activo, habilitado |
| `LocalDate` | `DATE` | `@Column(name="fecha")` | Fechas sin hora (cumpleaños, vencimiento) |
| `LocalDateTime` | `DATETIME` | `@Column(name="created_at")` | Fechas con hora (creación, actualización) |

### Campos Especiales

#### Array/Lista (simulado con String)
```java
// Almacena: "item1|item2|item3"
@Column(name = "features", length = 1000)
private String features;

// Métodos helper
public String[] getFeaturesAsArray() {
    return features != null ? features.split("\\|") : new String[0];
}

public void setFeaturesFromArray(String[] array) {
    this.features = array != null ? String.join("|", array) : null;
}
```

#### Fecha con Formatter
```java
@Column(name = "manufactured_date")
private LocalDateTime manufacturedDate;

public static final DateTimeFormatter FORMATTER = 
    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

public String getFormattedDate() {
    return manufacturedDate != null ? manufacturedDate.format(FORMATTER) : null;
}
```

---

## 5. QUÉ HACER SI ME PIDEN...

### Si piden HERENCIA

**Situación:** Crear clase que extienda de `Product` o similar

**Pasos:**
1. En el modelo (no en el DTO), extender la clase:
   ```java
   public class Electronics extends Product implements Marketable
   ```
2. Heredar campos de la clase padre (idProduct, name, description, price, stock, isAvailable)
3. Agregar campos propios (brand, warranty, etc.)
4. Crear método `getInstance()` estático:
   ```java
   public static Electronics getInstance(...) throws BuildException
   ```
5. Validar datos antes de crear la instancia
6. Llamar a `super()` en el constructor

**Importante:** La herencia va en el **modelo de dominio** (`model/`), no en el DTO. El DTO es plano (sin herencia).

### Si piden VALIDACIONES

**Opción A: Usar clase `Check` del proyecto**
```java
// La clase Check tiene métodos estáticos:
Check.minStringChars(texto, minimoCaracteres)  // true si cumple
Check.isValidNumber(numero, minimoValor)       // true si es válido
Check.ISBN(codigo)                             // true si ISBN válido
```

**Opción B: Validar manualmente**
```java
private String validateData(String campo) {
    String error = "";
    if (campo == null || campo.length() < 3) {
        error += "Campo inválido. ";
    }
    return error;
}
```

**Opción C: Lanzar BuildException**
```java
String error = validateData(...);
if (!error.isEmpty()) {
    throw new BuildException("Error: " + error);
}
```

### Si piden FECHAS con FORMATO PERSONALIZADO

**Formato español (dd/MM/yyyy):**
```java
public static final DateTimeFormatter FORMATO = 
    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

// Parsear String → LocalDateTime
LocalDateTime fecha = LocalDateTime.parse("25/12/2024 14:30:00", FORMATO);

// Formatear LocalDateTime → String
String texto = fecha.format(FORMATO);  // "25/12/2024 14:30:00"
```

**Otros formatos útiles:**
- `"yyyy-MM-dd"` - ISO estándar (2024-12-25)
- `"dd/MM/yyyy"` - Solo fecha española (25/12/2024)
- `"HH:mm:ss"` - Solo hora (14:30:00)

### Si piden ARRAYS

**No usar `String[]` directamente en JPA** (no se persiste bien).

**Solución:** Almacenar como String con separadores:
```java
// En el DTO
@Column(length = 1000)
private String caracteristicas;  // Almacena: "wifi|bluetooth|nfc"

// Métodos helper
public String[] getCaracteristicasArray() {
    return caracteristicas != null ? caracteristicas.split("\\|") : new String[0];
}

public void setCaracteristicasArray(String[] array) {
    this.caracteristicas = array != null ? String.join("|", array) : null;
}
```

### Si piden RELACIONES (@OneToMany, @ManyToOne)

**Si te lo piden en el examen (avanzado):**

```java
// Muchos Electronics pertenecen a una Category
@ManyToOne
@JoinColumn(name = "category_id")
private CategoryDTO category;

// Una Category tiene muchos Electronics
@OneToMany(mappedBy = "category")
private List<ElectronicsDTO> products;
```

**Si NO te lo piden:** No lo pongas. Mantén las entidades simples.

---

## 6. EJEMPLOS JSON PARA POSTMAN

### Login (Obtener Token)

```http
POST http://localhost:8082/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "1234"
}
```

**Respuesta:**
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiJ9...",
  "refresh_token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Entidad CON JWT (Requiere Header)

```http
POST http://localhost:8082/api/electronics
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "idProduct": "ELEC-001",
  "name": "Smart TV Samsung 55\"",
  "description": "Televisor 4K UHD",
  "price": 899.99,
  "stock": 25,
  "isAvailable": true,
  "brand": "Samsung",
  "warrantyMonths": 24,
  "weightKg": 18.5,
  "powerConsumptionWatts": 120.0,
  "manufacturedDateTime": "2024-12-25T14:30:00",
  "features": "4K|Smart TV|WiFi 6"
}
```

### Entidad SIN JWT (Sin Header)

```http
POST http://localhost:8082/api/category
Content-Type: application/json

{
  "name": "Televisores",
  "description": "Smart TVs y monitores",
  "active": true
}
```

### Peticiones Básicas

**Obtener todos (GET):**
```http
GET http://localhost:8082/api/electronics
Authorization: Bearer [token]  <-- Solo si es CON JWT
```

**Obtener uno (GET con ID):**
```http
GET http://localhost:8082/api/electronics/1
Authorization: Bearer [token]  <-- Solo si es CON JWT
```

**Actualizar (PUT):**
```http
PUT http://localhost:8082/api/electronics/1
Authorization: Bearer [token]  <-- Solo si es CON JWT
Content-Type: application/json

{
  "name": "Nombre actualizado",
  "price": 999.99
}
```

**Eliminar (DELETE):**
```http
DELETE http://localhost:8082/api/electronics/1
Authorization: Bearer [token]  <-- Solo si es CON JWT
```

---

## 7. CÓDIGOS HTTP Y ERRORES

### Códigos de Éxito (2xx)

| Código | Nombre | Significado | Cuándo usar |
|--------|--------|-------------|-------------|
| **200** | OK | Petición exitosa | GET, PUT, DELETE exitosos |
| **201** | Created | Recurso creado | POST exitoso (recomendado) |
| **204** | No Content | Sin contenido | DELETE exitoso (alternativa) |

### Códigos de Error del Cliente (4xx)

| Código | Nombre | Significado | Solución |
|--------|--------|-------------|----------|
| **400** | Bad Request | Petición mal formada | Revisar JSON enviado |
| **401** | Unauthorized | No autorizado | Falta token JWT o es inválido |
| **403** | Forbidden | Prohibido | Token válido pero sin permisos (rol) |
| **404** | Not Found | No encontrado | URL incorrecta o ID no existe |
| **405** | Method Not Allowed | Método no permitido | Usar GET en lugar de POST, etc. |
| **409** | Conflict | Conflicto | ID duplicado, recurso ya existe |
| **422** | Unprocessable Entity | Datos inválidos | Validación falló (campos incorrectos) |

### Códigos de Error del Servidor (5xx)

| Código | Nombre | Significado | Solución |
|--------|--------|-------------|----------|
| **500** | Internal Server Error | Error interno | Revisar logs de Java |
| **503** | Service Unavailable | Servicio no disponible | Base de datos caída, etc. |

---

## 8. ERRORES COMUNES Y SOLUCIONES

### Error: "Could not create bean" o "No default constructor"

**Síntoma:** La aplicación no inicia

**Causa:** Falta constructor vacío en el DTO

**Solución:**
```java
public ElectronicsDTO() {}  // Agregar esto
```

---

### Error: "Table doesn't exist"

**Síntoma:** Error 500 al hacer peticiones

**Causa:** La tabla no existe en MySQL

**Soluciones:**
1. **Opción A (Automática):**
   ```properties
   # En application.properties
   spring.jpa.hibernate.ddl-auto=update
   ```

2. **Opción B (Manual):**
   Ejecutar script SQL en MySQL:
   ```sql
   CREATE TABLE electronics (...);
   ```

---

### Error: "Invalid token" o "401 Unauthorized"

**Síntoma:** No puedo acceder a endpoints protegidos

**Causas y soluciones:**

| Causa | Solución |
|-------|----------|
| No envío token | Agregar header: `Authorization: Bearer eyJhbG...` |
| Token expirado | Hacer login de nuevo para obtener token nuevo |
| Token mal copiado | Copiar todo el token sin espacios extras |
| Endpoint es público pero configuro CON JWT | Verificar SecurityConfig tenga `.permitAll()` |

**Para verificar si el token es válido:**
1. Ir a https://jwt.io
2. Pegar el token
3. Verificar que se decodifica correctamente

---

### Error: "Access is denied" o "403 Forbidden"

**Síntoma:** Tengo token válido pero no puedo acceder

**Causa:** El usuario no tiene el rol requerido

**Solución:**
1. Verificar roles del usuario en la BD
2. O cambiar SecurityConfig para permitir el rol del usuario
3. O usar `.authenticated()` en lugar de `.hasRole("ADMIN")`

---

### Error: "JSON parse error"

**Síntoma:** Error 400 al enviar POST/PUT

**Causa:** JSON mal formado

**Soluciones:**
1. Verificar comillas dobles: `"campo": "valor"`
2. No dejar coma al final del último campo
3. Números sin comillas: `"price": 99.99` (no `"price": "99.99"`)
4. Booleanos sin comillas: `"activo": true` (no `"activo": "true"`)

---

### Error: "DataIntegrityViolationException"

**Síntoma:** Error 500 al guardar

**Causa:** Violación de restricciones de BD (campo nulo, duplicado, etc.)

**Soluciones:**
1. Verificar que campos `@Column(nullable=false)` tengan valor
2. Verificar que campos `@Column(unique=true)` no se repitan
3. Verificar tipos de datos (no enviar String donde espera int)

---

### Error: "No serializer found"

**Síntoma:** Error 500, no puede convertir a JSON

**Causa:** Falta getter en el DTO o campo incompatible

**Solución:**
1. Verificar que todos los campos tengan getter
2. No usar tipos no serializables (evitar arrays primitivos `[]`, usar List<>)

---

### Error: "Port 8082 already in use"

**Síntoma:** La aplicación no inicia

**Causa:** Otra aplicación usa el puerto 8082

**Soluciones:**
1. Cambiar puerto en `application.properties`:
   ```properties
   server.port=8083
   ```
2. O matar el proceso que usa el puerto:
   ```bash
   # Windows
   netstat -ano | findstr :8082
   taskkill /PID [numero] /F
   ```

---

## 9. CHECKLIST PRE-EXAMEN

Antes de dar por terminada tu entidad, verifica:

### Estructura
- [ ] Carpeta creada: `core/entities/[nombre]/dtos/`
- [ ] Carpeta creada: `core/entities/[nombre]/persistence/`
- [ ] Archivo creado: `[Nombre]DTO.java`
- [ ] Archivo creado: `[Nombre]Repository.java`
- [ ] Archivo creado: `[Nombre]Controller.java`

### DTO (Entidad JPA)
- [ ] Tiene `@Entity`
- [ ] Tiene `@Table(name="...")`
- [ ] Tiene `@Id` con `@GeneratedValue`
- [ ] Todos los campos tienen `@Column`
- [ ] Tiene constructor vacío `public [Nombre]DTO() {}`
- [ ] Todos los campos tienen getter
- [ ] Todos los campos tienen setter

### Repository
- [ ] Tiene `@Repository`
- [ ] Extiende `JpaRepository<[Nombre]DTO, Integer>`
- [ ] Nombre correcto: `[Nombre]Repository`

### Controller
- [ ] Tiene `@RestController`
- [ ] Tiene `@RequestMapping("/api/[nombre]")`
- [ ] Tiene `@Autowired` del Repository
- [ ] Método GET (obtener todos)
- [ ] Método GET con ID (obtener uno)
- [ ] Método POST (crear)
- [ ] Método PUT (actualizar)
- [ ] Método DELETE (eliminar)
- [ ] Usa `ResponseEntity<>` para respuestas

### Seguridad
- [ ] Modificado `SecurityConfig.java`
- [ ] Agregada línea de configuración para `/api/[nombre]/**`
- [ ] Configurado como CON JWT o SIN JWT según se pida

### Testing
- [ ] Aplicación inicia sin errores
- [ ] Si es CON JWT: Login funciona y devuelve token
- [ ] GET funciona
- [ ] POST funciona
- [ ] PUT funciona
- [ ] DELETE funciona
- [ ] Verificar en MySQL que los datos se guardan

---

## 10. PREGUNTAS FRECUENTES

### ¿Dónde va el modelo (getInstance, herencia) vs el DTO?

**Modelo (model/):** Lógica de negocio, validaciones, herencia
- Ejemplo: `Electronics.java` con `extends Product` y `getInstance()`
- Se usa para crear objetos validados antes de guardar

**DTO (dtos/):** Estructura de datos para persistencia
- Ejemplo: `ElectronicsDTO.java` con `@Entity` y campos planos
- Se usa para mapear la tabla de la base de datos
- Es lo que expone el Controller en la API

**Relación:** El Controller puede usar el Modelo para validar y luego convertir a DTO para guardar.

---

### ¿Por qué no se crea la tabla automáticamente?

**Razones:**
1. `spring.jpa.hibernate.ddl-auto=none` (no actualiza)
2. `spring.jpa.hibernate.ddl-auto=validate` (solo valida, no crea)
3. Errores en el DTO que impiden crear la tabla

**Solución:**
```properties
# En application.properties
spring.jpa.hibernate.ddl-auto=update
```

Valores posibles:
- `none`: No hace nada
- `validate`: Valida que el schema coincida (no modifica)
- `update`: Actualiza schema existente (recomendado para desarrollo)
- `create`: Borra y recrea (cuidado, pierdes datos)
- `create-drop`: Crea al iniciar, borra al cerrar

---

### ¿Cómo cambio de CON JWT a SIN JWT rápidamente?

**En SecurityConfig.java:**

```java
// CON JWT (requiere login)
http.requestMatchers("/api/mi_entidad/**").authenticated();

// SIN JWT (público)
http.requestMatchers("/api/mi_entidad/**").permitAll();
```

Solo cambias esa palabra y reinicias la aplicación.

---

### ¿Por qué necesito constructor vacío en el DTO?

JPA/Hibernate usa **reflection** para crear instancias de las entidades cuando carga datos de la base de datos. Necesita un constructor sin parámetros para poder crear el objeto antes de asignarle los valores.

Si no existe, lanza error: `"No default constructor for entity"`

---

### ¿Qué es el token JWT y por qué empieza con "eyJ"?

El token JWT tiene 3 partes separadas por puntos:
```
eyJhbG... . eyJzdWI... . SflKxw...
  ↑ Header      ↑ Payload     ↑ Signature
```

Todas las partes están codificadas en Base64. La "eyJ" es el inicio de cualquier JSON codificado en Base64 (empieza con `{`).

**Estructura:**
- **Header:** Algoritmo y tipo de token
- **Payload:** Datos del usuario (username, roles, expiración)
- **Signature:** Firma para verificar que no fue modificado

---

### ¿Cómo obtengo el token en Postman?

**Paso 1:** Hacer login
```http
POST http://localhost:8082/api/auth/login
{
  "username": "admin",
  "password": "1234"
}
```

**Paso 2:** Copiar el `access_token` de la respuesta

**Paso 3:** En la siguiente petición, agregar Header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Automatizar en Postman:**
1. En la pestaña "Tests" del login, agregar:
   ```javascript
   var jsonData = pm.response.json();
   pm.environment.set("token", jsonData.access_token);
   ```
2. Crear variable de entorno `token`
3. Usar: `Authorization: Bearer {{token}}`

---

### ¿Qué hago si olvidé mi contraseña de los usuarios de prueba?

Los usuarios se crean automáticamente en `DataInitializer.java`:

```java
// Usuarios por defecto:
admin / 1234    (rol ADMIN)
manager / 1234  (rol MANAGER)
user1 / 1234    (rol USER)
user2 / 1234    (rol USER)
```

Si cambiaste algo y no funcionan, revisa la tabla `users` en MySQL o reinicia la aplicación (con `ddl-auto=create` borra todo, cuidado).

---

### ¿Puedo tener una entidad CON JWT y otra SIN JWT en el mismo proyecto?

**¡Sí!** Es exactamente lo que has hecho:

```java
// Electronics requiere token
http.requestMatchers("/api/electronics/**").authenticated();

// Category es público
http.requestMatchers("/api/category/**").permitAll();

// Books requiere roles específicos
http.requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("USER", "MANAGER", "ADMIN");
http.requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN");
```

Cada endpoint puede tener su propia configuración de seguridad.

---

## 🎯 CONSEJOS FINALES PARA EL EXAMEN

1. **Lee bien el enunciado:** ¿Piden CON o SIN JWT? ¿Piden herencia? ¿Validaciones?

2. **Crea primero el DTO:** Es la base de todo. Sin entidad JPA no hay nada.

3. **Prueba paso a paso:**
   - Primero verifica que la app inicia sin errores
   - Luego prueba GET (el más simple)
   - Después POST, PUT, DELETE

4. **Si algo no funciona:**
   - Revisa los logs de la consola de Java
   - Verifica que MySQL esté corriendo
   - Comprueba que el puerto 8082 esté libre

5. **Si te quedas atascado:**
   - Empieza por lo básico (DTO simple, sin campos complejos)
   - Agrega funcionalidades una por una
   - Comenta código que no funciona y sigue con el resto

6. **Entrega algo que funcione:**
   - Mejor un CRUD básico funcional que uno complejo roto
   - Los profesores valoran más que funcione que que tenga mil funcionalidades

---

## 📞 RECORDATORIO RÁPIDO

| Si te piden... | Usa... |
|----------------|--------|
| Entidad básica | DTO con `@Entity` |
| Herencia | Modelo con `extends` + `getInstance()` |
| Validaciones | `BuildException` + método `validate()` |
| Fechas | `LocalDateTime` + `DateTimeFormatter` |
| Arrays | `String` con separadores `\|` + helpers |
| CON JWT | SecurityConfig: `.authenticated()` |
| SIN JWT | SecurityConfig: `.permitAll()` |
| Solo ADMIN | SecurityConfig: `.hasRole("ADMIN")` |
| Varios roles | SecurityConfig: `.hasAnyRole("USER","ADMIN")` |

---

**¡Mucha suerte en el examen! 🍀🚀**

*Creado: Mayo 2024*  
*Última actualización: Para examen de mañana*
