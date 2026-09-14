# Use an official Temurin JDK 17 base image that natively supports ARM64 (Apple Silicon)
FROM eclipse-temurin:17-jdk-alpine

# Install Maven via apk (lightweight)
RUN apk add --no-cache maven

# Set working directory
WORKDIR /app

# Copy pom.xml and cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy project source code
COPY src ./src

# Command to execute tests
CMD ["mvn", "test"]