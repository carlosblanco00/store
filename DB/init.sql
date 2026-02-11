-- ============================================
-- CREACIÓN DE ROLES
-- ============================================

CREATE ROLE product_user WITH LOGIN PASSWORD 'product_pass';
CREATE ROLE inventory_user WITH LOGIN PASSWORD 'inventory_pass';


-- ============================================
-- CREACIÓN DE BASES DE DATOS
-- ============================================

CREATE DATABASE product_db OWNER product_user;
CREATE DATABASE inventory_db OWNER inventory_user;


-- ============================================
-- CONFIGURACIÓN PRODUCT_DB
-- ============================================

\connect product_db

CREATE SCHEMA products AUTHORIZATION product_user;

ALTER ROLE product_user IN DATABASE product_db
SET search_path TO products;

GRANT ALL ON SCHEMA products TO product_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA products
GRANT ALL ON TABLES TO product_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA products
GRANT ALL ON SEQUENCES TO product_user;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =========================
-- TABLA PRODUCT
-- =========================

CREATE TABLE products.product (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(19,4) NOT NULL,
    cost NUMERIC(19,4) NOT NULL,
    status VARCHAR(50) NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_status ON products.product(status);

-- Trigger update_at
CREATE OR REPLACE FUNCTION products.update_product_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.update_at = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_product_timestamp
BEFORE UPDATE ON products.product
FOR EACH ROW
EXECUTE FUNCTION products.update_product_timestamp();


-- ============================================
-- CONFIGURACIÓN INVENTORY_DB
-- ============================================

\connect inventory_db

CREATE SCHEMA inventory AUTHORIZATION inventory_user;

ALTER ROLE inventory_user IN DATABASE inventory_db
SET search_path TO inventory;

GRANT ALL ON SCHEMA inventory TO inventory_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA inventory
GRANT ALL ON TABLES TO inventory_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA inventory
GRANT ALL ON SEQUENCES TO inventory_user;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =========================
-- TABLA INVENTORY
-- =========================

CREATE TABLE inventory.inventory (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id UUID NOT NULL,
    quantity NUMERIC(19,4) NOT NULL,
    cost_unit NUMERIC(19,4) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_inventory_product_id ON inventory.inventory(product_id);

-- Trigger update_at
CREATE OR REPLACE FUNCTION inventory.update_inventory_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.update_at = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_inventory_timestamp
BEFORE UPDATE ON inventory.inventory
FOR EACH ROW
EXECUTE FUNCTION inventory.update_inventory_timestamp();
