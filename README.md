# Twitch Chat Analyzer
## Goal Description
The main objective of this project is to provide a useful tool for keeping track of events related to your live chat on Twitch, mainly for Sentiment Analysis.

### Sentiment Analysis
>Sentiment analysis (also known as opinion mining or emotion AI) refers to the use of natural language processing, text analysis, computational linguistics, and biometrics to systematically identify, extract, quantify, and study affective states and subjective information. Sentiment analysis is widely applied to voice of the customer materials such as reviews and survey responses, online and social media, and healthcare materials for applications that range from marketing to customer service to clinical medicine.

Source: [Wikipedia](https://en.wikipedia.org/wiki/Sentiment_analysis "Sentiment analysis")

### Problem description
Live Twitch chats, especially if there are a lot of spectators, are really difficult to follow and moderate. The roles of the 'Moderators' have been born for several years, people to whom the Streamer relies to prevent their chat from becoming a jungle of frustrated monkeys.

This tool aims to help moderators and streamers keeping track of the interactions between the streamer and its audience, making use of Sentiment Analysis.

## Data Pipeline Technologies

- **Ingestion**: [Kafka Connect](https://docs.confluent.io/current/connect/index.html "Kafka Connect") with custom connector and [PircBotX](https://github.com/pircbotx/pircbotx "PircBotX")
- **Streaming**: [Apache Kafka](https://www.confluent.io/what-is-apache-kafka "Apache Kafka")
- **Processing**: [Spark Streaming](https://spark.apache.org/streaming/ "Spark Streaming"), [Spark SQL](https://spark.apache.org/sql/ "Spark SQL"), [PySpark(2.4.6)](https://spark.apache.org/docs/2.4.6/library "PySpark(2.4.6)")
- **Machine Learning/Sentiment Analysis**: [Vader Sentiment Analysis](https://pypi.org/project/vaderSentiment/ "Vader Sentiment Analysis")

- **Indexing**: [ElasticSearch](https://www.elastic.co/what-is/elasticsearch "ElasticSearch")  
- **Visualization**: [Kibana](https://www.elastic.co/what-is/kibana "Kibana") 

**Containerization**: [Docker]( https://www.docker.com "Docker")

## Project structure
<p align="center"><img src="docs/img/twitch_chat _analyzer_workflow.svg" alt="workflow" width="800"/></p>

The project workflow follows the structure above.
### In brief
1. The bot created by the PircBotX interface receives the messages sent in a chat selected by the user through the IRC protocol ([Internet Relay Chat](https://en.wikipedia.org/wiki/Internet_Relay_Chat "IRC")). 
2. A JSON is built with data and metadata provided by the bot. It is inserted into a Message Queue from which the connector structure picks them up and inserts them into the Kafka-topic: twitch. 
3. From there they are taken via a python script from the Spark Steaming interface. 
4. Spark SQL reconstructs the JSON, it consumes the message, making a Dataframe available. Spark SQL also communicates with the Vader Sentiment Analysis library which provides a result on the analysis of the message. 
5. "sentiment" field is added with the result obtained by Vader's elaboration, reported among one of the following classes: very_positive, positive_opinion, neutral_opinion, negative_opinion, very_negative, ironic. 
6. The newly built RDD is indexed through the product of the elastic family, Elasticsearch. 
7. Kibana (another elastic tool) deals with aggregating and placing metrics making data available through a user interface.

## Technical insights
There is doc file similar to this in each folder to get information for each specific component.

## Boot up process
In the /bin folder there are shell scripts that allow you to start the following project.

**N.B.** This project uses Docker as a containerization tool. Make sure you have it installed. Look online to understand how to install it in your system.

**N.B.** when files are downloaded to Linux machines, many versions remove execution permission for security reason, to add it to all sh files in this project folder, run:

```shell
$ cd path_to_cloned_repo
$ find ./ -type f -iname "*.sh" -exec chmod +x {} \;
```
In the [Kafka/Kafka-Settings](https://github.com/Warcreed/Tap-Project/tree/master/Kafka/Kafka-Settings "Kafka-Settings") folder, rename **chat-channel.properties.dist** to **chat-channel.properties** and set all parameters required by Twitch connection, instructions in the same file. Once set up, continue.

### All in one solution

In the bin folder, start the following script:
```shell
$ bin/docker-compose.sh
```
It starts components such as Zookeeper, Kafka, Elastisearch, and Kibana. When all the components are started, run the following command and follow the instructions on the screen:
```shell
$ bin/spark-consumer-start.sh
```
When Spark starts, follow the instructions on the screen and choose **Python**.

### Long solution, start the machines individually

In the bin folder, start **in order** the following scripts, in **different bash**:
```shell
$ bin/create-network.sh
~~~ Wait until end logging ~~~

$ bin/zookeeper-start.sh
~~~ Wait until end logging ~~~

$ bin/kafka-start.sh
~~~ Wait until end logging ~~~

$ bin/elasticsearch-start.sh
~~~ Wait until end logging ~~~

$ bin/kibana-start.sh
~~~ Wait until end logging ~~~

$ bin/spark-consumer-start.sh
~~~ Wait until end logging ~~~
```
It will start individual components such as Zookeeper, Kafka, Elastisearch, Kibana.
When Spark starts, follow the instructions on the screen and choose **Python**.

To stop all running container, just ctrl + C in their own shell.

### Almost done
In the browser, enter the following address:  http://10.0.100.52:5601 .
To set up Kibana, see its guide in the [Kibana folder](https://github.com/Warcreed/Tap-Project/tree/master/Kibana "Kibana").

## Developed by
[Danilo Santitto](https://github.com/Warcreed "Warcreed")