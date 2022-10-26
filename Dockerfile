# Maven 
FROM maven:3.8.3-openjdk-16 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -f /app/pom.xml clean package

FROM openjdk:16.0.2-jdk-buster
WORKDIR /app
COPY --from=builder /app/target/grpc-server-1.0.jar .
# Use shell script to support passing application name and its arguments to the ENTRYPOINT
EXPOSE 8080
ENTRYPOINT java -DPOSTGRES_PORT=$port -DPOSTGRES_HOST=$host -DPOSTGRES_DB_NAME=$db -DPOSTGRES_USER=$user -DPOSTGRES_PASSWORD=$password -cp grpc-server-1.0.jar com.example.Main.Main
#java -DPOST -cp grpc-server-1.0.jar com.example.Main.Main