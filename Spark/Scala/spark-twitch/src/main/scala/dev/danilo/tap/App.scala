package dev.danilo.tap

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.sql.SparkSession

/**
 * @author ${user.name}
 */
object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("TwitchChatMessageSpark").setMaster("local[2]");
    sparkConf.set("es.index.auto.create", "true");
    val spark = SparkSession.builder().config(sparkConf).getOrCreate();
    spark.sparkContext.setLogLevel("WARN");    
    
    val streamingContext = new StreamingContext(spark.sparkContext, Seconds(1))
    
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "10.0.100.25:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "TwitchChatMessageSpark",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean))

    val topics = Array("twitch")
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))
      
    stream.map(record => (record.key, record.value)).map(rdd => rdd._2)
                .foreachRDD(rdd => {
                  spark.read.json(rdd).show()
                });    
  }
}
