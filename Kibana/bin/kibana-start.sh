#!/bin/bash
docker stop twitch-kibana

docker container rm twitch-kibana

docker build . --tag kibana:latest
docker run --network tap-project_twitch --ip 10.0.100.52 -p 5601:5601 --name twitch-kibana kibana:latest