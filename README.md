# 📦 Products & Inventory Microservices



## 🧩 Descripción General



Este proyecto implementa una arquitectura basada en microservicios para la gestión de:



- 🛍 **Productos**

- 📦 **Inventario**



Cada microservicio es independiente y se conecta a una base de datos PostgreSQL compartida físicamente, pero con separación lógica mediante **schemas independientes**:



- `products`

- `inventory`



La aplicación está construida con:



- Java 17

- Spring Boot Web flux

- Arquitectura limpia (Clean Architecture)

- PostgreSQL

- Redis

- Docker



---



# 🏗 Arquitectura

![Arquitectura](https://miro.medium.com/v2/resize:fit:720/format:webp/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

La solución sigue principios de:



- Clean Architecture

- Separación por dominios

- Independencia de microservicios

- Responsabilidad única por esquema




## 📌 Esquemas en base de datos



| Microservicio | Schema        | Responsabilidad |

|--------------|--------------|----------------|

| Products     | `products`   | Gestión de productos |

| Inventory    | `inventory`  | Gestión de stock |



Aunque la base de datos es física única, cada servicio:



- Tiene su propio usuario

- Solo accede a su schema

- No accede a tablas del otro servicio



---





# 🚀 Cómo levantar el proyecto



## 1️⃣ Levantar infraestructura



```bash

docker compose up -d

```

---

# 📈 Evolutivos de Negocio

A continuación se describen mejoras orientadas a ampliar la capacidad funcional del sistema y su alineación con necesidades comerciales futuras.

---

## 🏷 1️⃣ Gestión de SKU por Producto

Incorporar un identificador comercial único (SKU) por producto.


- Facilita integración con sistemas externos (ERP, e-commerce, marketplaces).
- Permite trazabilidad comercial.
- Mejora control de catálogo y reportes.
- Soporta múltiples canales de venta.

### Propuesta técnica

- Agregar campo `sku` único en la entidad `Product`.
- Restricción `UNIQUE` a nivel de base de datos.
- Validación de unicidad en capa de aplicación.
- Posibilidad futura de soportar múltiples SKUs por producto (variantes).

---

## 🏬 2️⃣ Inventario por Bodegas

Evolucionar el modelo actual hacia un inventario distribuido por ubicación física.


- Control de stock por ciudad o centro de distribución.
- Soporte para logística descentralizada.
- Optimización de despacho y tiempos de entrega.
- Mejor planificación de abastecimiento.
- Permite múltiples registros de inventario por producto.
- Habilita reportes por bodega.
- Facilita futuras reglas de negocio como:
    - Transferencias entre bodegas.
    - Stock mínimo por ubicación.
    - Reservas por canal de venta.


# 🚀 Evolutivos Técnicos


---

## 🔐 1️⃣ Fortalecimiento de Seguridad

Robustecer autenticación y autorización.


- Implementar OAuth2 / JWT como Resource Server.
- Control de acceso basado en roles (RBAC).
- Rate limiting por usuario o IP.
- Configuración estricta de CORS.
- Headers de seguridad (HSTS, X-Content-Type-Options, etc.).

Esto permitiría proteger los microservicios en entornos productivos y facilitar la integración con proveedores de identidad externos.

---

## 🌐 2️⃣ Implementación de API Gateway

Centralizar seguridad y gobernanza.


- Punto único de entrada.
- Validación de JWT centralizada.
- Rate limiting global.
- Logging y monitoreo unificado.
- Aplicación de filtros transversales.

---

## 🔁 3️⃣ Idempotencia con Redis
Evitar duplicidad en operaciones críticas.


- Uso de header `Idempotency-Key`.
- Almacenamiento temporal en Redis.
- Retorno de respuesta previamente procesada si la clave ya existe.
- TTL configurable.

Esto permite reintentos seguros en entornos distribuidos.

---

## 🏷 4️⃣ Control de Concurrencia – ETag + If-Match

Prevenir sobrescritura de datos.

- Generar `ETag` al consultar recursos.
- Exigir `If-Match` en actualizaciones.
- Retornar `412 Precondition Failed` si el recurso cambió.
- Control de concurrencia optimista.
- Evita pérdida de información.
- Basado en estándar HTTP.

---

Estos evolutivos permiten que la arquitectura escale de manera segura, resiliente y alineada con buenas prácticas en entornos distribuidos.




