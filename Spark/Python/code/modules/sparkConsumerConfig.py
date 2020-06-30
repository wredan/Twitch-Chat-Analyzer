# -*- coding: UTF-8 -*-

# set streamer nationality
twitch_streamer_nationality = "en"

# Spark-Kafka Settings
bootstrap_server="10.0.100.22:2181"
topic = "twitch"
app_name = "Twitch-Spark"
groupId = "spark-streaming-consumer"
log_level = "ERROR"
window = 5 # Sec

kafka_params = {
    "auto.offset.reset": "smallest",
}

message_log = False

# ElasticSearch Settings
elastic_host="10.0.100.51"
elastic_host_port="9200"
elastic_index="twitch"
elastic_document="_doc"

es_write_conf = {
"es.nodes" : elastic_host,
"es.port" : elastic_host_port,
"es.resource" : '%s/%s' % (elastic_index,elastic_document),
"es.input.json" : "yes",
"mapred.reduce.tasks.speculative.execution": "false",
"mapred.map.tasks.speculative.execution": "false",
"es.mapping.id": "timestamp"
}

mapping = {
    "mappings": {
        "properties": {
            "timestamp": {
                "type": "date"
            }
        }
    }
}

# sentiment_class ={
#     'molto_positiva' : 0,
#     'opinione_positiva' : 1,
#     'opinione_neutra' : 3,
# }
