FROM openjdk:8u212-jdk-slim
LABEL maintainer="shoebahmed@yahoo.com"
VOLUME /tmp
EXPOSE 8080
ADD target/sellics-keyword-0.0.1-SNAPSHOT.jar sellics-keyword.jar
ENTRYPOINT ["java","-jar","/sellics-keyword.jar"]