# 📚 Microservicio REST - reading-service

Este proyecto es un **microservicio REST** diseñado para gestionar una lista organizada de lecturas preferidas por usuarios. Implementa una **arquitectura limpia** orientada a microservicios y está desarrollado con tecnologías modernas como **Java 17**, **Spring Boot**, **MongoDB** y **Maven**.

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
- **JUnit y Mockito**: Framework para pruebas unitarias e integración.

---

## 📋 Operaciones Disponibles

El microservicio expone las siguientes APIs REST para interactuar con las listas de lecturas de los usuarios:

### 1. **Crear lista de lectura inicial**
Este método es el core de los microservicios siguientes. Para un usuario que solicite y se confirme su alta en la aplicación deberá llamar a este microservicios
para realizar la instacia de su espacio de lecturas incial. 
   - **Método**: `POST`
   - **URL**: `/api/v1/readings/create-list`
   - **Descripción**: Crear lista de lectura inicial.
   - **Cuerpo de la solicitud**:
     ```json
     {
        "userId": "string"
     }
     ```    

### 2. **Actualizar una lista de lecturas**
   - **Método**: `PATCH`
   - **URL**: `/api/v1/readings/update-genre`
   - **Descripción**: Actualizar una lista de lecturas.
   - **Cuerpo de la solicitud**:
     ```json
     {
        "genreId": "string",
        "numberReviews": 0,
        "score": 0
     }
     ```  

### 3. **Añadir una nueva lista de lecturas**
   - **Método**: `PATCH`
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

### 4. **Añadir un nuevo libro**
   - **Método**: `PATCH`
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

### 5. **Consultar las lecturas**
   - **Método**: `GET`
   - **URL**: `/api/v1/readings`
   - **Descripción**: Obtener todas las lecturas de un usuario.
   - **Parámetros**:
     ```
     userId: string
     ```
   - **Ejemplo de la llamada**: /api/v1/readings?userId=00000000001

### 6. **Consultar una lista de lecturas**
   - **Método**: `GET`
   - **URL**: `/api/v1/readings/genres`
   - **Descripción**: Obtener una lista de lecturas específicas de un usuario.
   - **Parámetros**:
     ```
     genreId: string
     ```
   - **Ejemplo de la llamada**: /api/v1/readings/genre?genreId=00000000001  

### 7. **Eliminar una lista de lectura**
   - **Método**: `DELETE`
   - **URL**: `/api/v1/readings/genre/{userId}/{genre}`
   - **Descripción**: Eliminar una lista de lectura según su genero.   
   - **Ejemplo de la llamada**: /api/v1/readings/genre/00000000001/IA 

### 8. **Eliminar un libro**
   - **Método**: `DELETE`
   - **URL**: `/api/v1/readings/genre/{userId}/{genre}/{isbn}`
   - **Descripción**: Eliminar un libro de una lista de lecturas.
   - **Parámetros**:
     ```
     userId: string
     genre: string
     isbn: string
     ```
   - **Ejemplo de la llamada**: /api/v1/readings/genre/00000000001/IA/01010101

### 9. **Enviar un email**
   - **Método**: `POST`
   - **URL**: `/api/v1/email`
   - **Descripción**: Enviar un email.
   - **Cuerpo de la solicitud**:
     ```json
     {
        "from": "string"
        "to": "string",
        "subject": "string",
        "body": "string",
        "keyEmail": "string"
     }
     ```

### 10. **Consultar estado del microservicio**
   - **Método**: `GET`
   - **URL**: `/api/v1/readings/healthz`
   - **Descripción**: Obtener el estado del microservicio.

---

## 📦 Estructura del Proyecto

- **Dto**: Objetos para tranferir la información entre capas
- **Controller**: Clases encargadas para la exposición de APIs.
- **Service**: Especificaciones e implementaciones de los servicios ofrecidos
- **Entity**: Clases usadas para el mapeo entre Java y Mongo.
- **Repository**: Especificaciones para el acceso y operaciones con la base de datos
- **Tests**: Pruebas unitarias y de integración organizadas en módulos específicos.
- **Resources**: Almacen del fichero .properties que permite acceder mediante varibles a las propiedades de configuración del proyecto                 

---

## 📖 Cómo Contruir el Proyecto y generar la imagen

### Requisitos Previos
- Java 17
- Maven 3.8+
- MongoDB
- Docker última versión
- Solicitar la carpeta .env al equipo reading-service
```[edwareang@alum.us.es](mailto:edwareang@alum.us.es)
   [matgou@alum.us.es](mailto:matgou@alum.us.es)
```
### Pasos
1. Clona el repositorio:
   ```bash
   git clone https://github.com/FIS-Book/fisbookBE-reading.git
   cd reading-service
2. Construcción del proyecto y lanzamiento de pruebas unitarias y de integración
   Este punto es muy importante ya que debido a que las pruebas de integración realizan una conexión a la base de datos de mongo es muy importante cargar las variables del fichero .env mediante el siguiente comando
   **Get-Content .env | foreach { if ($_ -match "^(.*)=(.*)$") { [System.Environment]::SetEnvironmentVariable($matches[1], $matches[2]) } } 
   mvn clean package   
3. Construcción de la imagen 
   docker build -t reading-service .
4. Levantar la imagen en Docker.
   docker run -it --rm -p 8080:8080 --name reading-service --env-file .env reading-service  

## 📖 Test unitarios y de integración

