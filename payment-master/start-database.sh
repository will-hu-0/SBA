#!/bin/bash

CONTAINER='sba-payment-mysql'

startUpContainer () {
  docker run -d --name $CONTAINER \
    -e TZ='Asia/Shanghai' \
    -e MYSQL_DATABASE=sba_payment \
    -e MYSQL_ROOT_PASSWORD=123456 \
    -p 32003:3306 \
    --network-alias $CONTAINER  \
    mysql:5.7 \
    --character-set-server=utf8 \
    --skip-character-set-client-handshake=1
}

execute () {
  if [ "$(docker ps -aq -f status=exited -f name=$CONTAINER)" ]; then
    echo "Restarting existing mysql container.."
    docker start $CONTAINER
  elif [ "$(docker ps -q -f name=$CONTAINER)" ]; then
    echo "The mysql container is still running, skipping re-creating process.."
  else
    # run your container
    echo "Starting a new mysql container.."
    startUpContainer
  fi
}

execute