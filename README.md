# kafka-example-docker

This kafka example has a simple consumer and a simple producer for kafka in Java.

To use this example on MacOS you can use the following docker-compose:
author: Santthosh Selvadurai
github: https://github.com/santthosh

## Instructions:
### Kafka Docker for MacOS
1. Download Kafka Container for MacOS: https://github.com/santthosh/kafka-for-mac
```docker-compose up -d```

2. Add a mapping with your container ip to docker.for.mac.host.internal to your /etc/hosts file 
  e.g. 10.0.4.162 docker.for.mac.host.internal
  to check your container ip use:
  ```docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id```
  (!) your machine ip changes if you change network, update the config and restart the container

3. Connect to the kafka cluster via localhost:29092 (bootstrap server)

To create a new topic enter to the Kafka container and use:
```sh /usr/bin/kafka-topics --create --bootstrap-server localhost:29092 --replication-factor 1 --partitions 1 --topic test```
