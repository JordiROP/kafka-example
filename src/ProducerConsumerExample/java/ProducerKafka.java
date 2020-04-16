import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerKafka {
    private final PropertiesLoader propertiesLoader = PropertiesLoader.getPropertiesLoaderInstance();
    Logger log = LoggerFactory.getLogger(ProducerKafka.class);
    public static void main(String ... args) {
        ProducerKafka producerKafka = new ProducerKafka();
        producerKafka.runProducer();
    }

    private void runProducer() {

        Producer<Long, String> kafkaProd = this.createProducer();
        for (int index = 0; index < 1000; index++) {

            // This will generate a record that will be sent to the kafka topic
            ProducerRecord<Long, String> record = new ProducerRecord<>(
                    propertiesLoader.getProperty("kafka.topic.name"),
                    "This is record " + index);
            try {
                // We send the record, the function returns metadata with what we have sent the partition and offset
                RecordMetadata metadata = kafkaProd.send(record).get();
                System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
            }
            catch (ExecutionException | InterruptedException e) {
                System.out.println("Error in sending record");
                System.out.println(e);
            }
        }
    }

    private Producer<Long, String> createProducer() {
        // Set up the configurations for the producer
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesLoader.getProperty("kafka.brokers"));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, propertiesLoader.getProperty("kafka.client.id"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
}
