# Koopey backend

The application consists of both a frontend and backend.

- The frontend is based on node and Angular.
- The backend is based on java and springboot

## Build

To build the backend docker image

> `docker image build -t dotfive-backend .`
> To build backend project in java using gradle
> `.\gradlew`
> To build backend project in java
> `javac -d "bin" "src/com/koopey/server/ServerApplication"`

## Run

To run the "backend" through Gradle

> `.\gradlew bootRun`
> To run the "backend" docker image
> `docker container run -p 8111:8111 dotfive-backend`
> To run the "backend" java
> `java -classpath "bin" com.koopey.api.ServerApplication` > `java -jar koopey.jar`

## Test

To run tests

> `.\gradlew test`

## Links

To access features of the application

- [Backend](http://localhost:1709)
- [H2 database with JDBC URL "jdbc:h2:mem:app"](http://localhost:1709/console)
- [Open API](http://localhost:1709/v3/api-docs)
- [SpringBoot actuator health](http://localhost:1709/actuator/health)
- [SpringBoot actuator info](http://localhost:1709/actuator/info)
- [SpringBoot actuator metrics](http://localhost:1709/actuator/metrics)
- [SwaggerEndPoint](http://localhost:1709/api/v2/api-docs/)
- [SwaggerUi](http://localhost:1709/swagger-ui.html)
 

To access libraries used in this appliaction

- [Application properties](https://docs.spring.io/spring-boot/docs/2.4.1/reference/html/appendix-application-properties.html#common-application-properties)
- [SpringDoc for Open API and Swagger](https://springdoc.org/)
- [Java generate private and public keys](https://docs.oracle.com/javase/tutorial/security/apisign/step2.html)
- [Spring controllers](https://dzone.com/articles/14-tips-for-writing-spring-mvc-controller)

### Issues

- Auth is not integrated fully due to time constraints.
- Docker image has jdk8 by default which can be removed as we use jdk11.

## Shutdown server

> `netstat -a -o -n | findstr "1709"`
> `taskkill /F /PID 11080`

## Gradle

 > `./gradlew tasks --all`
 > `./gradlew javadoc`

/_INSERT INTO user (id, alias, name, password, email, mobile)
SELECT "1", "mole" ,'Scott Johnston', '$2a$10\$VKTuGcraGrYiKBTGbtQOs.8pugk9SA6PZc9jdJcN/IMU6xGxCteBu', "moleisking@gmail.com", "+34644862708" AS VALUE
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id ="1");_/

### Guides

- [Official Gradle documentation](https://docs.gradle.org)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.0.M6/gradle-plugin/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-jpa-and-spring-data)
- [Java Mail Sender](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-email)
- [Spring Web](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-developing-web-applications)
- [Spring Security](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-security)
- [Spring configuration file](https://docs.spring.io/spring-boot/docs/2.2.0.M6/reference/html/appendix.html#appendix)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [spring-boot-jwt-auth] (https://www.devglan.com/spring-security/spring-boot-jwt-auth)
- [spring Boot kafka] (https://www.tutorialspoint.com/spring_boot/spring_boot_apache_kafka.htm)
- [GraphQL] (https://netflix.github.io/dgs/getting-started/)

## Properties

- custom.jwt.expire=seconds

## SQL

`insert into transaction (
description, name,  type, asset_id, buyer_id, currency, destination_id, quantity, reference, seller_id, source_id, total, value, id
) values (
'des', 'name', 'type', null, null,'eur', null, 0, 'ref', 
UUID_TO_BIN('a62102c7-c103-4546-90ce-91cff7395894'), 
UUID_TO_BIN('1109cb64-480d-4e66-a156-97fa2f473baf'), 
0, 0, UUID_TO_BIN('768fabba-2b38-cea8-c0be-bc8d34190261'))`

# Sonar

> `./gradlew sonarqube -Dsonar.projectKey=KoopeyApi -Dsonar.host.url=http://localhost:9000 -Dsonar.login=312fcde051034f25d8eb3da40e7bc4c5317e479c`

java -cp KoopeyApi-0.0.1-SNAPSHOT.jar com/koopey/api/ServerApplication
java -jar KoopeyApi-0.0.1-SNAPSHOT.jar
