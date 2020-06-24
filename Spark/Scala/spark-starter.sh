/opt/spark/bin/spark-submit \
  --class dev.danilo.tap.App \
  --master local[2] \
	/opt/spark-twitch/spark-twitch-1.0-SNAPSHOT.jar
