#!/bin/bash
docker stop elasticsearch

docker container rm elasticsearch

docker build . --tag twitch:elasticsearch
docker run --network tap-project_twitch --ip 10.0.100.51 -p 9200:9200 -e "discovery.type=single-node" --name elasticsearch twitch:elasticsearch