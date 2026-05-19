🛠️ QUÉ INSTALAR DESPUÉS DEL GIT CLONE
1. BACKEND (Spring Boot)
✅ Java JDK 21
# Verificar si tienes Java
java -version
# Debe decir: openjdk version "21" o similar
# Si no, descargar de: https://adoptium.net/
✅ Maven (Opcional, pero recomendado)
Aunque el proyecto tiene Maven Wrapper (.mvn/wrapper), puedes instalar Maven globalmente:
# Windows: descargar de https://maven.apache.org/download.cgi
# O usar Chocolatey: choco install maven
# Verificar
mvn -version
Pero con el wrapper no es obligatorio, puedes usar:
# En lugar de 'mvn', usa:
./mvnw clean install        # Linux/Mac
mvnw.cmd clean install      # Windows
✅ MySQL
# Windows: Descargar MySQL Installer
# https://dev.mysql.com/downloads/installer/
# Asegúrate de tener:
# - MySQL Server (puerto 3306)
# - MySQL Workbench (opcional, para ver tablas)
⚠️ Archivos que NO vienen (excluidos en .gitignore):
Carpeta/Archivo	¿Por qué no está?	¿Qué hacer?
target/	Se genera al compilar	Ejecutar mvn clean install
.idea/	Config de IntelliJ	Se regenera al abrir proyecto
.vscode/	Config de VS Code	Se regenera al abrir proyecto
HELP.md	Ayuda de Spring	No es necesario
2. FRONTEND (React)
✅ Node.js 18+ (IMPORTANTE)
# Verificar
node -v   # Debe ser 18.x o superior
npm -v
# Si no lo tienes:
# https://nodejs.org/ (descargar LTS)
⚠️ node_modules/ NO viene en el repo
Después del git clone, DEBES ejecutar:
cd react_frontend/
npm install        # Instala todas las dependencias
Esto descargará todo lo del package.json (React, Vite, etc.)
📋 CHECKLIST POST-CLONE
Paso 1: Clonar
git clone <url-del-repo>
cd SoftlearningSpringReact
Paso 2: Backend
cd spring_java_app/
# Opción A: Con Maven instalado
mvn clean install
# Opción B: Con Maven Wrapper (incluido)
./mvnw clean install        # Linux/Mac
mvnw.cmd clean install      # Windows
Paso 3: Base de Datos
# Asegurar que MySQL esté corriendo en puerto 3306
# Crear base de datos si no existe: softlearning
Paso 4: Frontend (si vas a usarlo)
cd react_frontend/
npm install
npm run dev
🚀 COMANDOS RÁPIDOS PARA MAÑANA
# 1. Clonar
git clone <url>
cd SoftlearningSpringReact
# 2. Backend (en una terminal)
cd spring_java_app/
./mvnw spring-boot:run    # o mvnw.cmd en Windows
# 3. Frontend (en otra terminal, opcional)
cd react_frontend/
npm install               # Solo la primera vez
npm run dev
# 4. Probar en Postman
# http://localhost:8082
❓ ¿QUÉ PUEDE FALLAR?
Problema	Solución
"Java not found"	Instalar JDK 21
"Port 3306 in use"	Cambiar puerto de MySQL o matar proceso
"npm not found"	Instalar Node.js
"node_modules not found"	Ejecutar npm install en react_frontend/
"target not found"	Ejecutar mvn clean install
💡 RESUMEN
Software	¿Obligatorio?	¿Se descarga solo?
Java JDK 21	✅ Sí	❌ No, instalar manual
Maven	❌ No (usa wrapper)	✅ Wrapper incluido
MySQL	✅ Sí	❌ No, instalar manual
Node.js	✅ Sí (para frontend)	❌ No, instalar manual
Dependencias Maven	✅ Sí	✅ mvn clean install
Dependencias React	✅ Sí	✅ npm install