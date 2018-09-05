#!/usr/bin/env bash
mvn clean package

docker build -t course-dubbo-service.jar:1.0 .