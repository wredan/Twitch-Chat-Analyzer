#!/bin/bash
bin/spark-build.sh

docker stop twitch-python-spark

docker container rm twitch-python-spark

docker build . --tag twitch_python:spark
docker run -it --network twitch-chat-analyzer_twitch --ip 10.0.100.42 --name twitch-python-spark twitch_python:spark