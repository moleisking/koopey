FROM openjdk:11
LABEL version="1.0"

#ENV MYSQL_PASSWORD=2gwUA0iRJz5!DW7W*@D%&hh8%eUdiiRT

#COPY src /koopeyapi/src/
#COPY gradle /koopeyapi/gradle/
#COPY build.gradle /koopeyapi/
#COPY settings.gradle /koopeyapi/
#COPY gradlew /koopeyapi/
COPY build /koopeyapi/

WORKDIR /koopeyapi/

#RUN apk --no-cache add ca-certificates gradle 
#RUN gradle wrapper

EXPOSE 1709
 
#ENTRYPOINT ["./gradlew", "bootRun" ]

RUN java -jar KoopeyApi-0.0.1-SNAPSHOT.jar
CMD ["java", "ServerApplication"]

