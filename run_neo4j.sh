#! /bin/bash

docker run \
-d --publish=7474:7474 --publish=7687:7687 \
neo4j
