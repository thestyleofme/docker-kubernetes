FROM openjdk:8-jdk-alpine
MAINTAINER isacc codingdebugallday@163.com

COPY target/user-thrift-service-1.0-SNAPSHOT.jar /jars/user-thrift-service.jar

ENTRYPOINT ["java", "-jar", "/jars/user-thrift-service.jar"]