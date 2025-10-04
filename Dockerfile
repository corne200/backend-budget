# Imagen base con Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Crear directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto
COPY . .

# Construir el proyecto sin ejecutar tests
RUN ./mvnw clean package -DskipTests

# Etapa final: imagen liviana solo con el JAR compilado
FROM eclipse-temurin:17-jdk

# Crear un directorio de trabajo para la app
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto (ajústalo si tu Spring Boot usa otro)
EXPOSE 8080

# Variable de entorno para puerto (opcional, Render la usa por defecto)
ENV PORT=8080

# Comando para ejecutar el backend
ENTRYPOINT ["java", "-jar", "app.jar"]
