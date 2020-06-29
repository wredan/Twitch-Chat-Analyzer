package dev.danilo.tap

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark._
import org.apache.spark.sql.Row
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.sql.SparkSession
import com.vader.sentiment.analyzer.SentimentAnalyzer
import com.vader.sentiment.processor.TextProperties
import com.vader.sentiment.util.SentimentModifyingTokens
import org.apache.spark.rdd.RDD

/**
 * @author ${user.name}
 */
object App {
  
 var spark:SparkSession = null;
 
  def main(args: Array[String]) {
   
    val sparkConf = new SparkConf().setAppName("TwitchChatMessageSpark").setMaster("local[2]")
    sparkConf.set("es.index.auto.create", "true")
    this.spark = SparkSession.builder().config(sparkConf).getOrCreate()
    this.spark.sparkContext.setLogLevel("WARN")   
    
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "10.0.100.25:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "TwitchChatMessageSpark",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean))
    
    val streamingContext = new StreamingContext(spark.sparkContext, Seconds(5))
        
    val topics = Array("twitch")
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))
      
    
    stream.map(record => (record.key, record.value)).map(rdd => rdd._2)
                .foreachRDD(rdd => {
                  App.readValues(rdd)
                })   
    
    streamingContext.start()
		streamingContext.awaitTermination
  }
  
  def readValues(rdd: RDD[String]): Unit = {
		val dataset = this.spark.read.json(rdd);
		if (!dataset.isEmpty) {
		  dataset.show()
		  dataset.rdd.collect().foreach(f => getPrediction(f.getString(0)))
		}    
	} 
  
  def getPrediction(message: String ): Unit = {
    val sentimentAnalyzer = new SentimentAnalyzer(message);
    sentimentAnalyzer.analyze();
    val polarity = sentimentAnalyzer.getPolarity;
    println(polarity + " | " + message)
  }
}
