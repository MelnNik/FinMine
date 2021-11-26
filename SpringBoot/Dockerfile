FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
ENV PORT 8080
EXPOSE 8080
COPY ${JAR_FILE} FinMine.jar
ENTRYPOINT ["java","-jar","/FinMine.jar"]