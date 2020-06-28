
FROM maven:latest
ENV PATH /opt/kafka/bin:$PATH
ENV KAFKA_DIR "/opt/kafka"
ENV ZOOKEEPER_SERVER "10.0.100.22:2181"
ENV TWITCH_CONNECTOR "Kafka-Connector"
ENV TWITCH_SETTINGS "Kafka-Settings"
ENV TWITCH_CONNECT_DIR "/opt/connector"
ENV KAFKA_PLUGINS_DIR "/usr/local/share/kafka/plugins/"

ARG KAFKA_VERSION="2.12-2.5.0"

# Installing Kafka
ADD ${TWITCH_SETTINGS}/kafka_${KAFKA_VERSION}.tgz /opt

# Create Sym Link 
RUN ln -s /opt/kafka_${KAFKA_VERSION} ${KAFKA_DIR} 

#Copy properties
COPY ${TWITCH_SETTINGS}/chat-channel.properties ${KAFKA_DIR}/conf/
COPY ${TWITCH_SETTINGS}/worker.properties ${KAFKA_DIR}/conf/

# mvn package
# COPY ${TWITCH_CONNECTOR}/ ${TWITCH_CONNECT_DIR}/
# WORKDIR ${TWITCH_CONNECT_DIR}/
# RUN mvn package
# RUN mkdir -p ${KAFKA_PLUGINS_DIR}
# RUN cp target/twitch-kafka-connector-* ${KAFKA_PLUGINS_DIR}

COPY ${TWITCH_SETTINGS}/twitch-kafka-connector-0.0.1-SNAPSHOT.jar ${KAFKA_PLUGINS_DIR}

COPY ${TWITCH_SETTINGS}/kafka-starter.sh ${KAFKA_DIR}

WORKDIR ${KAFKA_DIR}
CMD ./kafka-starter.sh