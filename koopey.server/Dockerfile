FROM alpine:3.13
LABEL version="1.0"

ENV MYSQL_PASSWORD=2gwUA0iRJz5!DW7W*@D%&hh8%eUdiiRT

COPY src /koopey-backend/src/
COPY gradle /koopey-backend/gradle/
COPY build.gradle /koopey-backend/
COPY settings.gradle /koopey-backend/
COPY gradlew /koopey-backend/

WORKDIR /koopey-backend/

RUN apk --no-cache add gradle 
RUN apk --no-cache add ca-certificates
#RUN apk --no-cache add libc6-compat
RUN apk --no-cache add openjdk11
RUN apk --no-cache add mysql mysql-client
RUN gradle wrapper

RUN mysql -uroot -p""  
RUN mysql create database testdb;
RUN mysql SET PASSWORD FOR 'root' = PASSWORD('2gwUA0iRJz5!DW7W*@D%&hh8%eUdiiRT');
RUN mysql CREATE USER 'koopey'@'localhost' IDENTIFIED BY '2gwUA0iRJz5!DW7W*@D%&hh8%eUdiiRT';
RUN mysql GRANT ALL PRIVILEGES ON * . * TO 'koopey'@'localhost';

EXPOSE 1709
 
ENTRYPOINT ["./gradlew", "bootRun" ]

