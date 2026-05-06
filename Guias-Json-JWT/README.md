# 📮 Cómo usar las colecciones de Postman

## 📦 Archivos incluidos

1. **`Softlearning_Permissions_Tests.json`** - Colección completa con pruebas de permisos
2. **`Softlearning_Environment.json`** - Variables de entorno
3. **`GUÍA_PRUEBAS_PERMISOS.md`** - Guía detallada completa

## 🚀 Instrucciones de importación

### Paso 1: Importar la colección

1. Abre Postman
2. Haz clic en **"Import"** (botón arriba a la izquierda)
3. Selecciona el archivo: `Softlearning_Permissions_Tests.json`
4. Haz clic en **"Import"**

### Paso 2: Importar el entorno

1. En Postman, haz clic en el ícono de **engranaje** (⚙️) arriba a la derecha
2. Selecciona **"Import"**
3. Selecciona el archivo: `Softlearning_Environment.json`
4. Haz clic en **"Import"**

### Paso 3: Seleccionar el entorno

1. En el dropdown arriba a la derecha (donde dice "No Environment")
2. Selecciona: **"Softlearning - Entorno de Pruebas"**

## 🎯 Cómo ejecutar las pruebas

### Opción 1: Ejecutar individualmente

1. Asegúrate de que Spring Boot esté corriendo (`mvnw spring-boot:run`)
2. En Postman, expande la colección
3. Ve a la carpeta **"01 - AUTH"**
4. Haz clic en **"Login - USER"**
5. Haz clic en el botón **"Send"**
6. Si el login es exitoso, el token se guardará automáticamente
7. Ahora puedes probar los endpoints de la carpeta **"02 - USER Tests"**

### Opción 2: Ejecutar toda una carpeta

1. Haz clic derecho en la carpeta que quieres ejecutar (ej: "02 - USER Tests")
2. Selecciona **"Run Folder"**
3. Se abrirá el Collection Runner
4. Asegúrate de que el entorno esté seleccionado
5. Haz clic en **"Run"**
6. Verás los resultados de todas las pruebas

### Opción 3: Ejecutar toda la colección

1. Haz clic en los **tres puntos** (...) junto a la colección
2. Selecciona **"Run Collection"**
3. Configura las opciones:
   - **Environment**: Selecciona el entorno importado
   - **Iterations**: 1
   - **Delay**: 100ms (recomendado)
4. Haz clic en **"Run"**
5. Verás un reporte completo con todas las pruebas

## 📋 Estructura de la colección

```
📁 Softlearning API - Pruebas de Permisos
├── 📁 01 - AUTH
│   ├── 🔐 Login - ADMIN
│   ├── 🔐 Login - MANAGER
│   └── 🔐 Login - USER
├── 📁 02 - USER Tests (Solo Lectura)
│   ├── ✅ GET Books (Debe funcionar)
│   ├── ❌ POST Books (Debe fallar 403)
│   ├── ❌ PUT Books (Debe fallar 403)
│   ├── ❌ DELETE Books (Debe fallar 403)
│   ├── ✅ GET Clients
│   └── ✅ GET Orders
├── 📁 03 - MANAGER Tests (CRU sin Delete)
│   ├── ✅ GET Books
│   ├── ✅ POST Books
│   ├── ✅ PUT Books
│   ├── ❌ DELETE Books (Debe fallar 403)
│   └── [... más tests]
└── 📁 04 - ADMIN Tests (CRUD Completo)
    ├── ✅ GET Books
    ├── ✅ POST Books
    ├── ✅ PUT Books
    ├── ✅ DELETE Books
    └── 🧹 Cleanup
```

## ✅ Qué verificar en cada test

Cada request tiene tests automáticos que verifican:

- ✅ **Status code correcto** (200 para éxito, 403 para prohibido)
- ✅ **Token guardado** (después del login)
- ✅ **Datos correctos** (verifica estructura de la respuesta)

### Ver resultados

Después de ejecutar, verás:
- 🟢 **PASSED** - El test pasó correctamente
- 🔴 **FAILED** - El test falló (revisa el error)

## 🔧 Solución de problemas

### Error "Could not get any response"
- Verifica que Spring Boot esté corriendo
- Verifica que esté en el puerto 8082
- Verifica que no haya otro servicio usando ese puerto

