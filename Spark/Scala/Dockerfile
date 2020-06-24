FROM maven:latest

ENV SPARK_VERSION=3.0.0
ENV SPARK_DIR=/opt/spark
ENV PATH $SPARK_DIR/bin:$PATH
ENV TWITCH_SPARK_STREAMING "spark-twitch"
ENV TWITCH_SPARK_DIR "/opt/spark-twitch/"

ADD spark-${SPARK_VERSION}-bin-hadoop2.7.tgz /opt
RUN ln -s /opt/spark-${SPARK_VERSION}-bin-hadoop2.7 ${SPARK_DIR} 

# COPY ${TWITCH_SPARK_STREAMING} ${TWITCH_SPARK_DIR}
# WORKDIR ${TWITCH_SPARK_DIR}
# RUN mvn package
# RUN cp target/spark-twitch-* ${TWITCH_SPARK_DIR}

COPY ${TWITCH_SPARK_STREAMING}/target/spark-twitch-* ${TWITCH_SPARK_DIR}
COPY spark-starter.sh ${TWITCH_SPARK_DIR}
WORKDIR ${TWITCH_SPARK_DIR}
CMD ./spark-starter.sh
