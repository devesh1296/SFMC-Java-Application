FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# Copy pom.xml and source code
COPY pom.xml .
COPY src src

# Install Maven
RUN apk add --no-cache maven

# Package the application
RUN mvn package -DskipTests

# Create the final image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /workspace/app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables
ENV JAVA_OPTS=""
ENV PORT=8080

# Expose the port
EXPOSE ${PORT}

# Run the application
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
