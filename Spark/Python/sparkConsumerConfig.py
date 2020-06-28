# -*- coding: UTF-8 -*-

# set streamer nationality
twitch_streamer_nationality = "en"

# Spark-Kafka Settings
brokers="10.0.100.22:2181"
topic = "twitch"
app_name = "Twitch-Spark"
groupId = "spark-streaming-consumer"
log_level = "ERROR"

kafka_params = {
    "auto.offset.reset": "latest",
    "enable.auto.commit": False
}

message_log = True

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

# set your custom emote value to use for sentiment analysis. Keep in mind to set a balanced values for each emote.
twitch_emotes = {
    '<3': 0.4,
    '4head': 1,
    'babyrage': -0.7,
    'biblethump': -0.7,
    'blessrng': 0.3,
    'bloodtrail': 0.7,
    'coolstorybob': -1,
    'residentsleeper': -1,
    'kappa': 0.3,
    'lul': -0.3,
    'pogchamp': 1.5,
    'heyguys': 1,
    'wutface': -1.5,
    'kreygasm': 1,
    'seemsgood': 0.7,
    'kappapride': 0.7,
    'feelsgoodman': 1,
    'notlikethis': -1
}

