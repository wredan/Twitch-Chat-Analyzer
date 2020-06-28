## Twitch Chat Analyzer
### Descrizione Obiettivo
Obiettivo principale di questo progetto è quello di fornire un tool utile a tenere traccia degli eventi legati alla propria live chat su twitch, principalmente sotto l'aspetto della Sentiment Analysis.

#### Descrizione problema
Le live chat di twitch, soprattutto se sono presenti molto spettatori, risultano davvero ostiche da seguire e moderare. Nascono infatti da diversi anni i ruoli dei 'Moderatori', persone a cui lo Streamer fa affidamento per evitare che la propria chat si trasformi in una giungla di scimmiette frustrate.
<p align="center"><img src="docs/img/monkey_typewriter.jpg" alt="Monkey Typewriter Meme :)" width="250"/></p>
Questo tool vuole fornire proprio a questi ultimi (o anche allo Streamer stesso) un aiuto nel tenere traccia dello stato delle interazioni tra lo Streamer e il proprio pubblico, facendo uso della Sentiment Analysis.

#### Sentiment Analysis
>Sentiment analysis (also known as opinion mining or emotion AI) refers to the use of natural language processing, text analysis, computational linguistics, and biometrics to systematically identify, extract, quantify, and study affective states and subjective information. Sentiment analysis is widely applied to voice of the customer materials such as reviews and survey responses, online and social media, and healthcare materials for applications that range from marketing to customer service to clinical medicine.

Source: [Wikipedia](https://en.wikipedia.org/wiki/Sentiment_analysis "Sentiment analysis")


### Tecnologie Utilizzate
Le tecnologie utilizzate per questo progetto sono:

- **Ingestion**: [Kafka Connect](https://docs.confluent.io/current/connect/index.html "Kafka Connect") with custom connector and [PircBotX](https://github.com/pircbotx/pircbotx "PircBotX")
- **Streaming**: [Apache Kafka](https://www.confluent.io/what-is-apache-kafka "Apache Kafka")
- **Processing**: [Spark Streaming](https://spark.apache.org/streaming/ "Spark Streaming"), [PySpark(2.4.6)](https://spark.apache.org/docs/2.4.6/library "PySpark(2.4.6)")
- **Machine Learning/Sentiment Analysis**: [Vader Sentiment Analysis](https://pypi.org/project/vaderSentiment/ "Vader Sentiment Analysis")

- **Indexing**: [ElasticSearch](https://www.elastic.co/what-is/elasticsearch "ElasticSearch")  
- **Visualization**: [Kibana](https://www.elastic.co/what-is/kibana "Kibana") 

### Struttura del Progetto

### Risorse utili

### Realizzato da