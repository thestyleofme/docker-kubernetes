FROM openjdk:8-jdk-alpine
MAINTAINER isacc codingdebugallday@163.com

COPY target/user-edge-service-1.0-SNAPSHOT.jar /jars/user-edge-service.jar

ENTRYPOINT ["java", "-jar", "/jars/user-edge-service.jar"]