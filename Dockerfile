FROM openjdk:17-jdk

WORKDIR /app

LABEL maintainer="damian" \
      version="1.0" \
      description="Docker image for the search-service"

COPY target/search-service-0.0.1-SNAPSHOT.jar /app/search-service.jar

EXPOSE 8082

CMD ["java", "-jar", "/app/search-service.jar"]