# Maven 
FROM maven:3.8.3-openjdk-16 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -f /app/pom.xml clean package

FROM openjdk:16.0.2-jdk-buster
WORKDIR /app
COPY --from=builder /app/target/grpc-server-1.0.jar .
COPY --from=builder app.env .

RUN echo $(ls)

RUN export $(xargs < app.env)
# Use shell script to support passing application name and its arguments to the ENTRYPOINT
EXPOSE 8085
ENTRYPOINT java -DHOST=$HOST -DPORT=$PORT -DDBNAME=$DBNAME -DUSER=$USER -DPASSWORD=$PWD -cp grpc-server-1.0.jar com.example.Main.Main