## Elastichsearch
>Elasticsearch is a distributed, open source search and analytics engine for all types of data, including textual, numerical, geospatial, structured, and unstructured. Elasticsearch is built on Apache Lucene and was first released in 2010 by Elasticsearch N.V. (now known as Elastic). Known for its simple REST APIs, distributed nature, speed, and scalability, Elasticsearch is the central component of the Elastic Stack, a set of open source tools for data ingestion, enrichment, storage, analysis, and visualization.

Source: [elasticsearch](https://www.elastic.co/what-is/elasticsearch "elasticsearch")

In this project, Elasticsearch plays the role of indexing data from Spark after they have been processed.

### Setup

Zookeeper carries out its setup automatically for this project.

### Technical insights
- image: zookeeper
- container_name: "twitch-zookeeper"
- ipv4_address: "10.0.100.22"
- ports: 2181:2181
- network: folderName_twitch       

http://10.0.100.51:9200/_cat/indices?v