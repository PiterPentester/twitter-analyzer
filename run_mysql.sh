#! /bin/bash

docker run \
-e MYSQL_ROOT_PASSWORD=secret \
-e MYSQL_DATABASE=twitter-analyzer \
-e MYSQL_USER=twitter \
-e MYSQL_PASSWORD=twitter \
-p 3306:3306 \
-d mysql
