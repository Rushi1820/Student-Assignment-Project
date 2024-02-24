FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y redis-server

EXPOSE 6379

COPY target/Assignment-Playpowerlabs.jar .

EXPOSE 8080

#ENTRYPOINT ["java", "-jar", "Assignment-Playpowerlabs.jar"]
CMD ["sh", "-c", "redis-server --daemonize yes && java -jar Assignment-Playpowerlabs.jar"]
