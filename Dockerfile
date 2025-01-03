# Usa una imagen base de Java 17
FROM openjdk:17-jdk-slim

# Directorio de trabajo
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/reading-service.jar reading-service.jar

# Exponer el puerto de la aplicación
EXPOSE 3000

# Ejecutar la aplicación
CMD ["java", "-jar", "reading-service.jar"]