FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/MCPServer-0.0.1-SNAPSHOT.jar MCPServer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "MCPServer.jar"]