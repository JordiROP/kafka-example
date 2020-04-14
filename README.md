# kafka-example-docker

This kafka example has a simple consumer and a simple producer for kafka in Java.

## Disclaimer
I am not the creator of this docker-compose for MacOS the creator is mentionet below:

* **Author: Santthosh Selvadurai**

* **Github: https://github.com/santthosh**

## Instructions:
### Kafka Docker for MacOS
1. Download Kafka Container for MacOS: https://github.com/santthosh/kafka-for-mac
```
docker-compose up -d
```

In case of the link is unavailable you can find it also inside the resources folder inside the project

2. Add a mapping with your container ip to docker.for.mac.host.internal to your /etc/hosts file 
  
    e.g. 10.0.4.162 docker.for.mac.host.internal
  
    to check your ip use:
  ```
  ifconfig | grep -e "inet "
  ```

3. Connect to the kafka cluster via localhost:29092 (bootstrap server)

To create a new topic enter to the Kafka container and use:
```
sh /usr/bin/kafka-topics --create --bootstrap-server localhost:29092 --replication-factor 1 --partitions 1 --topic test
```