### Error 401 - Unauthorized
- Asegúrate de haber hecho login primero
- El token se guarda automáticamente después del login
- Si el token expiró, haz login de nuevo

### Error 403 - Forbidden (cuando no debería)
- Verifica que estés usando el usuario correcto
- Verifica en los logs de Spring Boot
- Asegúrate de que el usuario tenga el rol correcto

### Tests fallando
- Revisa el mensaje de error en la pestaña "Test Results"
- Verifica que los datos de prueba existan (IDs de libros, etc.)
- Revisa la consola de Postman (View → Show Postman Console)

## 📝 Flujo recomendado de pruebas

1. **Ejecutar "01 - AUTH"** completo
   - Verifica que los 3 logins funcionen
   - Verifica que cada usuario tenga los roles correctos

2. **Ejecutar "02 - USER Tests"**
   - Primero haz login como USER
   - Luego ejecuta todos los tests de esta carpeta
   - Verifica que solo los GETs funcionen (200)
   - Verifica que POST, PUT, DELETE den 403

3. **Ejecutar "03 - MANAGER Tests"**
   - Haz login como MANAGER
   - Ejecuta todos los tests
   - Verifica que GET, POST, PUT funcionen (200)
   - Verifica que DELETE dé 403

4. **Ejecutar "04 - ADMIN Tests"**
   - Haz login como ADMIN
   - Ejecuta todos los tests
   - Verifica que TODO funcione (200)
   - El cleanup eliminará los datos de prueba

## 🎨 Personalización

### Cambiar el puerto
Si tu aplicación corre en otro puerto:
1. Ve al entorno (icono de engranaje)
2. Edita la variable `baseUrl`
3. Cambia a: `http://localhost:TU_PUERTO`

### Agregar más tests
1. Duplica un request existente (clic derecho → Duplicate)
2. Modifica la URL, método o body
3. Agrega tests en la pestaña "Tests"

### Cambiar datos de prueba
1. Edita el body de los requests POST/PUT
2. Modifica los IDs en las URLs de PUT/DELETE
3. Asegúrate de que los IDs existan en la base de datos

## 📊 Interpretar resultados

### Collection Runner
Después de ejecutar verás:
- **Total requests**: Número total de requests ejecutados
- **Passed**: Cuántos tests pasaron
- **Failed**: Cuántos tests fallaron
- **Time**: Tiempo total de ejecución

### Códigos de estado esperados

| Código | Significado | ¿Es correcto? |
|--------|-------------|---------------|
| **200** | OK | ✅ Sí, operación permitida |
| **201** | Created | ✅ Sí, recurso creado |
| **401** | Unauthorized | ⚠️ Token inválido o expirado |
| **403** | Forbidden | ✅ Correcto si es usuario sin permisos |
| **404** | Not Found | ⚠️ El recurso no existe |
| **500** | Server Error | ❌ Error en el servidor |

### Ejemplo de resultados exitosos

**USER Tests:**
```
✅ GET Books - Status 200
✅ POST Books - Status 403 (Forbidden)
✅ PUT Books - Status 403 (Forbidden)
✅ DELETE Books - Status 403 (Forbidden)
```

**MANAGER Tests:**
```
✅ GET Books - Status 200
✅ POST Books - Status 200
✅ PUT Books - Status 200
✅ DELETE Books - Status 403 (Forbidden)
```

**ADMIN Tests:**
```
✅ GET Books - Status 200
✅ POST Books - Status 200
✅ PUT Books - Status 200
✅ DELETE Books - Status 200
```

## 🎓 Próximos pasos

1. **Exportar resultados**: En el Collection Runner, haz clic en "Export Results"
2. **Documentar**: Guarda screenshots de los resultados
3. **Automatizar**: Usa Newman (CLI de Postman) para ejecutar en CI/CD
4. **Expandir**: Agrega más endpoints y casos de prueba

## 📞 Ayuda

Si tienes problemas:
1. Revisa la guía completa: `GUÍA_PRUEBAS_PERMISOS.md`
2. Verifica que Spring Boot esté corriendo
3. Revisa los logs de Spring Boot
4. Verifica que los usuarios estén creados en la base de datos

---

¡Listo para probar! 🚀
