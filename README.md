# Custom Kafka Connector
Risorse utili:
- [Confluent Connect guide](https://docs.confluent.io/current/connect/devguide.html)
- [Github Repo kafka connect example](https://github.com/apache/kafka/tree/trunk/connect/file/src/main/java/org/apache/kafka/connect/file)
- [Connect installing plugins](https://docs.confluent.io/current/connect/userguide.html#connect-installing-plugins)

### Creazione risorsa (websocket, poll su API, BOT) per recuperare il flusso di dati.
Questa creazione è rimandata all'utente in quanto varia a seconda delle singole esigenze. 

### Creazione kafka connector (Source)
Il connettore si compone di due principali classi che estendono le seguenti: 
- SourceConnector
- SourceTask

La classe **SourceConnector** inizializza tramite il metodo **taskConfigs(int maxTasks)** ogni singolo task (nel nostro caso 1 solo). Inoltre si occupa di inizializare tramite **start(Map<String, String> props)** la risorsa che si occuperà di prelevare i dati ed inserirli in coda. Riceve inolte le proprietà dal file nome-mio-connettore.properties, si occuperà di farle presenti al task tramite il metodo **taskConfigs(int maxTasks)**.

La classe **SourceTask** si occupa del vero e prorio inserimento nell'unica partizione del topic creata. Tramite il metodo **poll()** essa inizializza un **List\<SourceRecord\> records** e finché la coda non risulta vuota, aggiunge alla lista dei SourceRecord (vedi lo schema nelle classi del progetto), infine ritorna la lista creata, i cui elementi verranno inseriti nella partizione del topic indicato dal SourceRecord.

Bisogna quindi settare due tipologie di properties:
- **nome-mio-connettore.properties**: si occupa di inizializzare proprietà inerenti al singolo connettore e inoltre alla risorsa utilizzata per prelevare i dati (vedi esempio nella repo).
- **worker.properties**: si occupa di definire proprietà generali dei connector.
        
### Creazione Uber/Fat Jar
Per creare l'uber jar da inserire nella cartella Kafka-Settings, si avvia una bash nella cartella contenente il progetto maven, quindi il pom.xml. Usando maven da console si inserisce il comando:
```sh
$ mvn package
```
Questo comando creerà l'uber jar nella cartella target all'interno del progetto. 

**Nota bene**: bisogna usare il jar con le dimensioni maggiori, solitamente privo del nome 'original'.
        
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

### Test con Kafka Console Consumer
Se tutto è stato eseguito correttamente è possibile testare il connettore tramite il kafka console consumer.
Nella cartella Kafka-test-consumer, effettuare:
```sh
$ docker build . --tag nameImage
$ docker run -it -network myNetwork --ip containerIpV4 -p containerPort nameImage
```
Il dockerfile si occupa di copiare lo script kafka-starter.sh e di avviare un consumatore collegandolo al server prelevato dai bootstrap.server (cambiare ip secondo la propria configurazione).
Se tutto è andato bene, si dovrebbero vedere i messaggi consumati e venir mostrati direttamente in bash.
