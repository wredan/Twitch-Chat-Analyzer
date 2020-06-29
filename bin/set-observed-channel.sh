#!/bin/bash

sed -ir "s/^[#]*\s*target.channel.username=.*/target.channel.username=$1/" Kafka/Kafka-Settings/chat-channel.properties

sed -ir "s/^[#]*\s*channel=#.*/channel=#${1,,}/" Kafka/Kafka-Settings/chat-channel.properties