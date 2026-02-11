\# 📦 Products \& Inventory Microservices



\## 🧩 Descripción General



Este proyecto implementa una arquitectura basada en microservicios para la gestión de:



\- 🛍 \*\*Productos\*\*

\- 📦 \*\*Inventario\*\*



Cada microservicio es independiente y se conecta a una base de datos PostgreSQL compartida físicamente, pero con separación lógica mediante \*\*schemas independientes\*\*:



\- `products`

\- `inventory`



La aplicación está construida con:



\- Java 17

\- Spring Boot Web flux

\- Arquitectura limpia (Clean Architecture)

\- PostgreSQL

\- Redis

\- Docker



---



\# 🏗 Arquitectura



La solución sigue principios de:



\- Clean Architecture

\- Separación por dominios

\- Independencia de microservicios

\- Responsabilidad única por esquema



\## 📌 Esquemas en base de datos



| Microservicio | Schema        | Responsabilidad |

|--------------|--------------|----------------|

| Products     | `products`   | Gestión de productos |

| Inventory    | `inventory`  | Gestión de stock |



Aunque la base de datos es física única, cada servicio:



\- Tiene su propio usuario

\- Solo accede a su schema

\- No accede a tablas del otro servicio



---



\# 🗄 Modelo de Datos



\## Products



Tabla: `products.products`



\- id (UUID)

\- name

\- description

\- price

\- cost

\- status

\- create\_at

\- update\_at



\## Inventory



Tabla: `inventory.inventory`



\- id (UUID)

\- product\_id (UUID)

\- quantity

\- cost\_unit

\- currency

\- is\_active

\- create\_at

\- update\_at



---



\# 🚀 Cómo levantar el proyecto



\## 1️⃣ Levantar infraestructura



```bash

docker compose up -d



