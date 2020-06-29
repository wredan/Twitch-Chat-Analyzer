# Spark Consumer for Sentiment Analysis

## What is Spark

Spark is a unified analysis engine for large data processing. It can analyze large volumes of data, using distributed systems. it is really fast.

## Spark Streaming

Apache Spark Streaming is a scalable fault-tolerant streaming processing system that natively supports both batch and streaming workloads. Spark Streaming is an extension of the core Spark API that allows data engineers and data scientists to process real-time data from various sources including (but not limited to) Kafka, Flume, and Amazon Kinesis. This processed data can be pushed out to file systems, databases, and live dashboards.

## Spark SQL

Spark SQL brings native support for SQL to Spark and streamlines the process of querying data stored both in RDDs (Spark’s distributed datasets) and in external sources. 

The basic concept behind Spark is related to the Resilient Distributed Datasets (RDD). It is the data structure used by Spark. RDD is fault-tolerant. RDD is not typed. It is distributed and partitioned so that data can be processed in parallel.

## Vader Sentiment Analysis

Sentiment Analysis, or Opinion Mining, is a sub-field of Natural Language Processing (NLP) that tries to identify and extract opinions within a given text.

VADER (Valence Aware Dictionary and sEntiment Reasoner) is a lexicon and rule-based sentiment analysis tool that is specifically attuned to sentiments expressed in social media. A **lexicon** is a list of lexical features (e.g., words) which are generally labelled according to their semantic orientation as either positive or negative.
- It works exceedingly well on social media type text, yet readily generalizes to multiple domains.
- It **doesn’t require any training data** but is constructed from a generalizable, valence-based, human-curated gold standard sentiment lexicon.
- It is fast enough to be used online with streaming data.
- It does not severely suffer from a speed-performance tradeoff.


## Python Solution

Python solution can be found [here](https://github.com/Warcreed/Twitch-Chat-Analyzer/tree/master/Spark/Python)

## Scala Solution (currently working)

Scala solution can be found [here](https://github.com/Warcreed/Twitch-Chat-Analyzer/tree/master/Spark/Scala)