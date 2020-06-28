#!/bin/bash
docker stop twitch-spark

docker container rm twitch-spark

docker build . --tag twitch_python:spark
docker run -it --network tap-project_twitch --ip 10.0.100.42 -p 9092 --name twitch-spark twitch_python:spark