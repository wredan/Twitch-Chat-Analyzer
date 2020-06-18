bin/kafka-server-start.sh config/server.properties --override zookeeper.connect=$ZOOKEEPER_SERVER &
sleep 5s
bin/connect-standalone.sh conf/worker.properties conf/chat-channel.properties

