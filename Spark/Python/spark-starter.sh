#/bin/bash
./spark-submit --packages org.apache.spark:spark-streaming-kafka-0-8_2.11:2.4.6 --jars /opt/spark/elasticsearch-hadoop-7.8.0.jar --py-files /opt/tap/code/modules.zip /opt/tap/code/sparkConsumer.py
