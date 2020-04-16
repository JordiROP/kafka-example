import java.io.IOException;
import java.util.Properties;

public class StreamsPropertiesLoader {
    private final Properties props;
    private static StreamsPropertiesLoader propertiesLoader;

    private StreamsPropertiesLoader() {
        props = new Properties();
        try {
            props.load(StreamsPropertiesLoader.class.getResourceAsStream("streams.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StreamsPropertiesLoader getPropertiesLoaderInstance() {
        if(propertiesLoader == null) {
            propertiesLoader = new StreamsPropertiesLoader();
        }
        return propertiesLoader;
    }

    public String getProperty(String key) {
        return this.props.getProperty(key);
    }
}
