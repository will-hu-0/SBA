#!/bin/bash

CONTAINER='sba-payment'

mvn clean
mvn install -Dmaven.test.skip=true

startUpContainer () {
  docker run -it --name $CONTAINER \
    -p 9995:9995 \
    -v `pwd`/target/payment-0.0.1-SNAPSHOT.jar:/apps/springboot.jar \
    -e "SPRING_PROFILES_ACTIVE=k8s" \
    --network sba \
    --network-alias $CONTAINER  \
    openjdk:8-jdk java \
    -jar /apps/springboot.jar
}

startUpContainer
docker logs -f $$CONTAINER