# Documentacio de Disseny - Seguretat Softlearning

## 1. Objectiu del Sistema de Seguretat

Implementar un sistema d'autenticacio JWT per controlar l'acces dels usuaris als serveis REST de l'aplicacio Softlearning. Els usuaris s'han d'autenticar amb username i password, i rebran un token JWT que han d'enviar a cada peticio per accedir als recursos protegits.

## 2. Tipus d'Usuaris (Rols)

Despres d'analitzar les necessitats del portal, s'han definit tres tipus d'usuaris:

- **ADMIN**: Administrador amb acces total (crear, llegir, actualitzar, eliminar)
- **MANAGER**: Gestor de continguts que pot crear, llegir i actualitzar, pero NO eliminar
- **USER**: Usuari basic amb acces nomes de lectura

## 3. Matriu de Permisos

Aquesta taula mostra quins rols poden accedir a cada servei segons el metode HTTP:

| Endpoint          | GET                  | POST                    | PUT            | DELETE |
| ----------------- | -------------------- | ----------------------- | -------------- | ------ |
| /api/books/\*\*   | USER, MANAGER, ADMIN | MANAGER, ADMIN          | MANAGER, ADMIN | ADMIN  |
| /api/clients/\*\* | USER, MANAGER, ADMIN | MANAGER, ADMIN          | MANAGER, ADMIN | ADMIN  |
| /api/orders/\*\*  | USER, MANAGER, ADMIN | MANAGER, ADMIN          | MANAGER, ADMIN | ADMIN  |
| /api/auth/\*\*    | Tothom (login)       | Tothom (login/register) | -              | -      |

## 4. Usuaris de Prova

Per provar el sistema de seguretat, es crearan els seguents usuaris inicials:

| Usuari  | Contrasenya | Rol     | Proves a realitzar                                    |
| ------- | ----------- | ------- | ----------------------------------------------------- |
| admin   | 1234        | ADMIN   | Verificar que pot fer CRUD complet                    |
| manager | 1234        | MANAGER | Verificar que no pot eliminar (ha de donar error 403) |
| user1   | 1234        | USER    | Verificar que nomes pot llegir (GET)                  |
| user2   | 1234        | USER    | Verificar acces nomes lectura                         |

## 5. Estructura de la Base de Dades

Es necessiten les seguents taules per gestionar la seguretat:

**Taula users**: Guarda la informacio dels usuaris

- id (clau primaria)
- username (unic)
- password (encriptada amb BCrypt)
- is_enabled (si el compte esta actiu)
- account_no_expired
- account_no_locked
- credential_no_expired

**Taula roles**: Guarda els rols disponibles

- id (clau primaria)
- role_name (ADMIN, MANAGER, USER)

**Taula user_roles**: Relacio many-to-many entre usuaris i rols

- user_id (clau forana a users)
- role_id (clau forana a roles)

**Taula tokens**: Guarda els tokens JWT per poder invalidar-los si cal

- id (clau primaria)
- token (el JWT)
- token_type (sempre BEARER)
- is_revoked (si s'ha invalidat)
- is_expired (si ha caducat)
- user_id (clau forana a users)

## 6. Funcionament del JWT

1. L'usuari envia POST /api/auth/login amb username i password
2. El servidor valida les credencials contra la base de dades
3. Si son correctes, genera un JWT amb la informacio de l'usuari
4. El servidor guarda el token a la taula tokens i l'envia al client
5. El client guarda el token i l'envia a cada peticio al header Authorization: Bearer TOKEN
6. El servidor valida el token i permet o denega l'acces segons el rol

El token te una durada d'1 hora. Despres d'aquest temps, l'usuari ha de tornar a fer login.

## 7. Decisions de Disseny

**Per que JWT i no sessions?**

- JWT es stateless, no cal guardar estat al servidor
- Permet escalar l'aplicacio facilment
- Es l'estandard actual per APIs REST

**Per que guardar els tokens a la base de dades?**

- Per poder invalidar tokens si l'usuari fa logout
- Per controlar quins tokens son actius
- Per seguretat addicional

**Com es controlen els permisos?**

- Es fa servir Spring Security amb configuracio a SecurityConfig
- Es defineixen les regles per endpoint i metode HTTP
- Els rols s'assignen als usuaris a la base de dades

## 8. Endpoints Publics i Protegits

**Publics (no cal token):**

- POST /api/auth/login
- POST /api/auth/register
- POST /api/auth/refresh-token

**Protegits (cal token valid):**

- Tots els /api/books/\*\*
- Tots els /api/clients/\*\*
- Tots els /api/orders/\*\*

## 9. Resum de la Implementacio

Classes a crear:

- Entitats: UserEntity, RoleEntity, TokenEntity
- Repositoris: UserRepository, RoleRepository, TokenRepository
- Configuracio: SecurityConfig, RoleEnum
- JWT: JwtUtils, JwtAuthenticationFilter
- Serveis: UserDetailsServiceImpl, AuthService
- Controladors: AuthController
- DTOs: LoginRequest, RegisterRequest, TokenResponse

Dependencies Maven:

- spring-boot-starter-security
- jjwt-api, jjwt-impl, jjwt-jackson
- lombok (opcional, per reduir codi)

---

Document creat abans de la implementacio del codi.
Data: Mayo 2024
Assignatura: Desenvolupament d'Aplicacions Web
