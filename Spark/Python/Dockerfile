FROM openjdk:8

ENV SPARK_VERSION "2.4.6"
ENV SPARK_DIR "/opt/spark"
ENV PATH $SPARK_DIR/bin:$PATH
ENV PATH "/root/.local/bin:$PATH"

RUN apt-get update && apt-get -y install python3.7 python3-pip wget

RUN pip3 install \
    pyspark==2.4.6 \
    elasticsearch \
    vadersentiment\
    pandas    

ENV PYSPARK_DRIVER_PYTHON python3.7
ENV PYSPARK_PYTHON python3.7

# Get Spark
# WORKDIR /opt
# RUN wget https://downloads.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop2.7.tgz && \
#     tar -zxvf spark-${SPARK_VERSION}-bin-hadoop2.7.tgz && \
#     rm -f spark-${SPARK_VERSION}-bin-hadoop2.7.tgz

ADD spark-${SPARK_VERSION}-bin-hadoop2.7.tgz /opt

# Create Sym Link 
RUN ln -s /opt/spark-${SPARK_VERSION}-bin-hadoop2.7 ${SPARK_DIR} 

COPY elasticsearch-hadoop-7.8.0.jar ${SPARK_DIR}/

# Add Python Code
# ADD sentita.tgz /opt/python
# WORKDIR /opt/python/sentita
# RUN pip3 install . tensorflow
# RUN python3 -m spacy download it
# RUN pip3 install tensorflow=2.2

COPY sparkConsumer.py  ${SPARK_DIR}/code/
COPY sparkConsumerConfig.py  ${SPARK_DIR}/code/
COPY sentimentAnalysis.py  ${SPARK_DIR}/code/

# Add Spark Starter
COPY spark-starter.sh $SPARK_DIR/bin/

WORKDIR ${SPARK_DIR}/bin
CMD ./spark-starter.sh
