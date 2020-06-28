#!/bin/bash
cd spark-twitch/
mvn package
cd ../

docker build --tag spark:consumer .