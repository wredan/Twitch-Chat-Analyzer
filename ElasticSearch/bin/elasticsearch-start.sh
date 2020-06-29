#!/bin/bash
docker stop elasticsearch

docker container rm elasticsearch

docker build . --tag twitch:elasticsearch
docker run --network twitch-chat-analyzer_twitch \
        --ip 10.0.100.51 \
        -p 9200:9200 \
        -e "discovery.type=single-node" \
        --name elasticsearch \
        -v twitch-chat-analyzer_elasticsearch:/usr/share/elasticsearch/data/ \
        twitch:elasticsearch