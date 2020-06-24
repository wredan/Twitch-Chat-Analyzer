# Spark Consumer for Sentiment Analysis
Risorse utili:
- [Kafka Integration](https://spark.apache.org/docs/2.1.0/streaming-kafka-0-8-integration.html)
- [Maven Spark streaming](https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-8-assembly_2.11)
- [Vader](https://github.com/cjhutto/vaderSentiment)

```sh
$ docker run -it --network tap-project_twitch --ip 10.0.100.42 -p 9092 --name twitch-spark spark:consumer 
```


### Creazione Spark Consumer
Il consumer: 
- SourceConnector
- SourceTask

La classe **SourceConnector** inizializza tramite il metodo **taskConfigs(int maxTasks)** ogni singolo task (nel nostro caso 1 solo). Inoltre si occupa di inizializare tramite **start(Map<String, String> props)** la risorsa che si occuperà di prelevare i dati ed inserirli in coda. Riceve inolte le proprietà dal file nome-mio-connettore.properties, si occuperà di farle presenti al task tramite il metodo **taskConfigs(int maxTasks)**.

La classe **SourceTask** si occupa del vero e prorio inserimento nell'unica partizione del topic creata. Tramite il metodo **poll()** essa inizializza un **List\<SourceRecord\> records** e finché la coda non risulta vuota, aggiunge alla lista dei SourceRecord (vedi lo schema nelle classi del progetto), infine ritorna la lista creata, i cui elementi verranno inseriti nella partizione del topic indicato dal SourceRecord.

Bisogna quindi settare due tipologie di properties:
- **nome-mio-connettore.properties**: si occupa di inizializzare proprietà inerenti al singolo connettore e inoltre alla risorsa utilizzata per prelevare i dati (vedi esempio nella repo).
- **worker.properties**: si occupa di definire proprietà generali dei connector.        
        
### Containerizzazione tramite docker
Il docker-compose.yml contiene la composizione dei container e della network da creare (controllare che gli indirizzi ip siano correttamente settati).

Sono presenti due container principali:
- **zookeper**: esso si occupa di orchestrare tutti gli elementi collegati a kafka, quali server, connectors, consumers e producers. Viene pullato e avviato automaticamente dal **docker-compose up**.
- **kafka-server**: esso è il container in cui vengono avviati gli script relativi al server e al connettore standalone.
- **kafka-consumer**(opzionale): serve come test iniziale per controllare se effettivamente funziona tutto, poi il consumatore sarà Spark.

L'avvio di zookeper e del kafka-server avviene sempre tramite il comando:
```sh
$ docker-compose up
```
Esso si occuperà di andare a prelevare il docker-compose.yml e seguire le sue direttive. 
Nello specifico, alcune direttive del Dockerfile del kafka-server sono:
- Settare la variabile di ambiente che imposta l'indirizzo ip e porta del server di zookeper;
- Copiare, nome-mio-connettore.properties, worker.properties, Uber/Fat Jar, kafka-starter.sh dentro il container;
- Impostare l'entrypoint sullo script kafka-starter.sh.

Una volta eseguito il container, viene eseguito **kafka-starter.sh** che si occupa di avviare il server broker che si connetterà a zookeper, di avviare lo script che farà partire il connettore.


