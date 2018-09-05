FROM openjdk:8-jdk-alpine
MAINTAINER isacc codingdebugallday@163.com

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /jars/api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/jars/api-gateway-zuul.jar"]