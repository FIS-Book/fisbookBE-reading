# 📚 Microservicio REST - reading-service

Este proyecto es un **microservicio REST** diseñado para gestionar una lista organizada de lecturas preferidas por usuarios. Implementa una **arquitectura limpia** orientada a microservicios y está desarrollado con tecnologías modernas como **Java 17**, **Spring Boot**, **MongoDB**, **Kafka**, y **Maven**.

---

## 🚀 Características Principales

- **Gestión de lecturas de usuarios**: Los usuarios pueden crear, consultar, actualizar y eliminar su lista de lecturas preferidas.
- **Arquitectura limpia**: Basado en principios de separación de capas para facilitar mantenibilidad y escalabilidad.
- **Persistencia NoSQL**: MongoDB se utiliza como base de datos para almacenar los datos del microservicio.
- **Comunicación asincrónica**: Kafka se usa para integrar eventos con otros servicios en el ecosistema.
- **Pruebas**: Se incluyen pruebas unitarias y de integración para garantizar la calidad del código.
- **Despliegue**: Preparado para entornos en contenedores con Docker.

---

## 🛠️ Tecnologías y Herramientas Usadas

- **Java 17**: Lenguaje de programación para la implementación del servicio.
- **Spring Boot**: Framework para el desarrollo de aplicaciones web y REST.
- **MongoDB**: Base de datos NoSQL para almacenamiento.
- **Maven**: Herramienta de gestión de dependencias y compilación.
- **Docker**: Para contenedores y despliegue.
- **JUnit**: Framework para pruebas unitarias e integración.

---

## 📋 Operaciones Disponibles

El microservicio expone las siguientes APIs REST para interactuar con las listas de lecturas de los usuarios:

### 1. **Añadir una nueva lista de lecturas**
   - **Método**: `PUT`
   - **URL**: `/api/v1/readings/add-genre`
   - **Descripción**: Añadir un nuevo genero en lista de lecturas.
   - **Cuerpo de la solicitud**:
     ```json
     {
        "userId": "string",
        "genre": "string",
        "title": "string",
        "description": "string"
     }
     ```

### 2. **Añadir un nuevo libro**
   - **Método**: `PUT`
   - **URL**: `/api/v1/readings/add-book`
   - **Descripción**: Añadir un nuevo libro a un genero de lecturas.
   - **Cuerpo de la solicitud**:
     ```json
     {
        "userId": "string",
        "genre": "string",
        "isbn": "string",
        "title": "string"
     }
     ```

### 3. **Crear lista de lectura inicial**
   - **Método**: `POST`
   - **URL**: `/api/v1/readings`
   - **Descripción**: Crear lista de lectura inicial.
   - **Parámetros**:
     ```
     userId: string
     ```

### 4. **Consultar las lecturas**
   - **Método**: `GET`
   - **URL**: `/api/v1/readings/{userId}`
   - **Descripción**: Obtener todas las lecturas de un usuario.
   - **Parámetros**:
     ```
     userId: string
     ```

### 5. **Eliminar una lista de lectura**
   - **Método**: `DELETE`
   - **URL**: `/api/v1/readings/genre/{userId}/{genre}`
   - **Descripción**: Eliminar una lista de lectura según su genero.
   - **Parámetros**:
     ```
     userId: string
     genre: string
     ```
### 6. **Eliminar un libro**
   - **Método**: `DELETE`
   - **URL**: `/api/v1/readings/genre/{userId}/{genre}/{isbn}`
   - **Descripción**: Eliminar un libro de una lista de lecturas.
   - **Parámetros**:
     ```
     userId: string
     genre: string
     isbn: string
     ```

### 7. **Enviar un email**
   - **Método**: `POST`
   - **URL**: `/api/v1/email`
   - **Descripción**: Enviar un email.
   - **Cuerpo de la solicitud**:
     ```json
     {
        "to": "string",
        "subject": "string",
        "body": "string"
     }
     ```

---

## 📦 Estructura del Proyecto

- **Dto**: Objetos para tranferir la información entre capas
- **Controller**: Clases encargadas para la exposición de APIs.
- **Service**: Especificaciones e implementaciones de los servicios ofrecidos
- **Entity**: Clases usadas para el mapeo entre Java y Mongo.
- **Repository**: Especificaciones para el acceso y operaciones con la base de datos
- **Tests**: Pruebas unitarias y de integración organizadas en módulos específicos.

---

## 📖 Cómo Ejecutar el Proyecto

### Requisitos Previos
- Java 17
- Maven 3.8+
- MongoDB (puerto por defecto: `27017`)

### Pasos
1. Clona el repositorio:
   ```bash
   git clone https://github.com/FIS-Book/fisbookBE-reading.git
   cd reading-service
2. Construcción del proyecto
   mvn clean package
3. Construcción de la imagen 
   docker build -t reading-service .
4. docker run -it --rm -p 3002:8080 --name reading-service reading-service

