# Use an official OpenJDK 17 runtime with Maven pre-installed
FROM maven:3.9.5-eclipse-temurin-17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first to leverage Docker layer caching for dependencies
COPY pom.xml .

# Download dependencies offline to speed up subsequent builds
RUN mvn dependency:go-offline -B

# Copy the rest of your application source code
COPY src ./src

# Default command to run your test suite when the container starts
CMD ["mvn", "test"]