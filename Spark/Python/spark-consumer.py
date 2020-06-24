# -*- coding: UTF-8 -*-
import pyspark
from pyspark import SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils

import pandas as pd
import json
from googletrans import Translator
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
import sparkConsumerConfig as config

translator = Translator()

analysis = SentimentIntensityAnalyzer()

analysis.lexicon.update(config.twitch_emotes)

def get_messages(key,rdd):
   
    # def f(x): print(json.loads(x[1]))
    # fore = rdd.foreach(f)
    message=rdd.map(lambda value: json.loads(value[1]))
    json_obj = message.collect()
    perform_sentiment_analysis_on_json(json_obj)
    # if not text:
    #     get_sentiment_analysis(text)
  
def perform_sentiment_analysis_on_json(json_obj):
    print("JSON: " + str(json_obj))
    for obj in json_obj:
        print("********************")
        print("Canale: " + obj["targetChannelUsername"])
        print("Utente: " + obj["nickname"])
        print("Messaggio: " + obj["message"])
        get_sentiment_analysis(obj["message"])
        print("********************\n")

def get_sentiment_analysis(phrase):    
    #english    
    if config.twitch_streamer_nationality == "en":
        polarity = analysis.polarity_scores(phrase)
    else:
    #other language
        try:
            trans = translator.translate(phrase, dest='en').text
            polarity = analysis.polarity_scores(trans)      
            print("Tradotto: " + trans)     
        except:
            polarity = analysis.polarity_scores(phrase)
        df2 = pd.DataFrame(list(polarity.items()), columns=['Sentiment Metric', 'Score'])
        print(df2)
        if polarity["compound"] >= 0.05:
            if polarity['pos'] - polarity["neu"] >= 0.2:
                print("La frase è molto positiva")
            elif 0 <= polarity['pos'] - polarity["neu"] < 0.2:
                if polarity['neg'] > 0.1:
                    print("La frase è ironica")
                else:
                    print("La frase esprime una opinione positiva")
            elif polarity['pos'] - polarity["neg"] < 0.2 and polarity['pos'] > 0.15:
                print("La frase esprime una opinione positiva")
            else:
                print("La frase esprime una opinione neutra")
        elif polarity["compound"] <= -0.05:
            if polarity['neg'] - polarity["neu"] >= 0.2:
                print("La frase è molto negativa")
            elif 0 <= polarity['neg'] - polarity["neu"] < 0.2:
                if polarity['pos'] > 0.1:
                    print("La frase è ironica")
                else:
                    print("La frase esprime una opinione negativa")
            elif polarity['neu'] - polarity["neg"] < 0.2:
                print("La frase esprime una opinione negativa")
            else:
                print("La frase esprime una opinione neutra")
        else:
            if polarity["pos"] > 0 and polarity["neg"] > 0:
                print("La frase è ironica")
            elif polarity['neu'] - polarity["pos"] < 0.2:
                print("La frase esprime una opinione positiva")
            elif polarity['neu'] - polarity["neg"] < 0.2:
                print("La frase esprime una opinione negativa")
            else:
                print("La frase esprime una opinione neutra")

brokers="10.0.100.22:2181"
topic = "twitch"

sc = SparkContext(appName="Twitch")
sc.setLogLevel("WARN")
ssc = StreamingContext(sc, 10)

kvs = KafkaUtils.createStream(ssc, brokers, "spark-streaming-consumer", {topic: 1})

kvs.foreachRDD(get_messages)

ssc.start()
ssc.awaitTermination()
