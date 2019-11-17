import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerKafka {
    Logger log = LoggerFactory.getLogger(ProducerKafka.class);

    public static void main(String ... args) {
        ConsumerKafka consumerKafka = new ConsumerKafka();
        consumerKafka.runConsumer();
    }

    private void runConsumer() {
        try (Consumer<Long, String> consumer = this.createConsumer()) {
            consumer.subscribe(Collections.singletonList(KafkaConstants.TOPIC_NAME));
            while (true) {
                ConsumerRecords<Long, String> records = consumer.poll(Duration.ofSeconds(2));
                for (ConsumerRecord<Long, String> record : records) {
                    System.out.printf("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n",
                            record.topic(), record.partition(), record.offset(),
                            record.key(), record.value());
                }
                consumer.commitAsync();
            }
        }
    }

    private Consumer<Long, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConstants.OFFSET_RESET_EARLIER);
        return new KafkaConsumer<>(props);
    }
}
