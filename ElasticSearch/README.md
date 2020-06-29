# Elastichsearch
>Elasticsearch is a distributed, open source search and analytics engine for all types of data, including textual, numerical, geospatial, structured, and unstructured. Elasticsearch is built on Apache Lucene and was first released in 2010 by Elasticsearch N.V. (now known as Elastic). Known for its simple REST APIs, distributed nature, speed, and scalability, Elasticsearch is the central component of the Elastic Stack, a set of open source tools for data ingestion, enrichment, storage, analysis, and visualization.

Source: [elasticsearch](https://www.elastic.co/what-is/elasticsearch "elasticsearch")

In this project, Elasticsearch plays the role of indexing data from Spark after they have been processed.

## Setup

Elasticsearch carries out its setup automatically for this project.

## Boot up process

You can get data directly from your browser because Elasticsearch use REST API.

## Technical insights
- image: docker.elastic.co/elasticsearch/elasticsearch:7.8.0
- container_name: "twitch-elasticsearch"
- ipv4_address: "10.0.100.51"
- ports: 9200:9200
- network: twitch-chat-analyzer_twitch        

## Useful query

- http://10.0.100.51:9200/_cat/indices?v