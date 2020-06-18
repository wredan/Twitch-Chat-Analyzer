FROM openjdk:8
ENV PATH /opt/kafka/bin:$PATH
ENV KAFKA_DIR "/opt/kafka"
ENV ZOOKEEPER_SERVER "10.0.100.22:2181"

ARG KAFKA_VERSION="2.12-2.5.0"

RUN apt-get update

# Installing Kafka
# ADD will automatically extract the file
ADD kafka_${KAFKA_VERSION}.tgz /opt

# Create Sym Link 
RUN ln -s /opt/kafka_${KAFKA_VERSION} ${KAFKA_DIR} 

# Copy All conf here
COPY chat-channel.properties ${KAFKA_DIR}/conf/
COPY worker.properties ${KAFKA_DIR}/conf/
COPY twitch-kafka-connector-* /usr/local/share/kafka/plugins/
COPY kafka-starter.sh ${KAFKA_DIR}

WORKDIR ${KAFKA_DIR}
CMD ./kafka-starter.sh
