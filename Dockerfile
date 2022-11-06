# Maven 
FROM maven:3.8.3-openjdk-16 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -f /app/pom.xml clean package

FROM openjdk:16.0.2-jdk-buster
WORKDIR /app
COPY --from=builder /app/target/grpc-server-1.0.jar .
COPY app.env .
COPY parseVariables.sh .

RUN chmod +x parseVariables.sh
RUN parseVariables.sh

RUN echo $(ls)
RUN echo $(cat app.env)

# RUN export $(xargs < app.env)

RUN echo $PG_HOST

# Use shell script to support passing application name and its arguments to the ENTRYPOINT
EXPOSE 8085
ENTRYPOINT java -DHOST=$PG_HOST -DPORT=$PG_PORT -DDBNAME=$PG_DB -DUSER=$PG_USER -DPASSWORD=$PG_PASS -cp grpc-server-1.0.jar com.example.Main.Main