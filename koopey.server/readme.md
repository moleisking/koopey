# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.0.M6/gradle-plugin/reference/html/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-email)
* [Spring Web](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Security](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-security)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)

[spring-boot-jwt-auth] https://www.devglan.com/spring-security/spring-boot-jwt-auth

### Info links

* [Application properties](https://docs.spring.io/spring-boot/docs/2.2.0.M6/reference/html/appendix.html#appendix)

https://dzone.com/articles/14-tips-for-writing-spring-mvc-controller


https://docs.oracle.com/javase/tutorial/security/apisign/step2.html

# Build backend
javac -d "bin" "src/com/koopey/server/ServerApplication"

# Run backend
java -classpath "bin" com.koopey.server.ServerApplication
java -jar koopey.jar

# Shutdown server
netstat -a -o -n | findstr "8889"
taskkill /F /PID 11080


/*INSERT INTO user (id, alias, name, password, email, mobile)
SELECT "1", "mole" ,'Scott Johnston', '$2a$10$VKTuGcraGrYiKBTGbtQOs.8pugk9SA6PZc9jdJcN/IMU6xGxCteBu', "moleisking@gmail.com", "+34644862708" AS VALUE
WHERE NOT EXISTS (SELECT 1 FROM user WHERE id ="1");*/