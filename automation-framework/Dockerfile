# Uses the Ubuntu/Debian-based Temurin JDK 17 image, which fully supports ARM64 / Apple Silicon
FROM eclipse-temurin:17-jdk

# Install Maven via apt
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies to leverage Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy project source code
COPY src ./src

# Default command to run tests
CMD ["mvn", "test"]