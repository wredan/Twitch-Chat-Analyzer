FROM openjdk:8
ENV PATH /opt/kafka/bin:$PATH
ENV KAFKA_DIR "/opt/kafka"

ARG KAFKA_VERSION="2.12-2.5.0"

RUN apt-get update

# Installing Kafka
# ADD will automatically extract the file
ADD ../Kafka-Settings/kafka_${KAFKA_VERSION}.tgz /opt

# Create Sym Link 
RUN ln -s /opt/kafka_${KAFKA_VERSION} ${KAFKA_DIR} 

# Copy All conf here
COPY kafka-starter.sh ${KAFKA_DIR}

WORKDIR ${KAFKA_DIR}
CMD ./kafka-starter.sh
