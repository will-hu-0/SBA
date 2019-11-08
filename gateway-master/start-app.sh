#!/bin/bash

CONTAINER='sba-gateway'

mvn clean
mvn install -Dmaven.test.skip=true

startUpContainer () {
  docker run -it --name $CONTAINER \
    -p 9000:9000 \
    -v `pwd`/target/gateway-0.0.1-SNAPSHOT.jar:/apps/springboot.jar \
    -e "SPRING_PROFILES_ACTIVE=k8s" \
    --network sba \
    --network-alias $CONTAINER  \
    openjdk:8-jdk java \
    -jar /apps/springboot.jar \
    2>&1
}

startUpContainer
docker logs -f $$CONTAINER