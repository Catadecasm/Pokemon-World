# Utiliza una imagen base de OpenJDK 17
FROM openjdk:17

# Etiqueta de información del autor
LABEL author="santi"

# Define un volumen para /tmp
VOLUME /tmp

# Expone el puerto 8080 en el contenedor
EXPOSE 8080

# Copia el archivo JAR de la aplicación al contenedor en la ubicación /app.jar
COPY target/PokemonDemo-0.0.1-SNAPSHOT.jar /app.jar

# Comando de inicio para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]