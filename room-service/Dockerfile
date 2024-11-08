FROM ubuntu:latest
LABEL authors="ommakwana"

ENTRYPOINT ["top", "-b"]


#-----------------
# Build Stage
#-----------------
FROM gradle:8-jdk23-alpine AS builder

# Copy the application code to the builder image
COPY --chown=gradle:gradle . /home/gradle/src

# Set the working directory
WORKDIR /home/gradle/src

# Build the application, excluding tests
RUN gradle build -x test

#-----------------
# Package
#-----------------
FROM openjdk:23-jdk

# Create a directory for the application
RUN mkdir /app

# Copy the built JAR file from the builder stage
COPY --from=builder /home/gradle/src/build/libs/*jar /app/room-service.jar

# Set PostgreSQL database credentials as environment variables
ENV POSTGRES_DB=room-service \
    POSTGRES_USER=admin \
    POSTGRES_PASSWORD=password

# Expose the port the application runs on
EXPOSE 9001

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/room-service.jar"]
