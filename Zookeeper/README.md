## Zookeeper
>ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services.

Source: [Zookeeper](https://zookeeper.apache.org/ "Zookeeper")

In this project, Zookeeper performs the centralized service function that maintains the configuration between Kafka, Kafka-connector and Spark.

### Setup

Zookeeper carries out its setup automatically for this project.

### Boot up process

You can run Zookeeper using this shell script in the main bin folder of this project.

```sh
$ bin/zookeeper-start.sh
```

Zookeeper will listen for events that it will record in its container space in the form of a file system. To navigate the file system run the following commands in order:

### Technical insights
- image: zookeeper
- container_name: "twitch-zookeeper"
- ipv4_address: "10.0.100.22"
- ports: 2181:2181
- network: folderName_twitch       