# Usar una imagen base con JDK 21
FROM eclipse-temurin:21-jdk-jammy

# Configurar el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY build/libs/nauta-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]