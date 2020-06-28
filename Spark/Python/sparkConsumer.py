# -*- coding: UTF-8 -*-
import pyspark
from pyspark import SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils
from pyspark.sql import SparkSession
from pyspark.sql.dataframe import DataFrame
from pyspark.sql import functions as F
from pyspark.sql import Row
from elasticsearch import Elasticsearch

import pandas as pd
import json
import sparkConsumerConfig as config
import sentimentAnalysis as sentiment

# Spark-Kafka
spark = SparkSession.builder.appName(config.app_name).getOrCreate()
spark.sparkContext.setLogLevel(config.log_level)
ssc = StreamingContext(spark.sparkContext, 5)
stream = KafkaUtils.createStream(ssc, config.brokers, config.groupId, {config.topic: 1}, config.kafka_params)

# ElasticSearch
elastic = Elasticsearch(hosts=[config.elastic_host])

response = elastic.indices.create(
    index=config.elastic_index,
    body=config.mapping,
    ignore=400
)
# elasticsearch index response
if 'acknowledged' in response:
    if response['acknowledged'] == True:
        print ("INDEX MAPPING SUCCESS FOR INDEX:", response['index'])
elif 'error' in response:
    print ("ERROR:", response['error']['root_cause'])
    print ("TYPE:", response['error']['type'])


def get_item_structure(item): 
    if config.twitch_streamer_nationality == "en":   
        result = sentiment.get_sentiment_analysis_en(item['message'])
    # elif twitch_streamer_nationality == "it":
    #     result = get_sentiment_analysis_ita(item['message'])
    return {
            'targetChannelUsername': item['targetChannelUsername'],
            'userId': item['userId'],
            'nickname': item['nickname'],
            'message': item['message'],
            'sentiment': result,
            'timestamp': int(item['timestamp'])
        }

def get_messages(key,rdd):   
    message_dataframe = spark.read.json(rdd.map(lambda value: json.loads(value[1])))      
    if not message_dataframe.rdd.isEmpty():
        analyzed_rdd = message_dataframe.rdd.map(lambda item: get_item_structure(item))
        if config.message_log:
            print("********************") 
            print(spark.createDataFrame(analyzed_rdd).show(20, False))  
            print("********************\n")
        elastic_rdd = analyzed_rdd.map(lambda item: json.dumps(item)).map(lambda x: ('key', x))

        elastic_rdd.saveAsNewAPIHadoopFile(
            path='-',
            outputFormatClass="org.elasticsearch.hadoop.mr.EsOutputFormat",
            keyClass="org.apache.hadoop.io.NullWritable",
            valueClass="org.elasticsearch.hadoop.mr.LinkedMapWritable",
            conf=config.es_write_conf)   

stream.foreachRDD(get_messages)

ssc.start()
ssc.awaitTermination()

