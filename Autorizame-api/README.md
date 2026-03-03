# 🚚 Autorizame API

API REST desarrollada con **Spring Boot** para la gestión de **Clientes, Autorizados, Empresas, Repartidores y Pedidos**, incluyendo operaciones CRUD completas, validaciones, agregación de datos, manejo global de errores y documentación automática con **Swagger / OpenAPI**.

Proyecto desarrollado con fines académicos siguiendo buenas prácticas de arquitectura REST.

---

## 📌 Características principales

- ✅ CRUD completo para todos los recursos
- ✅ Arquitectura por capas (Controllers, Services, Models)
- ✅ Inyección de dependencias (IoC)
- ✅ Validaciones con Jakarta Bean Validation
- ✅ Manejo global de errores con excepción personalizada
- ✅ Agregación de datos (conteo de pedidos por cliente)
- ✅ Documentación automática con Swagger / OpenAPI
- ✅ Base de datos en memoria (HashMap)
- ✅ Códigos HTTP correctos con ResponseEntity

---

## 🧱 Recursos disponibles

| Recurso        | Endpoint base                |
|----------------|------------------------------|
| Clientes       | /api/v1/clientes             |
| Autorizados    | /api/v1/autorizados          |
| Empresas       | /api/v1/empresas             |
| Repartidores   | /api/v1/repartidores         |
| Pedidos        | /api/v1/pedidos              |

---

## 🔁 Operaciones disponibles por recurso (CRUD)

- GET / → Obtener todos
- GET /{id} → Obtener por ID
- POST / → Crear nuevo
- PUT /{id} → Actualizar
- DELETE /{id} → Eliminar

Códigos de respuesta utilizados:
- 200 OK
- 201 Created
- 204 No Content
- 400 Bad Request
- 404 Not Found
- 500 Internal Server Error

---

## ➕ Agregación de datos

Endpoint para obtener el total de pedidos de un cliente:

GET /api/v1/pedidos/cliente/{idCliente}/total

Ejemplo de respuesta:

```json
{
  "idCliente": 1,
  "totalPedidos": 5
}
```
---
## ❗ Manejo de errores

La API implementa:

Excepción personalizada: RecursoNoEncontradoException

Manejador global con @RestControllerAdvice

Respuestas de error unificadas en formato JSON
```json
{
  "timestamp": "2025-12-06T22:20:10.456",
  "status": 404,
  "error": "Recurso no encontrado",
  "message": "Cliente con id 122 no encontrado"
}
```
---
## 📘 Documentación con Swagger

La API está documentada automáticamente mediante Swagger / OpenAPI.

Acceso una vez arrancada la aplicación:

http://localhost:8080/swagger-ui/index.html

Desde Swagger se pueden:

Probar endpoints

Ver modelos

Consultar esquemas de datos
---
## ⚙️ Instalación y ejecución
1. Clonar repositorio
```cmd
git clone https://github.com/tu-usuario/autorizame-api.git
```
cd autorizame-api

2. Compilar y ejecutar
```cmd
mvn clean
./mvnw spring-boot:run
```
La aplicación se ejecuta en:

http://localhost:8080
---
