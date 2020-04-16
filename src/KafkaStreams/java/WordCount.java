import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class WordCount {
    private static final StreamsPropertiesLoader propertiesLoader = StreamsPropertiesLoader.getPropertiesLoaderInstance();

    public static void main(String[] args) throws Exception {
        final StreamsBuilder builder = new StreamsBuilder();
        Properties props = createProperties();
        // Stream builder that will do the wordcount
        KStream<String, String> source = builder.stream(propertiesLoader.getProperty("streams.topic.input.name"));
        /*
        * This will consume from the ConsumerProducerExampleTopic, it will get each topic and split the values for word
        * creating a map for each, after that it will group and make the count and proceed to store the results in the
        * new topic StreamsPlaintextOutput
        * */
        source.flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+")))
                .groupBy((key, value) -> value)
                .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
                .toStream()
                .to(propertiesLoader.getProperty("streams.topic.output.name"), Produced.with(Serdes.String(), Serdes.Long()));
        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

    public static Properties createProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, propertiesLoader.getProperty("streams.name"));
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesLoader.getProperty("streams.brokers"));
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }
}
