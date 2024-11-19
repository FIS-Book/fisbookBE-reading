# Usa una imagen base de Java 17
FROM openjdk:17-jdk-slim

# Directorio de trabajo
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/reading-service.jar /app/reading-service.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "/app/reading-service.jar"]