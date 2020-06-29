# Zookeeper
>ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services.

Source: [Zookeeper](https://zookeeper.apache.org/ "Zookeeper")

In this project, Zookeeper performs the centralized service function that maintains the configuration between Kafka, Kafka-connector and Spark.

## Setup

Zookeeper carries out its setup automatically for this project.

## Boot up process

You can run Zookeeper using this shell script in the main bin folder of this project.

```sh
$ bin/zookeeper-start.sh
```

Zookeeper will listen for events that it will record in its container space in the form of a file system. 

Make sure your container is running using:
```shell
$ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                                  NAMES
d3d6a16cc926        zookeeper:latest    "/docker-entrypoint.…"   21 seconds ago      Up 19 seconds       2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, 8080/tcp   twitch-zookeeper
```
To navigate the file system run the following commands in order:

Get into container bash
```shell
$ docker exec -it twitch-zookeeper bash
root@d3d6a16cc926:/apache-zookeeper-3.6.1-bin# bin/zkCli.sh
-
-
-
[zk: localhost:2181(CONNECTED) 0] ls /
[admin, brokers, cluster, config, consumers, controller, controller_epoch, isr_change_notification, latest_producer_id_block, log_dir_event_notification, zookeeper]
```
Just type help for a full command list. Enjoy

## Technical insights
- image: zookeeper
- container_name: "twitch-zookeeper"
- ipv4_address: "10.0.100.22"
- ports: 2181:2181
- network: twitch-chat-analyzer_twitch       