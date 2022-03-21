FROM openjdk:11
LABEL version="1.0"
#2gwUA0iRJz5!DW7W*@D%&hh8%eUdiiRT
ENV KAFKA_HOST=192.168.1.180
ENV MYSQL_HOST=192.168.1.180
ENV MYSQL_PASSWORD=xxx
ENV MYSQL_USER=koopey

COPY build/libs /koopeyapi/

WORKDIR /koopeyapi/

EXPOSE 1709

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "KoopeyApi-0.0.1-SNAPSHOT.jar" ]
