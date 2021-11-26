# Spring Boot

This part is responsible for getting and updating data from the FastAPI part and storing all incoming data in the
PostgreSQL Database. Spring Security is used to Authorize and Authenticate users.

## 💻 Demo

![List of Controllers](../assets/SpringBootSwagger.png)

![app-user-controller](../assets/UserControllerSwagger.png)

![finmine-controller](../assets/FinmineControllerSwagger.png)

![registration-controller](../assets/RegistrationControllerSwagger.png)

![what-works-controller](../assets/WhatWorksControllerSwagger.png)

## 🔗 Build

```
mvn clean package
docker build -t fin-mine-spring-docker
```