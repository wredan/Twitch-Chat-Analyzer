#!/bin/bash
rm modules.zip
pushd code/modules/
zip -r ../../modules.zip ./
popd

docker stop twitch-python-spark

docker container rm twitch-python-spark

docker build . --tag twitch_python:spark
docker run -it --network twitch-chat-analyzer_twitch --ip 10.0.100.42 -p 9092:9092 --name twitch-python-spark twitch_python:spark