import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class StreamsConsumerKafka {
    private final StreamsPropertiesLoader propertiesLoader = StreamsPropertiesLoader.getPropertiesLoaderInstance();
    Logger log = LoggerFactory.getLogger(ProducerKafka.class);

    public static void main(String ... args) {
        StreamsConsumerKafka consumerKafka = new StreamsConsumerKafka();
        consumerKafka.runConsumer();
    }

    private void runConsumer() {
        try (Consumer<Long, String> consumer = this.createConsumer()) {
            consumer.subscribe(Collections.singletonList(propertiesLoader.getProperty("streams.topic.output.name")));
            while (true) {
                // We poll records from the kafka during 2 seconds after that we will proceed to print them
                // this will be done indefinitely so we can produce and consume at the same time
                ConsumerRecords<Long, String> records = consumer.poll(Duration.ofSeconds(2));
                for (ConsumerRecord<Long, String> record : records) {
                    System.out.printf("topic = %s, partition = %s, offset = %d, Key = %s, Value = %s\n",
                            record.topic(), record.partition(), record.offset(),
                            record.key(), record.value());
                }
                // Commits offsets to kafka Non-Blocking
                consumer.commitAsync();
            }
        }
    }

    private Consumer<Long, String> createConsumer() {
        // Creation of a Kafka consumer with its properties
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesLoader.getProperty("streams.brokers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, propertiesLoader.getProperty("streams.group.id"));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, propertiesLoader.getProperty("streams.offset.reset.earliest"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        return new KafkaConsumer<>(props);
    }
}
