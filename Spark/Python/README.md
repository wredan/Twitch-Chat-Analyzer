# Spark Consumer for Sentiment Analysis
Risorse utili:
- [Kafka Integration](https://spark.apache.org/docs/2.1.0/streaming-kafka-0-8-integration.html)
- [Maven Spark streaming](https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-8-assembly_2.11)
- [Vader](https://github.com/cjhutto/vaderSentiment)

## Creazione Spark Consumer
Il consumer è composto da uno script python che utilizza moduli quali PySpark-2.4.6, Vader Sentiment Analysis e altri moduli di supporto. 

### PySpark
Viene utilizzato il modulo PySpark che permette di instaurare una connessione ad un Kafka topic per la lettura del contenuto di una partizione.
Esistono due modalità principali:
- Creazione di uno Stream;
- Creazione di un Direct Stream.

La sostanziale differenza tra i due è che il primo permette una connessione tramite Zookeeper o qualsiasi altro servizio di orchestrazione che si occuperà di esplorare i topics e restituire il lo streaming del topic richiesto. La seconda modalità effettua una richiesta direttamente ad un Kafka-Server che si occuperà di fornire lo streaming del topic.

la modalità utilizzata in questa repo è la creazione di uno Stream tramite Zookeper.

### Struttura del codice
#### SparkContext: 
```py
sc = SparkContext(appName="Twitch")
sc.setLogLevel("WARN")
```
si occupa di impostare un contesto relativamente ad opzioni e settings che verranno usati per generare uno StreamingContext;

#### StreamingContext: 
```py
ssc = StreamingContext(sc, TimeInSeconds)
```
è il contesto che verrà utilizzato per impostare una connessione, diretta o meno. Il secondo parametro si riferisce al timing in sec in cui lo script effettua un controllo e consuma tutti i messaggi dal last offset fino all'attuale offset della partizione.

#### KafkaUtils: 
```py
brokers="10.0.100.22:2181"
topic = "twitch"
kvs = KafkaUtils.createStream(ssc, brokers, "spark-streaming-consumer", {topic: 1})
```
è responsabile della creazione dello streaming vera e propria. Richiede uno StreamingContext, il nome dello Spark Consumer, un insieme di topic da cui consumare e infine, a seconda della modalita, un insieme di Zookeper Servers o un insieme di Kafka Servers verso cui effettuare una connessione.

#### Avvio Stream
utilizzando i metodi:
```py
ssc.start()
ssc.awaitTermination()
```
ci si pone in ascolto nel topic indicato e verranno consumati i messaggi inseriti, vengono restituiti sotto forma di RDD.

#### Consumo messaggi
```py
kvs.foreachRDD(get_messages)
```

### Sentiment Analysis con Vader


### Containerizzazione ed esecuzione

```sh
$ docker build --tag spark:consumer .
$ docker run -it --network tap-project_twitch --ip 10.0.100.42 -p 9092 --name twitch-spark spark:consumer 
```



