#!/bin/bash

docker volume create twitch-chat-analyzer_elasticsearch

cd ElasticSearch
bin/elasticsearch-start.sh