#-----------------
# Build Stage
#-----------------
# Base image for the build stage
FROM gradle:8-jdk23-alpine AS builder

# Copy project files
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Build the application, skipping tests
RUN gradle build -x test

#-----------------
# Package Stage
#-----------------
FROM openjdk:23-jdk

# Create the application directory
RUN mkdir /app

# Copy the built JAR from the builder stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/booking-service.jar

# Set environment variables
ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PWD=password
# Expose the application port
EXPOSE 9004

# Define the entry point
ENTRYPOINT ["java", "-jar", "/app/booking-service.jar"]
