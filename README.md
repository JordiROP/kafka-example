# kafka-example

This repo has a simple Kafka Producer and consumer and a Kafka Stream wordcount example

## Disclaimer
The content on the resources is property of Apache, you can find the Kafka server here

* **Author: Apache Kafka**

* **Download: https://www.apache.org/dyn/closer.cgi?path=/kafka/2.5.0/kafka_2.12-2.5.0.tgz**

## Instructions:
1. Download Kafka : https://www.apache.org/dyn/closer.cgi?path=/kafka/2.5.0/kafka_2.12-2.5.0.tgz

2. Start the Zookeeper and the Kafka Server

```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
```
bin/kafka-server-start.sh config/server.properties
```

3. Create the required topics
* ConsumerProducerExampleTopic
```
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 \
    --replication-factor 1  --partitions 1 --topic ConsumerProducerExampleTopic
```
* StreamsPlaintextOutput
```
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 \
    --replication-factor 1  --partitions 1 --topic StreamsPlaintextOutput
```

