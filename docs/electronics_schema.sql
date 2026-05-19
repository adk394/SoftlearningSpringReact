-- =====================================================
-- SCRIPT SQL PARA CREAR TABLA ELECTRONICS
-- =====================================================
-- Ejecutar este script manualmente en MySQL cuando quieras crear la tabla
-- La tabla NO se crea automaticamente con JPA (gestion manual de schema)
-- 
-- Para ejecutar:
-- mysql -u root -p softlearning < electronics_schema.sql
-- O usar un cliente MySQL (MySQL Workbench, DBeaver, etc.)
-- =====================================================

-- Seleccionar base de datos (ajusta el nombre segun tu configuracion)
-- USE softlearning;

-- =====================================================
-- CREAR TABLA ELECTRONICS
-- =====================================================
-- DESCOMENTAR LAS LINEAS SIGUIENTES PARA CREAR LA TABLA:

/*
CREATE TABLE IF NOT EXISTS electronics (
    -- ID autoincremental (Primary Key)
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Codigo unico del producto (String)
    id_product VARCHAR(50) NOT NULL UNIQUE,
    
    -- Nombre del producto (String)
    name VARCHAR(100) NOT NULL,
    
    -- Descripcion (String)
    description VARCHAR(500),
    
    -- Precio (double)
    price DOUBLE NOT NULL,
    
    -- Stock disponible (int)
    stock INT NOT NULL,
    
    -- Disponible para venta (boolean/TINYINT)
    is_available TINYINT(1) NOT NULL DEFAULT 1,
    
    -- Marca (String)
    brand VARCHAR(50) NOT NULL,
    
    -- Meses de garantia (int)
    warranty_months INT NOT NULL,
    
    -- Peso en kg (float)
    weight_kg FLOAT,
    
    -- Consumo en vatios (double)
    power_consumption_watts DOUBLE,
    
    -- Fecha de fabricacion (DATETIME)
    manufactured_datetime DATETIME,
    
    -- Caracteristicas separadas por | (String que simula array)
    features VARCHAR(1000)
);
*/

-- =====================================================
-- INSERTAR DATOS DE EJEMPLO (Opcional)
-- =====================================================
-- DESCOMENTAR PARA INSERTAR DATOS DE PRUEBA:

/*
INSERT INTO electronics (
    id_product, name, description, price, stock, is_available,
    brand, warranty_months, weight_kg, power_consumption_watts,
    manufactured_datetime, features
) VALUES (
    'ELEC-001',
    'Smart TV Samsung 55" 4K',
    'Televisor inteligente con resolucion 4K UHD',
    899.99,
    25,
    1,
    'Samsung',
    24,
    18.5,
    120.0,
    '2024-12-25 14:30:00',
    '4K UHD|Smart TV|HDR10+|WiFi 6'
);

INSERT INTO electronics (
    id_product, name, description, price, stock, is_available,
    brand, warranty_months, weight_kg, power_consumption_watts,
    manufactured_datetime, features
) VALUES (
    'ELEC-002',
    'Laptop Dell XPS 13',
    'Portatil ultraligero con procesador Intel i7',
    1299.99,
    15,
    1,
    'Dell',
    12,
    1.2,
    45.5,
    '2024-11-15 09:00:00',
    'Intel i7|16GB RAM|512GB SSD|Pantalla tactil'
);

INSERT INTO electronics (
    id_product, name, description, price, stock, is_available,
    brand, warranty_months, weight_kg, power_consumption_watts,
    manufactured_datetime, features
) VALUES (
    'ELEC-003',
    'iPhone 15 Pro',
    'Smartphone con camara de 48MP y titanio',
    1099.99,
    0,
    0,
    'Apple',
    12,
    0.187,
    25.0,
    '2024-10-20 10:15:00',
    '5G|Face ID|Camara 48MP|Titanio'
);
*/

-- =====================================================
-- CONSULTAS DE EJEMPLO
-- =====================================================
-- DESCOMENTAR PARA PROBAR:

-- Ver todos los productos:
-- SELECT * FROM electronics;

-- Buscar por marca:
-- SELECT * FROM electronics WHERE brand = 'Samsung';

-- Buscar disponibles:
-- SELECT * FROM electronics WHERE is_available = 1;

-- Buscar por nombre (parcial):
-- SELECT * FROM electronics WHERE name LIKE '%TV%';

-- Contar por marca:
-- SELECT brand, COUNT(*) as total FROM electronics GROUP BY brand;

-- =====================================================
-- ELIMINAR TABLA (Si necesitas borrar y recrear)
-- =====================================================
-- DESCOMENTAR PARA ELIMINAR LA TABLA:
-- DROP TABLE IF EXISTS electronics;

-- =====================================================
-- NOTAS IMPORTANTES
-- =====================================================
-- 1. Este script debe ejecutarse MANUALMENTE
-- 2. La aplicacion NO crea la tabla automaticamente
-- 3. Una vez creada la tabla, puedes usar el CRUD desde la API
-- 4. El campo 'features' almacena un array como String separado por |
-- 5. El campo 'manufactured_datetime' usa formato: yyyy-MM-dd HH:mm:ss
-- =====================================================
