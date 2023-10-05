FROM OPENJDK:17
LABEL authors="santi"
VOLUME /tmp
EXPOSE 8080
COPY target/PokemonDemo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]