FROM openjdk:11
LABEL version="1.0"

#ENV MYSQL_PASSWORD=2gwUA0iRJz5!DW7W*@D%&hh8%eUdiiRT
ARG KAFKA_HOST
ARG MYSQL_HOST
ARG MYSQL_PASSWORD
ARG MMYSQL_USER

ENV KAFKA_HOST ${KAFKA_HOST}
ENV MYSQL_HOST ${MYSQL_HOST}
ENV MYSQL_PASSWORD ${MYSQL_PASSWORD}
ENV MYSQL_USER ${MYSQL_USER}

#COPY src /koopeyapi/src/
#COPY gradle /koopeyapi/gradle/
#COPY build.gradle /koopeyapi/
#COPY settings.gradle /koopeyapi/
#COPY gradlew /koopeyapi/
COPY build/libs /koopeyapi/

WORKDIR /koopeyapi/

#RUN apk --no-cache add ca-certificates gradle 
#RUN gradle wrapper

EXPOSE 1709

#ENTRYPOINT ["./gradlew", "bootRun" ]
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "KoopeyApi-0.0.1-SNAPSHOT.jar" ]
#RUN java -jar KoopeyApi-0.0.1-SNAPSHOT.jar
#CMD ["java", "ServerApplication"]

