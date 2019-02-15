# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="mriganka.roy@gmail.com"

# Add a volume pointing to /app
VOLUME /app

# Make port 8071 available to the world outside this container
EXPOSE 8071

# The application's jar file
ARG JAR_FILE=target/gateway-engine-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} /app/gateway-engine.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/gateway-engine.jar"]
