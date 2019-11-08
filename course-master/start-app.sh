#!/bin/bash

CONTAINER='sba-course'

mvn clean
mvn install -Dmaven.test.skip=true

startUpContainer () {
  docker run -it --name $CONTAINER \
    -p 9996:9996 \
    -v `pwd`/target/course-0.0.1-SNAPSHOT.jar:/apps/springboot.jar \
    -e "SPRING_PROFILES_ACTIVE=k8s" \
    --network sba \
    --network-alias $CONTAINER  \
    openjdk:8-jdk java \
    -jar /apps/springboot.jar
}

startUpContainer
docker logs -f $$CONTAINER