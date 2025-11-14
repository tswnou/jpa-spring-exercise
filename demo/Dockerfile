# Use official JDK image
FROM eclipse-temurin:17-jdk

# Set working directory inside container
WORKDIR /app

# Copy Maven project
COPY . .

# Build JAR using Maven wrapper
RUN ./mvnw -DskipTests package

# Run the Spring Boot app
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
