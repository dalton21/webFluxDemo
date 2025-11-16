# webfluxDemo

Pequeña API demo con Spring WebFlux y MongoDB.

Descripción
-----------
`webfluxDemo` es una aplicación de ejemplo construida con Spring Boot + Spring WebFlux que expone un CRUD mínimo para la entidad `Nodo`. Está pensada para mostrar el uso de reactive repositories con MongoDB y cómo exponer endpoints reactivos.

Estructura relevante
--------------------
- `src/main/java/com/hastalasnubes/model/Nodo.java` — clase de dominio `Nodo` (campos: `id`, `titulo`, `cuerpo`).
- `src/main/java/com/hastalasnubes/repository/NodoRepository.java` — repositorio reactivo (extiende `ReactiveCrudRepository`).
- `src/main/java/com/hastalasnubes/service` — servicio que contiene la lógica de negocio (`NodoService`, `NodoServiceImpl`).
- `src/main/java/com/hastalasnubes/controller/NodoController.java` — controlador REST con endpoints para crear y listar nodos.
- `src/main/resources/application.properties` — configuración. Por defecto usa `spring.data.mongodb.uri=mongodb://localhost:27017/cms`.

Requisitos
---------
- Java 11+ (o la versión usada para compilar el proyecto).
- Docker & Docker Compose (opcional, para levantar MongoDB con facilidad).
- Maven (se incluye el wrapper `mvnw` / `mvnw.cmd`).

Arrancar MongoDB con Docker Compose
---------------------------------
El repositorio incluye un `docker-compose.yml` que levanta MongoDB. Desde el directorio raíz del proyecto ejecuta (Windows cmd.exe):

```cmd
docker-compose up -d
```

Esto iniciará un contenedor `mongodb` con el puerto 27017 mapeado.

Compilar y ejecutar la aplicación
--------------------------------
Desde Windows (cmd.exe) usando el wrapper de Maven:

```cmd
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

O ejecutar el JAR generado:

```cmd
mvnw.cmd clean package
java -jar target\webfluxDemo-0.0.1-SNAPSHOT.jar
```

(Adaptar el nombre del JAR si la versión difiere.)

Configuración
-------------
La conexión a MongoDB se obtiene desde `src/main/resources/application.properties`:

spring.data.mongodb.uri=mongodb://localhost:27017/cms

Si ejecutas MongoDB en otro host/puerto o con credenciales, actualiza esa URI.

API: Endpoints disponibles
--------------------------
Base path: `/nodos`

- POST /nodos
  - Crea un nuevo `Nodo`.
  - Código de respuesta: 201 Created
  - Payload JSON de ejemplo:

```json
{
  "id": 1,
  "titulo": "Mi primer nodo",
  "cuerpo": "Contenido de ejemplo"
}
```

- GET /nodos
  - Devuelve todos los nodos (Flux / lista JSON).

- GET /nodos/{id}
  - Devuelve un `Nodo` por su `id` (tipo `long` en el modelo).

Observaciones de diseño
-----------------------
- El modelo `Nodo` usa `@Document(collection = "employees")` y el campo `id` es de tipo `long`. Normalmente, con MongoDB se usan `String` u `ObjectId` para el id, pero aquí el proyecto mantiene un `long` y una colección llamada `employees` (probablemente heredado de un ejemplo). Ten en cuenta esto si importas datos o integras con otras apps.
- `NodoRepository` extiende `ReactiveCrudRepository<Nodo, Long>` pero define `Optional<Nodo> findByTitulo(String)` — esto no es reactivo. Funciona porque la implementación la llama de forma síncrona en el servicio, pero para un diseño puramente reactivo sería mejor devolver `Mono<Nodo>`.
- `NodoServiceImpl.saveNodo` lanza `ResourceNotFoundException` si ya existe un nodo con el mismo título. Revisa ese comportamiento si prefieres otra política (ej. actualizar en lugar de rechazar).

Ejemplos de uso (curl)
----------------------
Crear un nodo:

```bash
curl -X POST "http://localhost:8080/nodos" -H "Content-Type: application/json" -d "{\"id\":2,\"titulo\":\"Prueba\",\"cuerpo\":\"Texto\"}"
```

Listar nodos:

```bash
curl http://localhost:8080/nodos
```

Obtener por id:

```bash
curl http://localhost:8080/nodos/2
```

Pruebas y desarrollo
--------------------
- Hay una clase de test `WebfluxDemoApplicationTests` en `src/test` (si quieres ejecutar tests):

```cmd
mvnw.cmd test
```

Siguientes mejoras sugeridas
---------------------------
- Hacer que `NodoRepository.findByTitulo` devuelva `Mono<Nodo>` para mantener la reactividad.
- Cambiar el tipo de `id` a `String` o `ObjectId` y aprovechar `org.bson.types.ObjectId` o `String` para compatibilidad con MongoDB.
- Añadir validación de entrada en el controlador (p. ej. usando `@Validated` y DTOs).
- Añadir endpoints para DELETE y PUT/PATCH.

Problemas comunes y troubleshooting
---------------------------------
- Error de conexión a MongoDB: asegúrate de que Mongo está levantado y la URI en `application.properties` es correcta.
- Puerto en uso: si tu app no arranca por puerto ocupado, cambia `server.port` en `application.properties`.

Contacto
--------
Si necesitas que adapte el README a más detalle (ej. ejemplos de Postman, OpenAPI/Swagger, o instrucciones para Windows y Linux paso a paso), dime qué prefieres y lo añado.

