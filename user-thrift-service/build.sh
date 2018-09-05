#!/usr/bin/env bash

mvn clean package

docker build -t user-thrift-service:1.0 .