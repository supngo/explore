# User Management API

### Reference Documentation

For further reference, please consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.6/maven-plugin/reference/html/)
- [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#web)
- [Spring Security](https://docs.spring.io/spring-boot/docs/3.0.6/reference/htmlsingle/#web.security)

## Guide

The goal of this exercise is to build a small application that persists, deletes, and lists users

### Solution

### 1. Requirements:

- Java 17 (https://www.oracle.com/java/technologies/downloads/)
- Maven (https://maven.apache.org/)
- Postman (https://www.postman.com/)
- h2 Database (https://www.h2database.com/html/main.html/)

### 2. How to build and run

- Checkout the repo at (https://github.com/supngo/explore.git)
- Run locally:

```
mvn clean package
java -jar target/explore-1.0.0.jar
```

### 3. Test REST Endpoints

User Postmand or Swagger to test the 4 endpoints below:

POST http://localhost:8080/explore/userManagement/user/

GET http://localhost:8080/explore/userManagement/user/{id}

GET http://localhost:8080/explore/userManagement/users

DELETE http://localhost:8080/explore/userManagement/user/{id}

### 4. Run Unit Test

```
mvn test
```

### 5. Heath Check

http://localhost:8080/explore/actuator/health

### 6. Swagger

http://localhost:8080/explore/swagger-ui/index.html

Question? thongo5430@gmail.com
