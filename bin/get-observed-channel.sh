#!/bin/bash
sed -rn 's/^target.channel.username=([^\n]+)$/\1/p' Kafka/Kafka-Settings/chat-channel.properties