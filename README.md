# 📚 Microservicio REST - reading-service

Este proyecto es un **microservicio REST** diseñado para gestionar una lista organizada de lecturas preferidas por usuarios. Implementa una **arquitectura limpia** orientada a microservicios y está desarrollado con tecnologías modernas como **Java 17**, **Spring Boot**, **MongoDB**, **Kafka**, y **Maven**.

---

## 🚀 Características Principales

- **Gestión de lecturas personalizadas**: Los usuarios pueden crear, consultar, actualizar y eliminar su lista de lecturas preferidas.
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
- **Kafka**: Mensajería para eventos y comunicación entre servicios.
- **Maven**: Herramienta de gestión de dependencias y compilación.
- **Docker**: Para contenedores y despliegue.
- **JUnit**: Framework para pruebas unitarias e integración.

---

## 📋 Operaciones Disponibles

El microservicio expone las siguientes APIs REST para interactuar con las listas de lecturas de los usuarios:

### 1. **Crear una nueva lectura**
   - **Método**: `POST`
   - **URL**: `/api/v1/lecturas`
   - **Descripción**: Permite al usuario agregar un nuevo elemento a su lista de lecturas.
   - **Cuerpo de la solicitud**:
     ```json
     {
       "titulo": "El arte de la guerra",
       "autor": "Sun Tzu",
       "categoria": "Estrategia",
       "prioridad": 1
     }
     ```

### 2. **Consultar la lista de lecturas**
   - **Método**: `GET`
   - **URL**: `/api/v1/lecturas`
   - **Descripción**: Recupera la lista completa de lecturas preferidas por el usuario.

### 3. **Actualizar una lectura existente**
   - **Método**: `PUT`
   - **URL**: `/api/v1/lecturas/{id}`
   - **Descripción**: Permite al usuario actualizar los detalles de un elemento específico.
   - **Cuerpo de la solicitud**:
     ```json
     {
       "titulo": "El arte de la guerra",
       "autor": "Sun Tzu",
       "categoria": "Estrategia",
       "prioridad": 2
     }
     ```

### 4. **Eliminar una lectura**
   - **Método**: `DELETE`
   - **URL**: `/api/v1/lecturas/{id}`
   - **Descripción**: Elimina un elemento específico de la lista de lecturas del usuario.

---

## 📦 Estructura del Proyecto

El proyecto sigue los principios de **arquitectura limpia**, organizándose en capas claramente definidas:

- **Domain**: Define las entidades, interfaces y reglas de negocio.
- **Application**: Contiene casos de uso y lógica de aplicación.
- **Infrastructure**: Implementaciones específicas como repositorios, controladores REST y configuración.
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

