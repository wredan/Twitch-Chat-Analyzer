#!/bin/bash
cd Kafka-Connector

mvn package

cd ../

rm Kafka-Settings/twitch-kafka-connector-0.0.1-SNAPSHOT.jar
cp Kafka-Connector/target/twitch-kafka-connector-0.0.1-SNAPSHOT.jar Kafka-Settings/