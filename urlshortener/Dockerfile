# Use the official OpenJDK 21 image as a base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

COPY . /app

# Build the application without running tests
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "build/libs/urlshortener-0.0.1-SNAPSHOT.jar"]
