#!/bin/bash

CONTAINER='sba-registry'

mvn clean
mvn install -Dmaven.test.skip=true

# Start up a network
docker network create -d bridge sba

startUpContainer () {
  docker run -it --name $CONTAINER \
    -p 10001:10001 \
    -v `pwd`/target/registry-0.0.1-SNAPSHOT.jar:/apps/springboot.jar \
    -e "SPRING_PROFILES_ACTIVE=k8s" \
    --network sba \
    --network-alias $CONTAINER  \
    openjdk:8-jdk java \
    -jar /apps/springboot.jar \
    2>&1
}

startUpContainer
docker logs -f $$CONTAINER