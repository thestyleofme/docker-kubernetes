#!/usr/bin/env bash
mvn clean package

docker build -t course-dubbo-service:1.0 .