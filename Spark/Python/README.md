# Spark Consumer for Sentiment Analysis

Python > 3.4 is required. Python 3.7 is used by this container.

## Spark Consumer creation
Il consumer è composto da uno script The consumer is made up of a python scripts that uses modules such as PySpark-2.4.6, Vader Sentiment Analysis and other support modules. che utilizza moduli quali PySpark-2.4.6, Vader Sentiment Analysis e altri moduli di supporto. 

### PySpark
The PySpark module allows you to establish a connection to a Kafka topic for reading the content of a partition.
There are two main ways:
- Creating a **Stream**;
- Creating a **Direct Stream**.

The difference between the two is that the first one allows a connection via Zookeeper or any other orchestration service that will explore the topics and return the streaming of the requested topic. The second mode makes a request directly to a Kafka-Server that will take care of streaming the topic.

Creating a **Stream** is the method used here.

### Code Style

Currently, code is splitted up into 3 python files:
- `sparkConsumer.py` is main file who takes care about Spark Streaming - Kafka connection, Spark SQL and Vader Processing, Elasticsearch indexing.
- `sparkConsumerConfig.py` keeps all start up config.
- `sentimentAnalysis.py` keeps all sentiment analysis methods.

#### Spark-Kafka: 
`SparkSession` takes care of setting a context relating to options and settings that will be used to generate a StreamingContext.
```py
global spark
spark = SparkSession.builder.appName(config.app_name).getOrCreate()
spark.sparkContext.setLogLevel(config.log_level)
```
`StreamingContext` is the context that will be used to set up a connection, direct or not. The second parameter refers to the timing in sec where the script checks and consumes all the messages from the last offset to the current partition offset.

```py
ssc = StreamingContext(spark.sparkContext, 5)
```
`createStream()` initializes the connection parameters to the kafka interface. `foreachRDD()` perform actual operation on incoming data.

```py
stream = KafkaUtils.createStream(ssc, config.brokers, config.groupId, {config.topic: 1}, config.kafka_params)
stream.foreachRDD(get_messages)
ssc.start()
ssc.awaitTermination()
```

`get_messages()` function get JSON from rdd values while `get_item_structure()` is where the final rdd structure is made up. `saveAsNewAPIHadoopFile()` is the elasticsearch api interface to get and index data from spark.

```py
def get_item_structure(item): 
    if config.twitch_streamer_nationality == "en":   
        result = sentiment.get_sentiment_analysis_en(item['message'])
    return {
            'targetChannelUsername': item['targetChannelUsername'],
            'nickname': item['nickname'],
            'message': item['message'],
            'sentiment': result,
            'timestamp': int(item['timestamp'])
        }

def get_messages(key,rdd):   
    message_dataframe = spark.read.json(rdd.map(lambda value: json.loads(value[1])))      
    if not message_dataframe.rdd.isEmpty():
        analyzed_rdd = message_dataframe.rdd.map(lambda item: get_item_structure(item))
        elastic_rdd = analyzed_rdd.map(lambda item: json.dumps(item)).map(lambda x: ('', x))

        elastic_rdd.saveAsNewAPIHadoopFile(
            path='-',
            outputFormatClass="org.elasticsearch.hadoop.mr.EsOutputFormat",
            keyClass="org.apache.hadoop.io.NullWritable",
            valueClass="org.elasticsearch.hadoop.mr.LinkedMapWritable",
            conf=config.es_write_conf) 
```
### Vader Sentiment Analysis
`get_sentiment_analysis_en()` function above return a sentiment class between: very_positive, positive_opinion, neutral_opinion, negative_opinion, very_negative, ironic.
It still need some work to find the correct balance for each vader values.
Basically vader get back 4 values:
- compound is used to mainly get if the phrase is positive (> 0.05), neutral(-0.05 < x < 0.05) or negative (< -0.05).
- pos, neg, neu are more specific values where you can perform your custom control (see code [here](https://github.com/Warcreed/Twitch-Chat-Analyzer/blob/master/Spark/Python/code/sentimentAnalysis.py) for more details).

## Setup

You can change setup in `sparkConsumerConfig.py` files.

## Boot up process

You can run Spark Python Consumer using this shell script in the main bin folder of this project, selecting Python option (1).

```sh
$ bin/spark-consumer-start.sh
```

## Technical insights
- image: twitch_python:spark
- container_name:  twitch-python-spark
- ipv4_address: 10.0.100.42
- ports: 9092
- network: twitch-chat-analyzer_twitch

Useful resources:
- [Kafka Integration](https://spark.apache.org/docs/2.1.0/streaming-kafka-0-8-integration.html)
- [Maven Spark streaming](https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-8-assembly_2.11)
- [Vader](https://github.com/cjhutto/vaderSentiment)
- [Elastisearch python](https://www.bmc.com/blogs/write-apache-spark-elasticsearch-python/)
- [Elastisearch integration](https://starsift.com/2018/01/18/integrating-pyspark-and-elasticsearch/)



