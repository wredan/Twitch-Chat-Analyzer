#!/bin/bash
docker stop twitch-zookeeper

docker container rm twitch-zookeeper

docker build . --tag zookeeper:latest
docker run --network tap-project_twitch --ip 10.0.100.22 -p 2181:2181 --name twitch-zookeeper zookeeper:latest