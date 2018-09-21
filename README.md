# Docker Kubernetes Study

```docker
docker pull openjdk:8-jdk-alpine
docker run -it --entrypoint /bin/sh openjdk:8-jdk-alpine
docker pull python:3.6

docker build -t user-thrift-service:1.0 .
docker run -it user-thrift-service:1.0 --mysql.address=192.168.174.137

docker build -t python-base:latest -f Dockerfile.base .
docker build -t message-python-thrift-service:1.0 .
docker run -it message-python-thrift-service:1.0

docker build -t user-edge-service:1.0 .
docker run -it user-edge-service:1.0 --redis.address=192.168.174.137

docker build -t course-dubbo-service:1.0 .
docker run -it course-dubbo-service:1.0 --mysql.address=192.168.174.137 --zookeeper.address=192.168.174.137

docker build -t course-edge-service:1.0 .
docker run -it course-dubbo-service:1.0 --zookeeper.address=192.168.174.137

docker build -t api-gateway-zuul:1.0 .
docker run -it api-gateway-zuul:1.0
```